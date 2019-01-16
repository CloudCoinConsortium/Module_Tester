package com.cloudcoin.moduletester;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Unpacker {

    private static String RootPath = Main.RootPath;


    public Unpacker() {
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
            Files.createDirectories(Paths.get(RootPath + "Import\\"));
            Files.createDirectories(Paths.get(RootPath + "Suspect\\"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ShowCommandLineOutput() {
        //KeyboardReader reader = new KeyboardReader();
        int input = 1;
        System.out.println("Starting test for Unpacker");
        System.out.println("Emptying Import and Suspect Folder");
        TestUtils.FlushFolder("Import");
        TestUtils.FlushFolder("Suspect");
        while (input < 9) {
            try {


                switch (input) {
                    case 1:
                        System.out.println("1. Unpack CloudCoin (Single Stack)");
                        byte[] testcoin = makeCloudCoinSingle(1);
                        String testfilename = TestUtils.saveFile(testcoin, 1, "Import",  ".stack");
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Unpacker-Java.jar\" C:\\CloudCoin\\Accounts\\DefaultUser\\");
                        if(Files.exists(Paths.get(RootPath+ "Suspect\\" + testfilename))){

                            if(!Files.exists(Paths.get(RootPath + "Imported\\" + testfilename)))
                                System.out.println("The unpacked coin is not in Imported folder. TEST FAILED.");
                            else
                                System.out.println("Test coin is unpacked. TEST SUCCESSFUL");

                        }else {
                            System.out.println("Couldn't find test coin in Suspect Folder. TEST FAILED");
                        }
                        break;
                    case 2:
                        System.out.println("2. Unpack CloudCoins (Multi Stack: 2)");
                        String testfilename2 = TestUtils.saveFile(makeCloudCoinStack(2), 2, "Import", ".stack");
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Unpacker-Java.jar\" C:\\CloudCoin\\Accounts\\DefaultUser\\");
                        if(Files.exists(Paths.get(RootPath+ "Suspect\\" + TestUtils.getDenomination(2) + ".CloudCoin.1." + 2 + ".stack"))
                        &&Files.exists(Paths.get(RootPath+ "Suspect\\" + TestUtils.getDenomination(102) + ".CloudCoin.1." + 102 + ".stack"))
                                &&Files.exists(Paths.get(RootPath+ "Suspect\\" + TestUtils.getDenomination(202) + ".CloudCoin.1." + 202 + ".stack")))
                        {
                            if(!Files.exists(Paths.get(RootPath + "Imported\\" + testfilename2)))
                                System.out.println("The unpacked coin is not in Imported folder. TEST FAILED.");
                            else
                            System.out.println("Test coin is unpacked. TEST SUCCESSFUL");
                        }else {
                            System.out.println("Couldn't find test coin in Suspect Folder. TEST FAILED");
                        }
                        break;
                    case 3:
                        System.out.println("3. Unpack CloudCoin (JPG)");
                        String testfilename3 = TestUtils.saveFile(makeCloudCoinJpg(), 1346931, "Import",".jpg");
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Unpacker-Java.jar\" C:\\CloudCoin\\Accounts\\DefaultUser\\");
                        if(Files.exists(Paths.get(RootPath+ "Suspect\\" + "1.CloudCoin.1.1346931.stack"))){

                            if(!Files.exists(Paths.get(RootPath + "Imported\\" + testfilename3)))
                                System.out.println("The unpacked coin is not in Imported folder. TEST FAILED.");
                            else
                            System.out.println("Test coin is unpacked. TEST SUCCESSFUL");

                        }else {
                            System.out.println("Couldn't find test coin in Suspect Folder. TEST FAILED");
                        }
                        break;
                    case 4:
                        System.out.println("4. Unpack 4 CloudCoin files (one of each type)");
                        String testcoin1 = TestUtils.saveFile(makeCloudCoinSingle(4), 4, "Import",".stack");
                        String testfilename4 = TestUtils.saveFile(makeCloudCoinStack(5), 5, "Import",".stack");
                        //TestUtils.saveFile(makeCloudCoinJpg(), 6, "Import",".jpg");
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Unpacker-Java.jar\" C:\\CloudCoin\\Accounts\\DefaultUser\\");
                        if(Files.exists(Paths.get(RootPath+ "Suspect\\" + testcoin1))
                        &&Files.exists(Paths.get(RootPath+ "Suspect\\" +  TestUtils.getDenomination(5) + ".CloudCoin.1." + 5 + ".stack"))
                        &&Files.exists(Paths.get(RootPath+ "Suspect\\" +  TestUtils.getDenomination(105) + ".CloudCoin.1." + 105 + ".stack"))
                        &&Files.exists(Paths.get(RootPath+ "Suspect\\" +  TestUtils.getDenomination(205) + ".CloudCoin.1." + 205 + ".stack"))){

                            if(!Files.exists(Paths.get(RootPath + "Imported\\" + testfilename4)) && !Files.exists(Paths.get(RootPath + "Imported\\" + testcoin1)))
                                System.out.println("An unpacked coin is not in Imported folder. TEST FAILED.");
                            else
                            System.out.println("Test coin is unpacked. TEST SUCCESSFUL");

                        }else {
                            System.out.println("Couldn't find test coin in Suspect Folder. TEST FAILED");
                        }
                        break;
                        /*
                    case 5:
                        System.out.println("5. Unpack CloudCoins (Multi Stack: 100)");
                        TestUtils.saveFile(makeCloudCoinStackCustom(7, 100), 7, "Import",".stack");
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Unpacker-Java.jar\" C:\\CloudCoin");
                        if(Files.exists(Paths.get(RootPath+ "Suspect\\" + testfilename))){

                            System.out.println("Test coin is unpacked. TEST SUCCESSFUL");

                        }else {
                            System.out.println("Couldn't find test coin in Suspect Folder. TEST FAILED");
                        }
                        break;
                    case 6:
                        System.out.println("6. Unpack CloudCoins (Multi Stack: 1000)");
                        TestUtils.saveFile(makeCloudCoinStackCustom(8, 1000), 8, "Import",".stack");
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Unpacker-Java.jar\" C:\\CloudCoin");
                        if(Files.exists(Paths.get(RootPath+ "Suspect\\" + testfilename))){

                            System.out.println("Test coin is unpacked. TEST SUCCESSFUL");

                        }else {
                            System.out.println("Couldn't find test coin in Suspect Folder. TEST FAILED");
                        }
                        break;
                    case 7:
                        System.out.println("7. Unpack CloudCoins (Multi Stack: 10000)");
                        TestUtils.saveFile(makeCloudCoinStackCustom(9, 10000), 9, "Import",".stack");
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Unpacker-Java.jar\" C:\\CloudCoin");
                        if(Files.exists(Paths.get(RootPath+ "Suspect\\" + testfilename))){

                            System.out.println("Test coin is unpacked. TEST SUCCESSFUL");

                        }else {
                            System.out.println("Couldn't find test coin in Suspect Folder. TEST FAILED");
                        }
                        break;
                        */
                    case 8:
                        System.out.println("Finished Testing. Clearing out tested Folders.");
                        TestUtils.FlushFolder("Import");
                        TestUtils.FlushFolder("Suspect");
                        return;
                }
            } catch (Exception e) {
                System.out.println("Uncaught exception - " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        input++;
        }
    }



    public static byte[] makeCloudCoinSingle(int sn) {
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

    public static byte[] makeCloudCoinStack(int sn) {
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
                "    },\n" +
                "    {\n" +
                "      \"nn\": 1,\n" +
                "      \"sn\": " + sn + 100 + ",\n" +
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
                "    },\n" +
                "    {\n" +
                "      \"nn\": 1,\n" +
                "      \"sn\": " + sn + 200 + ",\n" +
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

    public static byte[] makeCloudCoinJpg() throws IOException {
        return Files.readAllBytes(Paths.get("1.CloudCoin.1.1346931.jpg"));
    }

    public static byte[] makeCloudCoinStackCustom(int sn, int size) {
        StringBuilder cloudCoin = new StringBuilder("{\n" +
                "  \"cloudcoin\": [\n");
        for (int i = 0; i < size; i++) {
            cloudCoin.append("    {\n" +
                    "      \"nn\": 1,\n" +
                    "      \"sn\": " + (sn + i) + ",\n" +
                    "      \"an\": [\n");
            for (int j = 0; j < 24; j++)
                cloudCoin.append("        \"00000000000000000000000000000000\",\n");
            cloudCoin.append("        \"00000000000000000000000000000000\"\n");
            cloudCoin.append("      ],\n" +
                    "      \"ed\": \"11-2020\",\n" +
                    "      \"pown\": \"ppppppppppppppppppppppppp\",\n" +
                    "      \"aoid\": []\n" +
                    "    }");
            if (i != size - 1)
                cloudCoin.append(",\n");
        }
        cloudCoin.append("\n" +
                "  ]\n" +
                "}");
        return cloudCoin.toString().getBytes();
    }


}
