package com.cloudcoin.moduletester;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Minder {
    private static String RootPath = Main.RootPath;
    public static String CommandFolder = "C:\\CloudCoin\\Command\\";


    public Minder() {
        createDirectories();

        ShowCommandLineOutput();
    }

    public static void setRootPath(String[] args) {
        if (args == null || args.length == 0)
            return;

        if (Files.isDirectory(Paths.get(args[0]))) {
            RootPath = args[0];
        }
    }

    public static void createDirectories() {
        try {
            Files.createDirectories(Paths.get(RootPath));
            Files.createDirectories(Paths.get(RootPath + "Detected\\"));
            Files.createDirectories(Paths.get(RootPath + "Bank\\"));
            Files.createDirectories(Paths.get(RootPath + "Fracked\\"));
            Files.createDirectories(Paths.get(RootPath + "Counterfeit\\"));
            Files.createDirectories(Paths.get(RootPath + "Mind\\"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ShowCommandLineOutput() {
        KeyboardReader reader = new KeyboardReader();
        int input = 1;

        System.out.println("Starting test for Minder");
        System.out.println("Emptying Associated Folders");
        TestUtils.FlushFolder("Bank");
        TestUtils.FlushFolder("Mind");


        while (input < 4) {
            try {


                switch (input) {
                    case 1:
                        System.out.println("1. 1 CloudCoin to Mind");

                        TestUtils.saveFile( makeCloudCoin(1) , 1, "Bank");
                        saveCommand(makeCommand(true));
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Minder-Java.jar\" C:\\CloudCoin\\");

                        String[] inMindFolder = TestUtils.selectFileNamesInFolder(RootPath + "Mind\\");
                        String[] inBankFolder = TestUtils.selectFileNamesInFolder(RootPath + "Bank\\");
                        if(inMindFolder.length > 0 && inBankFolder.length == 0) {
                            System.out.println("TEST 1 SUCCESS");
                            input++;
                        }
                        else{
                            System.out.println("TEST 1 FAILED: No Files found in Mind Folder");
                            input = 3;
                        }
                        break;
                    case 2:
                        System.out.println("2. 1 CloudCoin From Mind");


                        saveCommand(makeCommand(false));
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Minder-Java.jar\" C:\\CloudCoin\\");
                        inBankFolder = TestUtils.selectFileNamesInFolder(RootPath + "Bank\\");
                        inMindFolder = TestUtils.selectFileNamesInFolder(RootPath + "Mind\\");
                        if(inBankFolder.length > 0 && inMindFolder.length == 0) {
                            System.out.println("TEST 2 SUCCESS");

                        }
                        else{
                            System.out.println("TEST 2 FAILED: Test coin not found in Bank folder.");
                        }
                        input++;
                        break;

                    case 3:
                        System.out.println("All Tests Complete. Clearing out Folders used in testing.");
                        TestUtils.FlushFolder("Bank");
                        TestUtils.FlushFolder("Mind");
                        return;
                }

            } catch (Exception e) {
                System.out.println("Uncaught exception - " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }

    public static byte[] makeCloudCoin(int sn) {
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

    public static void saveCommand(byte[] command) throws IOException {
        String filename = TestUtils.ensureFilenameUnique("Minder.mind",
                ".txt", CommandFolder);
        Files.createDirectories(Paths.get(CommandFolder));
        Files.write(Paths.get(CommandFolder + filename), command);
    }

    public static byte[] makeCommand(Boolean send) {
        String command = send ? "toMind" : "fromMind";

        return ("{\n" +
                "      \"command\": \""+command+"\",\n" +
                "      \"account\": \"DefaultUser\",\n" +
                "      \"passphrase\": \"test\",\n" +
                "  \"cloudcoin\": \"1\",\n" +

                "}").getBytes();
    }
}
