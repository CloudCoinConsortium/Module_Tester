package com.cloudcoin.moduletester;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

public class LossFixer {
    public LossFixer(){
        try{
            System.out.println("Starting LostFixer Test");

            System.out.println("Flushing Lost, Predetect, and Detected Folders");
            FlushFolder("Lost");
            FlushFolder("Predetect");
            FlushFolder("Detected");

            System.out.println("Creating test CloudCoin files");
            byte [] cc = makeCloudCoinLost( 1);
            saveFile(cc, 1, "Lost");
            saveFile(cc, 1, "Predetect");

            System.out.println("Activating Fake Raida for the last 6 Nodes");
            byte[] hostsTextBytes = ("13.91.224.175 raida19.cloudcoin.global" + System.getProperty("line.separator")
                    + "13.91.224.175 raida20.cloudcoin.global" + System.getProperty("line.separator")
                    + "13.91.224.175 raida21.cloudcoin.global" + System.getProperty("line.separator")
                    + "13.91.224.175 raida22.cloudcoin.global" + System.getProperty("line.separator")
                    + "13.91.224.175 raida23.cloudcoin.global" + System.getProperty("line.separator")
                    + "13.91.224.175 raida24.cloudcoin.global" + System.getProperty("line.separator")
            ).getBytes();
            Files.write(Paths.get("C:\\Windows\\system32\\drivers\\etc\\hosts"),hostsTextBytes);

            System.out.println("Starting LossFixer");
            //runProcess("javac -cp src \"C:\\Program Files\\CloudCoin\\Main.java\"");
            runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-LossFixer-Java.jar\" C:\\CloudCoin test");

            System.out.println("Asserting that test CloudCoin has been fixed");
            String[] DetectedFileNames = selectFileNamesInFolder(Main.RootPath + "Detected" +File.separator);
            if(DetectedFileNames.length > 0 && Files.exists(Paths.get(Main.RootPath + "Detected" +File.separator + DetectedFileNames[0]))){
                System.out.println("Found file in Detected Folder");
                String expectedValue = new String(makeCloudCoinFixed(1));
                String actualValue = new String(Files.readAllBytes(Paths.get(Main.RootPath + "Detected" +File.separator + DetectedFileNames[0])));
                if(expectedValue.equals(actualValue)){
                    System.out.println("Test coin is fixed. TEST SUCCESSFUL");
                }else{
                    System.out.println("Test coin doesn't match expected result. TEST INCONCLUSIVE OR FAILED");
                    System.out.println("Printing coin data to console:");
                    System.out.println(new String(actualValue));
                }
                Files.deleteIfExists(Paths.get(Main.RootPath + "Detected" +File.separator + DetectedFileNames[0]));
            } else {
                System.out.println("No files in Detected Folder. TEST FAILED");
            }

            Files.write(Paths.get("C:\\Windows\\system32\\drivers\\etc\\hosts"), ("#" + System.getProperty("line.separator")).getBytes());
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }



        System.out.println("LossFixer Test Completed");


    }

    private static void printLines(String cmd, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(cmd + " " + line);
        }
    }

    private static void runProcess(String command) throws Exception{
        Process pro = Runtime.getRuntime().exec(command);
        printLines(command + " stdout:", pro.getInputStream());
        printLines(command + " stderr:", pro.getErrorStream());
        pro.waitFor();
        System.out.println(command + " exitValue() " + pro.exitValue());
    }

    public static void saveFile(byte[] cloudCoin, int sn, String folder) throws IOException {
        String filename = ensureFilenameUnique(getDenomination(sn) + ".CloudCoin.1." + sn + ".",".stack", Main.RootPath + folder + "\\");
        Files.write(Paths.get(Main.RootPath + folder + "\\" + filename), cloudCoin);
    }

    public static byte[] makeCloudCoinLost(int sn) {
        return ("{\n" +
                "  \"cloudcoin\": [\n" +
                "    {\n" +
                "      \"nn\": 1,\n" +
                "      \"sn\": " + sn + ",\n" +
                "      \"an\": [\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\"\n" +
                "      ],\n" +
                "      \"ed\": \"11-2020\",\n" +
                "      \"pown\": \"pppppppppppppppppppnnnnnn\",\n" +
                "      \"aoid\": []\n" +
                "    }\n" +
                "  ]\n" +
                "}").getBytes();
    }

    public static byte[] makeCloudCoinFixed(int sn) {
        return ("{\n" +
                "  \"cloudcoin\": [\n" +
                "    {\n" +
                "      \"nn\": 1,\n" +
                "      \"sn\": " + sn + ",\n" +
                "      \"an\": [\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\",\n" +
                "        \"00000000000000000000000000000000\"\n" +
                "      ],\n" +
                "      \"ed\": \"11-2020\",\n" +
                "      \"pown\": \"ppppppppppppppppppppppppp\",\n" +
                "      \"aoid\": []\n" +
                "    }\n" +
                "  ]\n" +
                "}").getBytes();
    }

    public static String ensureFilenameUnique(String filename, String extension, String folder) {
        if (!Files.exists(Paths.get(folder + filename + extension)))
            return filename + extension;

        filename = filename + '.';
        String newFilename;
        int loopCount = 0;
        do {
            newFilename = filename + Integer.toString(++loopCount);
        }
        while (Files.exists(Paths.get(folder + newFilename + extension)));
        return newFilename + extension;
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
    public static String[] selectFileNamesInFolder(String folderPath) {
        File folder = new File(folderPath);
        Collection<String> files = new ArrayList<>();
        if (folder.isDirectory()) {
            File[] filenames = folder.listFiles();

            if (null != filenames) {
                for (File file : filenames) {
                    if (file.isFile()) {
                        files.add(file.getName());
                    }
                }
            }
        }
        return files.toArray(new String[]{});
    }

    private static void FlushFolder(String folder) {
        try{
            Files.newDirectoryStream(Paths.get(Main.RootPath + folder + "\\")).forEach(file ->{
                try{Files.deleteIfExists(file);}catch (IOException e){
                    System.out.println("Uncaught exception - " + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            });
        }
        catch(IOException e){
            System.out.println("Uncaught exception - " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
