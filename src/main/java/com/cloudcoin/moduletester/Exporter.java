package com.cloudcoin.moduletester;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Exporter {

    private static String RootPath = "C:\\CloudCoinServer\\"
            .replace("\\", File.separator);


    private static String PasswordFolder = RootPath + "accounts\\Passwords" + File.separator;
    private static String CommandFolder = RootPath + "Command" + File.separator;

    private static final DateTimeFormatter timestampFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    public Exporter() {
        createDirectories();

        ShowCommandLineOutput();
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
            Files.createDirectories(Paths.get(RootPath));
            Files.createDirectories(Paths.get(CommandFolder));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ShowCommandLineOutput() {
        KeyboardReader reader = new KeyboardReader();

        while (true) {
            try {
                System.out.println();
                System.out.println("1. Export 2 1-CloudCoins, multiple files");
                System.out.println("2. Export 2 5-CloudCoins, single stack");
                System.out.println("3. Export 2 25-CloudCoins, single stack");
                System.out.println("4. Export 1 100-CloudCoins, jpg");
                System.out.println("5. Export 3 250-CloudCoin, csv");
                System.out.println("6. Export 381 CloudCoins, single stack");
                System.out.println("7. Export 1000 1-CloudCoins, single stack");
                System.out.println("8. Export 10000 25-CloudCoins, single stack");
                System.out.println("9. Export 100000 250-CloudCoins, single stack");
                System.out.println("0. Exit");

                int input = reader.readInt();

                int denomination;
                int sn;
                switch (input) {
                    case 1:
                        denomination = 1;
                        sn = denominationToSN(denomination);
                        saveCloudCoin(makeCloudCoin(sn), sn, denomination);
                        saveCloudCoin(makeCloudCoin(++sn), sn, denomination);
                        saveCommand(makeCommand("2", "0", ""));
                        break;
                    case 2:
                        denomination = 5;
                        sn = denominationToSN(denomination);
                        saveCloudCoin(makeCloudCoin(sn), sn, denomination);
                        saveCloudCoin(makeCloudCoin(++sn), sn, denomination);
                        saveCommand(makeCommand("10", "1", ""));
                        break;
                    case 3:
                        denomination = 25;
                        sn = denominationToSN(denomination);
                        saveCloudCoin(makeCloudCoin(sn), sn, denomination);
                        saveCloudCoin(makeCloudCoin(++sn), sn, denomination);
                        saveCommand(makeCommand("50", "1", ""));
                        break;
                    case 4:
                        denomination = 100;
                        sn = denominationToSN(denomination);
                        saveCloudCoin(makeCloudCoin(sn), sn, denomination);
                        saveCommand(makeCommand("100", "2", ""));
                        break;
                    case 5:
                        denomination = 250;
                        sn = denominationToSN(denomination);
                        saveCloudCoin(makeCloudCoin(sn), sn, denomination);
                        saveCloudCoin(makeCloudCoin(++sn), sn, denomination);
                        saveCloudCoin(makeCloudCoin(++sn), sn, denomination);
                        saveCommand(makeCommand("750", "3", ""));
                        break;
                    case 6:
                        denomination = 1;
                        sn = denominationToSN(denomination);
                        saveCloudCoin(makeCloudCoin(sn), sn, denomination);
                        denomination = 5;
                        sn = denominationToSN(denomination);
                        saveCloudCoin(makeCloudCoin(sn), sn, denomination);
                        denomination = 25;
                        sn = denominationToSN(denomination);
                        saveCloudCoin(makeCloudCoin(sn), sn, denomination);
                        denomination = 100;
                        sn = denominationToSN(denomination);
                        saveCloudCoin(makeCloudCoin(sn), sn, denomination);
                        denomination = 250;
                        sn = denominationToSN(denomination);
                        saveCloudCoin(makeCloudCoin(sn), sn, denomination);
                        saveCommand(makeCommand("381", "1", ""));
                        break;
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
                    default:
                    case 0:
                        return;
                }
            } catch (Exception e) {
                System.out.println("Uncaught exception - " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }

    public static void saveCloudCoin(byte[] cloudCoin, int sn, int denomination) throws IOException {
        String filename = ensureFilenameUnique(denomination + ".CloudCoin.1." + sn,
                ".stack", Main.RootPath + "Bank\\");
        Files.createDirectories(Paths.get(Main.RootPath + "Bank\\"));
        Files.write(Paths.get(Main.RootPath + "Bank\\" + filename), cloudCoin);
    }

    public static void saveCommand(byte[] command) throws IOException {
        String filename = ensureFilenameUnique("exporter" + LocalDateTime.now().format(timestampFormat),
                "", CommandFolder);
        Files.createDirectories(Paths.get(CommandFolder));
        Files.write(Paths.get(CommandFolder + filename), command);
    }

    public static byte[] makeCommand(String amount, String type, String tag) {
        return ("{\n" +
                "      \"command\": \"exporter\",\n" +
                "      \"account\": \"DefaultUser\",\n" +
                "      \"amount\": " + amount + ",\n" +
                "\t  \"type\": " + type + ",\n" +
                "\t  \"tag\": \"" + tag + "\"\n" +
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