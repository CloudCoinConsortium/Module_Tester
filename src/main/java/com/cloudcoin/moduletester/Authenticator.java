package com.cloudcoin.moduletester;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Authenticator {

    private static String RootPath = Main.RootPath;


    public Authenticator() {
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
            Files.createDirectories(Paths.get(RootPath + "Suspect\\"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ShowCommandLineOutput() {
        int input = 1;
        System.out.println();

        System.out.println("Starting test for Authenticator");
        System.out.println("Emptying Detected and Suspect Folder");
        TestUtils.FlushFolder("Suspect");
        TestUtils.FlushFolder("Detected");
        boolean test2 = true;
        boolean test3 = true;
        boolean test4 = true;


        while (input < 5) {
            try {

                switch (input) {
                    case 1:
                        System.out.println("1. Authenticate 1 CloudCoin (Counterfeit)");
                        TestUtils.saveFile(makeCloudCoinCounterfeit(1), 1, "Suspect");
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Authenticator-Java.jar\" C:\\CloudCoin");
                        if(Files.exists(Paths.get(RootPath + "Detected\\" + TestUtils.getDenomination(1) + ".CloudCoin.1." + 1 + ".stack"))) {
                            System.out.println("TEST 1 SUCCESS");
                        }
                        else{
                            System.out.println("TEST 1 FAILED: Test coin not found in Detected folder.");
                        }
                        break;
                    case 2:
                        System.out.println("2. Authenticate 10 CloudCoins (Counterfeit)");
                        for (int i = 0; i < 10; i++)
                            TestUtils.saveFile(makeCloudCoinCounterfeit(2 + i), 2 + i, "Suspect");
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Authenticator-Java.jar\" C:\\CloudCoin");
                        for (int j = 0; j < 10; j++)
                            if(!Files.exists(Paths.get(RootPath + "Detected\\" + TestUtils.getDenomination(2 + j) + ".CloudCoin.1." + (2+j) + ".stack")))
                                test2 = false;
                        if(test2) {
                            System.out.println("TEST 2 SUCCESS");
                        }
                        else{
                            System.out.println("TEST 2 FAILED: a Test coin not found in Detected folder.");
                        }
                        break;
                    case 3:
                        System.out.println("3. Authenticate 100 CloudCoins (Counterfeit)");
                        for (int i = 0; i < 100; i++)
                            TestUtils.saveFile(makeCloudCoinCounterfeit(100 + i), 100 + i, "Suspect");
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Authenticator-Java.jar\" C:\\CloudCoin");
                        for (int j = 0; j < 100; j++)
                            if(!Files.exists(Paths.get(RootPath + "Detected\\" + TestUtils.getDenomination(100 + j) + ".CloudCoin.1." + (100+j) + ".stack")))
                                test3 = false;
                        if(test3) {
                            System.out.println("TEST 3 SUCCESS");
                        }
                        else{
                            System.out.println("TEST 3 FAILED: a Test coin not found in Detected folder.");
                        }
                        break;
                    case 4:
                        System.out.println("4. Authenticate 400 CloudCoins (Counterfeit)");
                        for (int i = 0; i < 1000; i++)
                            TestUtils.saveFile(makeCloudCoinCounterfeit(1000 + i), 1000 + i, "Suspect");
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Authenticator-Java.jar\" C:\\CloudCoin");
                        for (int j = 0; j < 1000; j++)
                            if(!Files.exists(Paths.get(RootPath + "Detected\\" + TestUtils.getDenomination(1000 + j) + ".CloudCoin.1." + (1000+j) + ".stack")))
                                test4 = false;
                        if(test4) {
                            System.out.println("TEST 4 SUCCESS");
                        }
                        else{
                            System.out.println("TEST 4 FAILED: a Test coin not found in Detected folder.");
                        }
                        break;
                    case 5:
                        return;
                }
            } catch (Exception e) {
                System.out.println("Uncaught exception - " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        input++;
        }
    }



    public static byte[] makeCloudCoinCounterfeit(int sn) {
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
                "      \"pown\": \"uuuuuuuuuuuuuuuuuuuuuuuuu\",\n" +
                "      \"aoid\": []\n" +
                "    }\n" +
                "  ]\n" +
                "}").getBytes();
    }

}
