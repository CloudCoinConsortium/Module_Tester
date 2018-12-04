package com.cloudcoin.moduletester;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reauthenticator {

    private static String RootPath = "C:\\CloudCoinServer\\"
            .replace("\\", File.separator);

    private static String CommandFolder = RootPath + "Command" + File.separator;

    private static final DateTimeFormatter timestampFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    public Reauthenticator() {
        createDirectories();

        ShowCommandLineOutput();
    }

    public static void setRootPath(String[] args) {
        if (args == null || args.length == 0)
            return;

        if (Files.isDirectory(Paths.get(args[0]))) {
            RootPath = args[0];
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
                System.out.println("1. Reauthenticate CloudCoins in Bank.");
                System.out.println("0. Exit");

                int input = reader.readInt();

                int denomination;
                int sn;
                switch (input) {
                    case 1:
                        denomination = 1;
                        sn = denominationToSN(denomination);
                        saveCloudCoin(makeCloudCoin(sn), sn, denomination);
                        saveCommand(makeCommand("reauthenticate", "DefaultUser"));
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
                ".stack", RootPath + "Bank\\");
        Files.createDirectories(Paths.get(RootPath + "Bank\\"));
        Files.write(Paths.get(RootPath + "Bank\\" + filename), cloudCoin);
    }

    public static void saveCommand(byte[] command) throws IOException {
        String filename = ensureFilenameUnique("reauthenticator" + LocalDateTime.now().format(timestampFormat),
                "", CommandFolder);
        Files.createDirectories(Paths.get(CommandFolder));
        Files.write(Paths.get(CommandFolder + filename), command);
    }

    public static byte[] makeCommand(String command, String account) {
        return ("{\n" +
                "      \"command\": \"" + command + "\",\n" +
                "      \"account\": \"" + account + "\"\n" +
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