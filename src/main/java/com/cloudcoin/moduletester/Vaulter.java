package com.cloudcoin.moduletester;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class Vaulter {
    private static String RootPath = Main.RootPath;
    public static String CommandFolder = "C:\\CloudCoin\\Command\\";


    public Vaulter() {
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
            Files.createDirectories(Paths.get(RootPath + "Vault\\"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ShowCommandLineOutput() {
        KeyboardReader reader = new KeyboardReader();
        int input = 1;

        System.out.println("Starting test for Vaulter");
        System.out.println("Emptying Associated Folders");
        TestUtils.FlushFolder("Bank");
        TestUtils.FlushFolder("Vault");


        while (input < 4) {
            try {


                switch (input) {
                    case 1:
                        System.out.println("1. Vaulting 1 CloudCoin");

                        TestUtils.saveFile( makeCloudCoin(1) , 1, "Bank");
                        saveCommand(makeCommand(true));
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Vaulter-Java.jar\" C:\\CloudCoin");

                        String[] inVaultFolder = TestUtils.selectFileNamesInFolder(RootPath + "Vault\\");
                        String[] inBankFolder = TestUtils.selectFileNamesInFolder(RootPath + "Bank\\");
                        if(inVaultFolder.length > 0 && inBankFolder.length == 0) {
                            System.out.println("TEST 1 SUCCESS");
                            input++;
                        }
                        else{
                            System.out.println("TEST 1 FAILED: No Files found in Vault Folder");
                            input = 3;
                        }
                        break;
                    case 2:
                        System.out.println("2. Retrieving 1 CloudCoin");


                        saveCommand(makeCommand(false));
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Vaulter-Java.jar\" C:\\CloudCoin");
                        inBankFolder = TestUtils.selectFileNamesInFolder(RootPath + "Bank\\");
                        inVaultFolder = TestUtils.selectFileNamesInFolder(RootPath + "Vault\\");
                        if(inBankFolder.length > 0 && inVaultFolder.length == 0) {
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
                        TestUtils.FlushFolder("Vault");
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
        String filename = TestUtils.ensureFilenameUnique("Vaulter.vault",
                ".txt", CommandFolder);
        Files.createDirectories(Paths.get(CommandFolder));
        Files.write(Paths.get(CommandFolder + filename), command);
    }

    public static byte[] makeCommand(Boolean send) {
        String command = send ? "toVault" : "fromVault";

        return ("{\n" +
                "      \"command\": \""+command+"\",\n" +
                "      \"account\": \"DefaultUser\",\n" +
                "      \"passphrase\": \"test\",\n" +
                "  \"cloudcoin\": \"1\",\n" +

                "}").getBytes();
    }
}
