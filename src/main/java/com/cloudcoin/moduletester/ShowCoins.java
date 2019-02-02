package com.cloudcoin.moduletester;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

public class ShowCoins {

    private static String RootPath = Main.RootPath;
    private static String CommandFolder = RootPath + "Command\\";
    private static String LogsPath = Main.LogsPath + "ShowCoins\\";


    public ShowCoins() {
        createDirectories();
        Instant start = Instant.now();
        StartShowCoinsTest();
        Instant end = Instant.now();
        System.out.println("Four Tests,Time Elapsed: " + Duration.between(start, end).toMillis() + "ms");

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
            Files.createDirectories(Paths.get(LogsPath));
            Files.createDirectories(Paths.get(RootPath + "Bank\\"));
            Files.createDirectories(Paths.get(RootPath + "Fracked\\"));
			Files.createDirectories(Paths.get(RootPath + "Lost\\"));
			Files.createDirectories(Paths.get(RootPath + "Gallery\\"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void StartShowCoinsTest() {

            try {

                System.out.println("Testing Show 1 CloudCoin (Bank)");

                        TestUtils.FlushFolder("Bank");
                TestUtils.FlushFolder("Gallery");
                TestUtils.FlushFolder("Lost");
                TestUtils.FlushFolder("Fracked");
                        TestUtils.saveFile(makeCloudCoinCounterfeit(1), 1, "Bank");
                        saveCommand(makeCommand());
                TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-ShowCoins-Java.jar\" C:\\CloudCoin\\Accounts\\DefaultUser\\ singleRun");
                //assert
                if(Files.exists(Paths.get(LogsPath + "Bank.1.1.0.0.0.0.txt")))
                    System.out.println("TEST 1 SUCCESSFUL");
                else
                    System.out.println("No Log file found. TEST FAILED");

                System.out.println("Testing Show 10 CloudCoins (Fracked)");


                        for (int i = 0; i < 5; i++)
                            TestUtils.saveFile(makeCloudCoinCounterfeit(1 + i), 1 + i, "Fracked");
                        TestUtils.saveFile(makeCloudCoinCounterfeit(2097154), 2097154, "Fracked");
                saveCommand(makeCommand());
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-ShowCoins-Java.jar\" C:\\CloudCoin\\Accounts\\DefaultUser\\ singleRun");
                //assert
                if(Files.exists(Paths.get(LogsPath + "Fracked.10.5.1.0.0.0.txt")))
                    System.out.println("TEST 2 SUCCESSFUL");
                else
                    System.out.println("No Log file found. TEST FAILED");

                System.out.println("Testing Show 100 CloudCoins (Lost)");


                        TestUtils.saveFile(makeCloudCoinCounterfeit(6291458), 6291458, "Lost");
                saveCommand(makeCommand());
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-ShowCoins-Java.jar\" C:\\CloudCoin\\Accounts\\DefaultUser\\ singleRun");
                //assert
                if(Files.exists(Paths.get(LogsPath + "Lost.100.0.0.0.1.0.txt")))
                    System.out.println("TEST 3 SUCCESSFUL");
                else
                    System.out.println("No Log file found. TEST FAILED");

                System.out.println("Testing Show 1000 CloudCoins (Bank)");

                        TestUtils.FlushFolder("Bank");
                        for (int i = 0; i < 4; i++)
                            TestUtils.saveFile(makeCloudCoinCounterfeit(14680066 + i), 14680066 + i, "Bank");
                saveCommand(makeCommand());
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-ShowCoins-Java.jar\" C:\\CloudCoin\\Accounts\\DefaultUser\\ singleRun");
                //assert
                if(Files.exists(Paths.get(LogsPath + "Bank.1000.0.0.0.0.4.txt")))
                    System.out.println("TEST 4 SUCCESSFUL");
                else
                    System.out.println("No Log file found. TEST FAILED");

                System.out.println("Testing Show Gallery");
                if(Files.exists(Paths.get(LogsPath + "Gallery.false.txt")))
                System.out.println("TEST 4 SUCCESSFUL");
                else
                System.out.println("No Log file found. TEST FAILED");

                System.out.println("All Tests Complete. Clearing out Folders used in testing.");
                TestUtils.FlushFolder("Bank");
                TestUtils.FlushFolder("Gallery");
                TestUtils.FlushFolder("Lost");
                TestUtils.FlushFolder("Fracked");

            } catch (Exception e) {
                System.out.println("Uncaught exception - " + e.getLocalizedMessage());
                e.printStackTrace();
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
                "      \"pown\": \"fffffffffffffffffffffffff\",\n" +
                "      \"aoid\": []\n" +
                "    }\n" +
                "  ]\n" +
                "}").getBytes();
    }

    public static void saveCommand(byte[] command) throws IOException {
        String filename = "showcoins.command";
        Files.write(Paths.get(CommandFolder + filename), command);
    }

    public static byte[] makeCommand() {
        return ("{\n"
                + "  \"command\": \"showcoins\",\n"
                + "  \"account\": \"default\"\n"
                + "}").getBytes();
    }

}
