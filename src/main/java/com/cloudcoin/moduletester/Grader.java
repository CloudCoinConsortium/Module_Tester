package com.cloudcoin.moduletester;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Grader {

    private static String RootPath = Main.RootPath;


    public Grader() {
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
            Files.createDirectories(Paths.get(RootPath + "Lost\\"));
            Files.createDirectories(Paths.get(RootPath + "Logs\\"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ShowCommandLineOutput() {
        Scanner reader = new Scanner(System.in);

        while (true) {
            try {
                System.out.println();
                System.out.println("1. Grade 1 CloudCoin (Passing)");
                System.out.println("2. Grade 1 CloudCoin (Fracked)");
                System.out.println("3. Grade 1 CloudCoin (Counterfeit)");
                System.out.println("4. Grade 1 CloudCoin (Lost)");
                System.out.println("5. Grade 4 CloudCoins (one of each)");
                System.out.println("6. Grade 10 CloudCoins (Passing)");
                System.out.println("7. Grade 40 CloudCoins (ten of each)");
                System.out.println("8. Grade 100 CloudCoins (Passing)");
                System.out.println("9. Grade 400 CloudCoins (hundred of each)");
                System.out.println("0. Exit");

                reader.hasNext();
                String input;
                try {
                    input = reader.next();
                } catch (Exception e) {
                    e.printStackTrace();
                    reader = new Scanner(System.in);
                    continue;
                }

                switch (input) {
                    case "1":
                        saveFile(makeCloudCoinPassing(1), 1);
                        break;
                    case "2":
                        saveFile(makeCloudCoinFracked(1), 1);
                        break;
                    case "3":
                        saveFile(makeCloudCoinCounterfeit(1), 1);
                        break;
                    case "4":
                        saveFile(makeCloudCoinLost(1), 1);
                        break;
                    case "5":
                        saveFile(makeCloudCoinPassing(1), 1);
                        saveFile(makeCloudCoinFracked(2), 2);
                        saveFile(makeCloudCoinCounterfeit(3), 3);
                        saveFile(makeCloudCoinLost(4), 4);
                        break;
                    case "6":
                        for (int i = 0; i < 10; i++)
                            saveFile(makeCloudCoinPassing(i), i);
                        break;
                    case "7":
                        for (int i = 0; i < 10; i++)
                            saveFile(makeCloudCoinPassing(i), i);
                        for (int i = 0; i < 10; i++)
                            saveFile(makeCloudCoinFracked(i), i);
                        for (int i = 0; i < 10; i++)
                            saveFile(makeCloudCoinCounterfeit(i), i);
                        for (int i = 0; i < 10; i++)
                            saveFile(makeCloudCoinLost(i), i);
                        break;
                    case "8":
                        for (int i = 0; i < 100; i++)
                            saveFile(makeCloudCoinPassing(i), i);
                        break;
                    case "9":
                        for (int i = 0; i < 100; i++)
                            saveFile(makeCloudCoinPassing(i), i);
                        for (int i = 0; i < 100; i++)
                            saveFile(makeCloudCoinFracked(i), i);
                        for (int i = 0; i < 100; i++)
                            saveFile(makeCloudCoinCounterfeit(i), i);
                        for (int i = 0; i < 100; i++)
                            saveFile(makeCloudCoinLost(i), i);
                        break;
                    case "0":
                        return;
                }
            } catch (Exception e) {
                System.out.println("Uncaught exception - " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }

    public static void saveFile(byte[] cloudCoin, int sn) throws IOException {
        String filename = ensureFilenameUnique("1.CloudCoin.1.0000" + sn + ".e054a34f2790fd3353ea26e5d92d9d2f",".stack", RootPath + "Detected\\");
        Files.write(Paths.get(RootPath + "Detected\\" + filename), cloudCoin);
    }

    public static byte[] makeCloudCoinPassing(int sn) {
        return makeCloudCoin(sn, "ppppppppppppppppppppppppp");
    }

    public static byte[] makeCloudCoinFracked(int sn) {
        return makeCloudCoin(sn, "ppppppppppppppppppppppppf");
    }

    public static byte[] makeCloudCoinCounterfeit(int sn) {
        return makeCloudCoin(sn, "pppppppppppppppppppf");
    }

    public static byte[] makeCloudCoinLost(int sn) {
        return makeCloudCoin(sn, "ppppppppppppppppppp");
    }

    public static byte[] makeCloudCoin(int sn, String pown) {
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
                "      \"pown\": \"" + pown + "\",\n" +
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
}
