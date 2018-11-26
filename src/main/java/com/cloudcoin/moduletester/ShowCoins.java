package com.cloudcoin.moduletester;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ShowCoins {

    private static String RootPath = Main.RootPath;


    public ShowCoins() {
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
            Files.createDirectories(Paths.get(RootPath + "Bank\\"));
            Files.createDirectories(Paths.get(RootPath + "Fracked\\"));
			Files.createDirectories(Paths.get(RootPath + "Lost\\"));
			Files.createDirectories(Paths.get(RootPath + "Gallery\\"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ShowCommandLineOutput() {
        KeyboardReader reader = new KeyboardReader();

        while (true) {
            try {
                System.out.println();
                System.out.println("1. Show 1 CloudCoin (Bank)");
                System.out.println("2. Show 10 CloudCoins (Fracked)");
                System.out.println("3. Show 100 CloudCoins (Lost)");
                System.out.println("4. Show 400 CloudCoins (Bank)");
                System.out.println("0. Exit");

                int input = reader.readInt();

                switch (input) {
                    case 1:
                        saveFile(makeCloudCoinCounterfeit(1), 1, "Bank");
                        CreateCommand();
                        break;
                    case 2:
                        for (int i = 0; i < 5; i++)
                            saveFile(makeCloudCoinCounterfeit(1 + i), 1 + i, "Fracked");
						saveFile(makeCloudCoinCounterfeit(2097154), 2097154, "Fracked");
						CreateCommand();
                        break;
                    case 3:
                        saveFile(makeCloudCoinCounterfeit(6291458), 6291458, "Lost");
                        CreateCommand();
                        break;
                    case 4:
                        for (int i = 0; i < 4; i++)
                            saveFile(makeCloudCoinCounterfeit(14680066 + i), 14680066 + i, "Bank");
                        CreateCommand();
                        break;
                    case 0:
                        return;
                }
            } catch (Exception e) {
                System.out.println("Uncaught exception - " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }

    public static void saveFile(byte[] cloudCoin, int sn, String folder) throws IOException {
        String filename = ensureFilenameUnique(getDenomination(sn) + ".CloudCoin.1.0000" + sn + ".e054a34f2790fd3353ea26e5d92d9d2f",".stack", RootPath + folder + "\\");
        Files.write(Paths.get(RootPath + folder + "\\" + filename), cloudCoin);
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

    public static void CreateCommand() throws  IOException{
        byte[] empty = new byte[0];

        Files.write(Paths.get( "C:\\CloudCoinServer\\Command\\Showcoins.showcoins.txt"), empty);

    }
}
