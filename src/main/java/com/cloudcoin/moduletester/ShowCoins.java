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
                        TestUtils.FlushFolder("Bank");
                        TestUtils.saveFile(makeCloudCoinCounterfeit(1), 1, "Bank");
                        CreateCommand();
                        break;
                    case 2:
                        TestUtils.FlushFolder("Fracked");
                        for (int i = 0; i < 5; i++)
                            TestUtils.saveFile(makeCloudCoinCounterfeit(1 + i), 1 + i, "Fracked");
                        TestUtils.saveFile(makeCloudCoinCounterfeit(2097154), 2097154, "Fracked");
						CreateCommand();
                        break;
                    case 3:
                        TestUtils.FlushFolder("Lost");
                        TestUtils.saveFile(makeCloudCoinCounterfeit(6291458), 6291458, "Lost");
                        CreateCommand();
                        break;
                    case 4:
                        TestUtils.FlushFolder("Bank");
                        for (int i = 0; i < 4; i++)
                            TestUtils.saveFile(makeCloudCoinCounterfeit(14680066 + i), 14680066 + i, "Bank");
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

    public static void CreateCommand() throws  IOException{
        byte[] command = "{\r\n \"command\": \"showcoins\",\r\n \"account\": \"default\" \r\n}".getBytes();

        Files.write(Paths.get( "C:\\CloudCoinServer\\Command\\Showcoins.showcoins.txt"), command);

    }
}
