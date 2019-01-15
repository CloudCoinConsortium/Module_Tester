package com.cloudcoin.moduletester;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class Backupper {

    public static final String TAG_BACKUP = "CloudCoin Backup ";

    public static final String TAG_GALLERY = "Gallery";
    public static final String TAG_BACKUPER = "Backupper";
    public static final String TAG_BANK = "Bank";
    public static final String TAG_FRACKED = "Fracked";

    // name of file which will be placed in command folder
    public static String TAG_file_name = "backupper";

    public static final String TAG_CLOUD_COIN = "CloudCoin";
    public static final String TAG_COMMAND = "Command";
    public static final String TAG_BACKUP_DEFAULT = "Backup";
    public static final String TAG_LOGS = "Logs";

    public static final String TAG_ACCOUNTS = "Accounts";
    public static final String TAG_PASSWORDS = "Passwords";

//    public static final String RootPath = Paths.get("").toAbsolutePath().toString() + File.separator;
private static String RootPath = "C:\\CloudCoin\\";
    private static String DefaultPath = Main.RootPath;

    public static String CommandFolder = RootPath + TAG_CLOUD_COIN + File.separator + TAG_COMMAND + File.separator;
    public static String LogsFolder = RootPath + TAG_LOGS + File.separator
            + TAG_BACKUPER + File.separator;
    public static String AccountFolder = RootPath  + TAG_ACCOUNTS
            + File.separator + TAG_PASSWORDS + File.separator;
    public static String backupFolder = DefaultPath + TAG_BACKUP_DEFAULT + File.separator;

    public static String GalleryFolder = DefaultPath + TAG_GALLERY + File.separator;
    public static String BankFolder = DefaultPath + TAG_BANK + File.separator;
    public static String FrackedFolder = DefaultPath + TAG_FRACKED + File.separator;

    public static String Tag_account = RootPath + TAG_CLOUD_COIN + File.separator + TAG_ACCOUNTS;
    private static DateTimeFormatter timestampFormat = DateTimeFormatter.ofPattern("yyyy MMM dd HH.mm ss a");

    public Backupper() {
        createDirectories();
        try {
            System.out.println("Starting test for Backupper");
            System.out.println("Emptying Backup and Bank Folder");
            TestUtils.FlushFolder("Bank");
            TestUtils.FlushFolder("Backup");

            saveCommand(makeCommand());
            //saveAccountFile(makePassword());

            TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Backupper-Java.jar\" C:\\CloudCoin");
            String[] inBackupFolder = TestUtils.selectFileNamesInFolder(backupFolder);
            if(inBackupFolder.length > 0){
                System.out.println("TEST SUCCESS");
            }
            else{
                System.out.println("TEST FAILED: No Files in Backup Folder");
            }
            System.out.println("All Test. Clearing out Folders used in testing.");
            TestUtils.FlushFolder("Bank");
            TestUtils.FlushFolder("Backup");
        } catch (Exception ex) {
            Logger.getLogger(Backupper.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Uncaught exception - " + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }



    public static void createDummyCoins() throws IOException {
            int denomination;
            int sn;
            denomination = 1;
            sn = denominationToSN(denomination);
            saveCloudCoin(makeCloudCoin(sn), sn, denomination);
            saveCloudCoin(makeCloudCoin(++sn), sn, denomination);
    }
    
    public static void saveAccountFile(byte[] command) throws IOException {
        String filename = "1.txt";
        Files.write(Paths.get(AccountFolder + filename), command);
    }
    public static void saveCommand(byte[] command) throws IOException {
        String filename = "backupper2018 Nov 21 01.46 14 AM.command";
        Files.write(Paths.get(CommandFolder + filename), command);
    }

    public static byte[] makeCommand() {
        return ("{\n"
                + "      \"command\": \"backupper\",\n"
                + "      \"account\": \"1\",\n"
                + "      \"toPath\": \"" + backupFolder + "\"\n"
                + "}").getBytes();
    }
    public static byte[] makePassword() {
        return ("123neeraj").getBytes();
    }

    /* Methods */
    /**
     * Asks the user for instructions on how to export CloudCoins to new files.
     */
    public void backupCoins() {

        KeyboardReader reader = new KeyboardReader();

        // Ask for Bacck up.
        System.out.println("Do you want to backup your CloudCoin?");
        System.out.println("1 => backup");
        System.out.println("2 => Exit");

        int userChoice = reader.readInt();

        if (userChoice < 1 || userChoice > 1) {
            if (userChoice == 2) {
                System.out.println("User have cancel backup proecess. Exiting...");
            } else {
                System.out.println("nvalid Choice. No CloudCoins were backuped. Exiting...");
            }
        } else {
            String commandContent = getCommandFileContnet(CommandFolder);
            String backupAccount = "", backupPath = "";
            try {
                if (commandContent.isEmpty()) {
                    createBackupDirectory(backupPath);
                } else {
                    JSONObject jObj = new JSONObject(commandContent);
                    if (jObj.has("account")) {
                        backupAccount = jObj.getString("account");
                    }
                    if (jObj.has("toPath")) {
                        backupPath = jObj.getString("toPath");
                    }
                    if (backupAccount.isEmpty() || backupAccount.equalsIgnoreCase("default")) {
                        System.out.println("No Backup Account is specfied in command file. Going to backup at default location");
                    } else {
                        String password = getAccountFileName(backupAccount);
                        createFoldersWithAccountPassword(password);
                    }
                    if (backupPath.isEmpty() || backupPath.equalsIgnoreCase("default")) {
                        System.out.println("No Backup path is specfied in command file. Going to backup at default location");
                        createBackupDirectory("");
                    } else {
                        System.out.println("Taking backup at " + backupPath);
                        createBackupDirectory(backupPath);
                    }
                }

                copyFiles(new File(BankFolder), new File(backupFolder));
                copyFiles(new File(FrackedFolder), new File(backupFolder));
                copyFiles(new File(GalleryFolder), new File(backupFolder));
                System.out.println("Backup completed");
            } catch (JSONException ex) {
                Logger.getLogger("Backupper").log(Level.SEVERE, null, ex);
            }
        }
    }

    public static int denominationToSN(int denomination) {
        switch (denomination) {
            default:
            case 1:
                return 2000000;
            case 5:
                return 4000000;
            case 25:
                return 6000000;
            case 100:
                return 14000000;
            case 250:
                return 16000000;
        }
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

    public static void saveCloudCoin(byte[] cloudCoin, int sn, int denomination) throws IOException {
        String filename = ensureFilenameUnique(denomination + ".CloudCoin.1." + sn,
                ".stack", BankFolder);
        Files.write(Paths.get(BankFolder+ File.separator + filename), cloudCoin);
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

    public static String getBackUpTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy MMM dd HH.mm ss a");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static boolean createDirectories() {
        try {

            Files.createDirectories(Paths.get(RootPath));
            Files.createDirectories(Paths.get(CommandFolder));
            Files.createDirectories(Paths.get(LogsFolder));
            Files.createDirectories(Paths.get(AccountFolder));
            Files.createDirectories(Paths.get(backupFolder));
            Files.createDirectories(Paths.get(BankFolder));
            Files.createDirectories(Paths.get(GalleryFolder));
            Files.createDirectories(Paths.get(FrackedFolder));
        } catch (IOException e) {
            System.out.println("FS#CD: " + e.getLocalizedMessage());
            return false;
        }

        return true;
    }

    public static boolean createBackupDirectory(String path) {
        try {

            if (!path.isEmpty()) {
                Path createDirectories = Files.createDirectories(Paths.get(path + File.separator + TAG_BACKUP + getBackUpTime()));
                backupFolder = createDirectories.toString();
            } else {
                Path createDirectories = Files.createDirectories(Paths.get(backupFolder + TAG_BACKUP + getBackUpTime()));
                backupFolder = createDirectories.toString();
            }
        } catch (IOException e) {
            System.out.println("FS#CD: " + e.getLocalizedMessage());
            return false;
        }

        return true;
    }

    public static boolean createFoldersWithAccountPassword(String acccount_pass) {
        try {

            if (!acccount_pass.isEmpty()) {
                Path createDirectories = Files.createDirectories(Paths.get(Tag_account + File.separator + acccount_pass.trim() + File.separator));
                GalleryFolder = Files.createDirectories(Paths.get(createDirectories.toString() + File.separator + TAG_GALLERY + File.separator)).toString();
                BankFolder = Files.createDirectories(Paths.get(createDirectories.toString() + File.separator + TAG_BANK + File.separator)).toString();
                FrackedFolder = Files.createDirectories(Paths.get(createDirectories.toString() + File.separator + TAG_FRACKED + File.separator)).toString();
            } else {
                Path createDirectories = Files.createDirectories(Paths.get(Tag_account + File.separator + "default_account_password "+ getBackUpTime() + File.separator));
//                GalleryFolder = Files.createDirectories(Paths.get(createDirectories.toString() + File.separator + TAG_GALLERY + File.separator)).toString();
//                BankFolder = Files.createDirectories(Paths.get(createDirectories.toString() + File.separator + TAG_BANK + File.separator)).toString();
//                FrackedFolder = Files.createDirectories(Paths.get(createDirectories.toString() + File.separator + TAG_FRACKED + File.separator)).toString();

            }
            createDummyCoins();
        } catch (IOException e) {
            System.out.println("FS#CD: " + e.getLocalizedMessage());
            return false;
        }

        return true;
    }

    public static boolean copyFiles(File sourceDirectory, File destinationDirectory) {

        try {
            if (sourceDirectory.isDirectory()) {
                if (!destinationDirectory.exists()) {
                    destinationDirectory.mkdir();
                }

                String[] children = sourceDirectory.list();
                for (String children1 : children) {
                    copyFiles(new File(sourceDirectory, children1), new File(destinationDirectory, children1));
                }
            } else {

                OutputStream out;
                try (InputStream in = new FileInputStream(sourceDirectory)) {
                    out = new FileOutputStream(destinationDirectory);
                    // Copy the bits from instream to outstream
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                }
                out.close();
            }
        } catch (IOException e) {
            return false;
        }

        return true;
    }

      public static String getCommandFileContnet(String folderPath) {
        File folder = new File(folderPath);
        String commandFile = "";
        if (folder.isDirectory()) {

            File[] filenames = folder.listFiles();

            if (null != filenames) {
                for (File file : filenames) {
                    if (file.isFile()) {
                        if (file.getName().contains(".stack")) {
                            commandFile = readCommandFile(file.getAbsolutePath());
                            break;
                        }
                    }
                }
            }
        }
        return commandFile;
    }
 public static String getAccountFileName(String accountName) {
        File folder = new File(AccountFolder);
        String commandFile = "";
        if (folder.isDirectory()) {

            File[] filenames = folder.listFiles();

            if (null != filenames) {
                for (File file : filenames) {
                    if (file.isFile()) {
                        if (file.getName().contains(accountName)) {
                            commandFile = file.getAbsolutePath();
                            commandFile = fetchAccountContent(commandFile);
                            break;
                        }
                    }
                }
            }
        }
        return commandFile;
    }
 
 
    public static String readCommandFile(String filePath) {

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
//            String fileAsString = sb.toString();
//            JSONObject jObj =new JSONObject(fileAsString);
//            if(jObj.has("toPath")){
//                return jObj.getString("toPath");
//            }
        } catch (IOException | JSONException ex) {
            Logger.getLogger(Backupper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public static String fetchAccountContent(String filePath) {

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
        } catch (IOException | JSONException ex) {
            Logger.getLogger(Backupper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
