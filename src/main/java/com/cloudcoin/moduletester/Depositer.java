package com.cloudcoin.moduletester;



import com.cloudcoin.moduletester.utils.Cloudcoin;
import com.cloudcoin.moduletester.utils.Response;
import com.cloudcoin.moduletester.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Depositer {

    public static final String RootPath = Paths.get("").toAbsolutePath().toString() + File.separator;
    public static String CommandFolder = RootPath + "Command" + File.separator;
    public static String CommandHistory = RootPath + "Command" + File.separator + "CommandHistory" + File.separator;
    public static String IDFolder = RootPath + "Accounts" + File.separator + "DefaultUser" + File.separator + "ID";
    public static String ExportPath = "";
    public static String Bank_URL = "http://cloudcoinconsortium.org/retrieve";
    // name of file which will be placed in command folder
    public static String TAG_file_name = "getDeposit";

    public Depositer() {
        try {
            createDirectories();
            saveCommand(makeCommand());
            createDummyCoin();
            deposit();
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public static void createDirectories() {
        try {
            Files.createDirectories(Paths.get(RootPath));
            Files.createDirectories(Paths.get(IDFolder));
            Files.createDirectories(Paths.get(CommandFolder));
            Files.createDirectories(Paths.get(CommandHistory));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Methods */
    /**
     * Asks the user for instructions on how to export CloudCoins to new files.
     */
    public void deposit() throws IOException, InterruptedException, ExecutionException {

        KeyboardReader reader = new KeyboardReader();

        // Ask for Bacck up.
        System.out.println("Do you want to deposit your CloudCoin?");
        System.out.println("1 => Deposit");
        System.out.println("2 => Exit");

        int userChoice = reader.readInt();

        if (userChoice < 1 || userChoice > 1) {
            if (userChoice == 2) {
                System.out.println("User have cancel deposit process Exiting...");
            } else {
                System.out.println("invalid Choice. No CloudCoins were deposited. Exiting...");
            }
        } else {
            String commandContent = getCommandFileContnet(CommandFolder);

            try {
                if (commandContent.isEmpty()) {
                    System.out.println("No Command found");
                } else {
                    JSONObject jObj = new JSONObject(commandContent);
                    if (jObj.has("bankURL")) {
                        Bank_URL = jObj.getString("bankURL");
                    }
                    if (jObj.has("account") && jObj.has("moveTo")) {
                        ExportPath = RootPath + "Accounts" + File.separator + jObj.getString("account") + File.separator + jObj.getString("moveTo") + File.separator;
                        createExportFolder();
                    }
                    Response response = Utils.createGson().fromJson(getFileContnet(IDFolder), Response.class);
                    Cloudcoin coin = response.getCloudcoin().get(0);
                    String an = coin.getAn().get(0);
                    getTicket(Integer.parseInt(coin.getNn()), Integer.parseInt(coin.getSn()), an, getDenomination(Integer.parseInt(coin.getSn())));
                }

            } catch (JSONException ex) {
                Logger.getLogger("Depositer").log(Level.SEVERE, null, ex);
            }
        }
    }

    public static String ensureFilenameUnique(String filename, String extension, String folder) {
        if (!Files.exists(Paths.get(folder + filename + extension))) {
            return filename + extension;
        }

        filename = filename + '.';
        String newFilename;
        int loopCount = 0;
        do {
            newFilename = filename + Integer.toString(++loopCount);
        } while (Files.exists(Paths.get(folder + newFilename + extension)));
        return newFilename + extension;
    }

    public static void createExportFolder() throws IOException {
        Files.createDirectories(Paths.get(ExportPath));
    }
    /**
     * Returns an ticket from a trusted server
     *
     * @param nn int that is the coin's Tickets Number
     * @param sn int that is the coin's Serial Number
     * @param an String that is the coin's Authenticity Number (GUID)
     * @param d int that is the Denomination of the Coin
     * @return Response Object.
     */
    public void getTicket(int nn, int sn, String an, int d) throws InterruptedException, ExecutionException {
        CompletableFuture.supplyAsync(() -> {
            Bank_URL = Bank_URL + "get_ticket?nn=" + nn + "&sn=" + sn + "&an=" + an + "&pan=" + an + "&denomination=" + d;

            long before = System.currentTimeMillis();

            try {
                String fullResponse = Utils.getHtmlFromURL(Bank_URL);
                long after = System.currentTimeMillis();
                long ts = after - before;

                JSONObject jsonObject = new JSONObject(fullResponse);

                if (jsonObject.has("cloudcoin")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("cloudcoin");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        saveCoinFile(exportcoin(jObj.toString()), jObj.getInt("sn"), getDenomination(jObj.getInt("sn")));
                        System.out.println(jObj.toString());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(Depositer.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }).get();
    }

    public static int getDenomination(int sn) {
        int nom;
        if (sn < 1)
            nom = 0;
        else if ((sn < 2097153))
            nom = 1;
        else if ((sn < 4194305))
            nom = 5;
        else if ((sn < 6291457))
            nom = 25;
        else if ((sn < 14680065))
            nom = 100;
        else if ((sn < 16777217))
            nom = 250;
        else
            nom = 0;

        return nom;
    }
    public static void saveCoinFile(byte[] cloudCoin, int sn, int denomination) throws IOException {
        String filename = ensureFilenameUnique(denomination+".CloudCoin.1.0000" + sn + ".e054a34f2790fd3353ea26e5d92d9d2f", ".stack", ExportPath);
        Files.write(Paths.get(ExportPath+ File.separator + filename), cloudCoin);
    }

    public static byte[] exportcoin(String coin) {
        return ("{\n"
                + "  \"cloudcoin\": [\n"
                + coin
                + "  ]\n"
                + "}").getBytes();
    }


    public static void createDummyCoin() throws IOException {
        int denomination;
        int sn;
        denomination = 1;
        sn = getDenomination(denomination);
        saveCloudCoin(makeCloudCoin(sn), denomination);
    }

    public static void saveCloudCoin(byte[] cloudCoin, int sn) throws IOException {
        String filename = ensureFilenameUnique("1.CloudCoin.1." + sn,
                ".stack", IDFolder);
        Files.write(Paths.get(IDFolder + File.separator + filename), cloudCoin);
    }

    public static byte[] makeCloudCoin(int sn) {
        return ("{\n"
                + "  \"cloudcoin\": [\n"
                + "    {\n"
                + "      \"nn\": 1,\n"
                + "      \"sn\": " + sn + ",\n"
                + "      \"an\": [\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\",\n"
                + "        \"00000000000000000000000000000000\"\n"
                + "      ],\n"
                + "      \"ed\": \"11-2020\",\n"
                + "      \"pown\": \"ppppppppppppppppppppppppp\",\n"
                + "      \"aoid\": []\n"
                + "    }\n"
                + "  ]\n"
                + "}").getBytes();
    }


    public static void saveCommand(byte[] command) throws IOException {
        String filename = "Depositer.retrieve.txt";
        Files.write(Paths.get(CommandFolder + filename), command);
    }

    public static byte[] makeCommand() {
        return ("{\n"
                + "      \"command\": \"getDeposit\",\n"
                + "      \"bankURL\": \"https://raida0.cloudcoin.global/service/\",\n"
                + "      \"account\": \"default\",\n"
                + "      \"ID\": \"default\",\n"
                + "      \"moveTo\": \"Import" + "\"\n"
                + "}").getBytes();
    }


    public static String readFile(String filePath) {

        InputStream is;

        try {
            is = new FileInputStream(filePath);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));

            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();

            while (line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }

            return sb.toString();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Depositer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Depositer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static String getCommandFileContnet(String folderPath) {
        File folder = new File(folderPath);
        String commandFile = "";
        if (folder.isDirectory()) {

            File[] filenames = folder.listFiles();

            if (null != filenames) {
                for (File file : filenames) {
                    if (file.isFile()) {
                        if (file.getName().equalsIgnoreCase("Depositer.retrieve.txt")) {
                            commandFile = readFile(file.getAbsolutePath());
                            break;
                        }
                    }
                }
            }
        }
        return commandFile;
    }

    public static String getFileContnet(String folderPath) {
        File folder = new File(folderPath);
        String commandFile = "";
        if (folder.isDirectory()) {

            File[] filenames = folder.listFiles();

            if (null != filenames) {
                for (File file : filenames) {
                    if (file.isFile()) {
                        commandFile = readFile(file.getAbsolutePath());
                        break;
                    }
                }
            }
        }
        return commandFile;
    }


    public static int milliSecondsToTimeOutDetect = 2000;

    /**
     * Creates a Gson object, a JSON parser for converting JSON Strings and objects.
     *
     * @return a Gson object.
     */
    public static Gson createGson() {
        return new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .create();
    }

    public static String getHtmlFromURL(String urlAddress) {
        String data = "";

        try {
            URL url = new URL(urlAddress);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setConnectTimeout(milliSecondsToTimeOutDetect);
            if (200 != connect.getResponseCode())
                return data;

            BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));

            StringBuilder builder = new StringBuilder();
            while ((data = in.readLine()) != null)
                builder.append(data);
            in.close();
            data = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            data = "";
        }

        return data;
    }

}
