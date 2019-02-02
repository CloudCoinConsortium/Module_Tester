package com.cloudcoin.moduletester;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Exporter {

    private static String RootPath = "C:\\CloudCoin\\";
    private static String DefaultPath = Main.RootPath;


    private static String PasswordFolder = RootPath + "accounts\\Passwords" + File.separator;
    private static String CommandFolder = DefaultPath + "Command" + File.separator;

    private static final DateTimeFormatter timestampFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    public Exporter() {
        createDirectories();

        Instant start = Instant.now();
        ShowCommandLineOutput();
        Instant end = Instant.now();
        System.out.println("Six Tests,Time Elapsed: " + Duration.between(start, end).toMillis() + "ms");
    }

    public static void setRootPath(String[] args) {
        if (args == null || args.length == 0)
            return;

        if (Files.isDirectory(Paths.get(args[0]))) {
            RootPath = args[0];
            PasswordFolder = RootPath + "accounts\\Passwords" + File.separator;
            CommandFolder = RootPath + "Command" + File.separator;
        }
    }

    public static void createDirectories() {
        try {
            Files.createDirectories(Paths.get(DefaultPath));
            Files.createDirectories(Paths.get(CommandFolder));
            Files.createDirectories(Paths.get(DefaultPath + "Detected\\"));
            Files.createDirectories(Paths.get(DefaultPath + "Bank\\"));
            Files.createDirectories(Paths.get(DefaultPath + "Fracked\\"));
            Files.createDirectories(Paths.get(DefaultPath + "Counterfeit\\"));
            Files.createDirectories(Paths.get(DefaultPath + "Lost\\"));
            Files.createDirectories(Paths.get(DefaultPath + "Export\\"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ShowCommandLineOutput() {
        KeyboardReader reader = new KeyboardReader();

        int input = 1;

        while (input < 8) {
            try {
                System.out.println();

                System.out.println("Starting test for Exporter");
                System.out.println("Emptying Associated Folders");
                TestUtils.FlushFolder("Bank");
                TestUtils.FlushFolder("Export");


                //System.out.println("6. Export 381 CloudCoins, single stack");
                //System.out.println("7. Export 1000 1-CloudCoins, single stack");
                //System.out.println("8. Export 10000 25-CloudCoins, single stack");
                //System.out.println("9. Export 100000 250-CloudCoins, single stack");
                //System.out.println("0. Exit");



                int denomination;
                int sn;
                switch (input) {
                    case 1:
                        System.out.println("1. Export 2 1-CloudCoins, multiple files");
                        denomination = 1;
                        sn = denominationToSN(denomination);
                        saveCloudCoin(makeCloudCoin(sn), sn, denomination);
                        saveCloudCoin(makeCloudCoin(++sn), sn, denomination);
                        saveCommand(makeCommand("2", "0", ""));

                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Exporter-Java.jar\" C:\\CloudCoin\\Accounts\\DefaultUser\\ singleRun");
                        if(Files.exists(Paths.get(DefaultPath + "Export\\" + denomination + ".CloudCoin.1." + --sn  + ".stack")) &&
                                Files.exists(Paths.get(DefaultPath + "Export\\" + denomination + ".CloudCoin.1." + sn + ".stack"))) {
                            System.out.println("TEST 1 SUCCESS");
                        }
                        else{
                            System.out.println("TEST 1 FAILED: Test coin not found in Export folder.");
                        }
                        break;
                    case 2:
                        System.out.println("2. Export 2 5-CloudCoins, single stack");
                        denomination = 5;
                        sn = denominationToSN(denomination);
                        saveCloudCoin(makeCloudCoin(sn), sn, denomination);
                        saveCloudCoin(makeCloudCoin(++sn), sn, denomination);
                        saveCommand(makeCommand("10", "1", ""));

                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Exporter-Java.jar\" C:\\CloudCoin\\Accounts\\DefaultUser\\ singleRun");
                        if(Files.exists(Paths.get(DefaultPath + "Export\\" + (denomination * 2) + ".CloudCoins"  + ".stack"))) {
                            System.out.println("TEST 2 SUCCESS");
                        }
                        else{
                            System.out.println("TEST 2 FAILED: Test coin not found in Export folder.");
                        }
                        break;
                    case 3:
                        System.out.println("3. Export 2 25-CloudCoins, single stack");
                        denomination = 25;
                        sn = denominationToSN(denomination);
                        saveCloudCoin(makeCloudCoin(sn), sn, denomination);
                        saveCloudCoin(makeCloudCoin(++sn), sn, denomination);
                        saveCommand(makeCommand("50", "1", ""));

                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Exporter-Java.jar\" C:\\CloudCoin\\Accounts\\DefaultUser\\ singleRun");
                        if(Files.exists(Paths.get(DefaultPath + "Export\\" + (denomination * 2) + ".CloudCoins" + ".stack")) ) {
                            System.out.println("TEST 3 SUCCESS");
                        }
                        else{
                            System.out.println("TEST 3 FAILED: Test coin not found in Export folder.");
                        }
                        break;
                    case 4:
                        System.out.println("4. Export 1 100-CloudCoins, jpg");
                        denomination = 100;
                        sn = denominationToSN(denomination);
                        saveCloudCoin(makeCloudCoin(sn), sn, denomination);
                        saveCommand(makeCommand("100", "2", ""));

                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Exporter-Java.jar\" C:\\CloudCoin\\Accounts\\DefaultUser\\ singleRun");
                        if(Files.exists(Paths.get(DefaultPath + "Export\\" + denomination + ".CloudCoin.1."+ sn  + ".jpg")) ) {
                            System.out.println("TEST 4 SUCCESS");
                        }
                        else{
                            System.out.println("TEST 4 FAILED: Test coin not found in Export folder.");
                        }
                        break;
                    case 5:
                        System.out.println("5. Export 3 250-CloudCoin, csv");
                        denomination = 250;
                        sn = denominationToSN(denomination);
                        saveCloudCoin(makeCloudCoin(sn), sn, denomination);
                        saveCloudCoin(makeCloudCoin(++sn), sn, denomination);
                        saveCloudCoin(makeCloudCoin(++sn), sn, denomination);
                        saveCommand(makeCommand("750", "3", ""));

                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Exporter-Java.jar\" C:\\CloudCoin\\Accounts\\DefaultUser\\ singleRun");
                        if(Files.exists(Paths.get(DefaultPath + "Export\\" + (denomination * 3) + ".CloudCoins" + ".csv")) ){
                            System.out.println("TEST 5 SUCCESS");
                        }
                        else{
                            System.out.println("TEST 5 FAILED: Test coin not found in Export folder.");
                        }
                        break;

                    case 6:

                        denomination = 100;
                        sn = denominationToSN(denomination);
                        saveCloudCoin(makeCloudCoin(sn), sn, denomination);
                        saveCloudCoin(makeCloudCoin(++sn), sn, denomination);
                        saveCloudCoin(makeCloudCoin(++sn), sn, denomination);
                        saveCloudCoin(makeCloudCoin(++sn), sn, denomination);
                        saveCloudCoin(makeCloudCoin(++sn), sn, denomination);
                        int unusedDenomination = 250;
                        sn = denominationToSN(unusedDenomination);
                        saveCloudCoin(makeCloudCoin(sn), sn, denomination);
                        saveCloudCoin(makeCloudCoin(++sn), sn, denomination);
                        saveCommand(makeCommandAlt("500", "1", "", 0, 0, 0, 5, 0));
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Exporter-Java.jar\" C:\\CloudCoin\\Accounts\\DefaultUser\\ singleRun");
                        if(Files.exists(Paths.get(DefaultPath + "Export\\" + (denomination * 5) + ".CloudCoins"  + ".stack"))) {
                            System.out.println("TEST 6 SUCCESS");
                        }
                        else{
                            System.out.println("TEST 6 FAILED: Test coin not found in Export folder.");
                        }
                        break;

                        /*
                    case 7:
                        denomination = 1;
                        sn = denominationToSN(denomination);
                        for (int i = 0; i < 1000; i++) {
                            saveCloudCoin(makeCloudCoin(sn), sn++, denomination);
                        }
                        saveCommand(makeCommand("1000", "1", ""));
                        break;
                    case 8:
                        denomination = 1;
                        sn = denominationToSN(denomination);
                        for (int i = 0; i < 10000; i++) {
                            saveCloudCoin(makeCloudCoin(sn), sn++, denomination);
                        }
                        saveCommand(makeCommand("10000", "1", ""));
                        break;
                    case 9:
                        denomination = 1;
                        sn = denominationToSN(denomination);
                        for (int i = 0; i < 100000; i++) {
                            saveCloudCoin(makeCloudCoin(sn), sn++, denomination);
                        }
                        saveCommand(makeCommand("100000", "1", ""));
                        break;
                        */
                    default:
                    case 7:
                        System.out.println("All Tests Complete. Clearing out Folders used in testing.");
                        TestUtils.FlushFolder("Bank");
                        TestUtils.FlushFolder("Export");
                        return;
                }
                input++;
            } catch (Exception e) {
                System.out.println("Uncaught exception - " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }

    public static void saveCloudCoin(byte[] cloudCoin, int sn, int denomination) throws IOException {
        String filename = ensureFilenameUnique(denomination + ".CloudCoin.1." + sn,
                ".stack", DefaultPath + "Bank\\");
        Files.createDirectories(Paths.get(DefaultPath + "Bank\\"));
        Files.write(Paths.get(DefaultPath + "Bank\\" + filename), cloudCoin);
    }

    public static void saveCommand(byte[] command) throws IOException {
        String filename = ensureFilenameUnique("exporter"// + LocalDateTime.now().format(timestampFormat)
                , ".command", CommandFolder);
        Files.createDirectories(Paths.get(CommandFolder));
        Files.write(Paths.get(CommandFolder + filename), command);
    }

    public static byte[] makeCommand(String amount, String type, String tag) {
        return ("{\n" +
                "  \"command\": \"exporter\",\n" +
                "  \"account\": \"default\",\n" +
                "  \"amount\": " + amount + ",\n" +
                "  \"type\": " + type + ",\n" +
                "  \"tag\": \"" + tag + "\"\n" +
                "}").getBytes();
    }

    public static byte[] makeCommandAlt(String amount, String type, String tag, int ones, int fives, int twentyfives, int hundreds, int twohundredfifties) {
        return ("{\n" +
                "  \"command\": \"exporter\",\n" +
                "  \"account\": \"default\",\n" +
                "  \"amount\": " + amount + ",\n" +
                "  \"1s\": " + ones + ",\n" +
                "  \"5s\": " + fives + ",\n" +
                "  \"25s\": " + twentyfives + ",\n" +
                "  \"100s\": " + hundreds + ",\n" +
                "  \"250s\": " + twohundredfifties + ",\n" +
                "  \"type\": " + type + ",\n" +
                "  \"tag\": \"" + tag + "\"\n" +
                "}").getBytes();
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
}