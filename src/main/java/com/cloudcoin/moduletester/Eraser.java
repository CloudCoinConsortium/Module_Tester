package com.cloudcoin.moduletester;

import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Eraser {
    private static String RootPath = Main.RootPath;
    private static String LogsFolder = "C:\\CloudCoin\\Logs\\";
    private static String ReceiptsFolder = "C:\\CloudCoin\\Receipts\\";
    private static String CommandsFolder = "C:\\CloudCoin\\Command\\";
    private static String UserLogsFolder = RootPath + "Logs\\";
    private static String UserReceiptsFolder = RootPath + "Receipts\\";


    public Eraser() {
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
            Files.createDirectories(Paths.get(LogsFolder));
            Files.createDirectories(Paths.get(UserLogsFolder));
            Files.createDirectories(Paths.get(ReceiptsFolder));
            Files.createDirectories(Paths.get(UserReceiptsFolder));
            Files.createDirectories(Paths.get(CommandsFolder));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ShowCommandLineOutput() {
        KeyboardReader reader = new KeyboardReader();
        int input = 1;

        System.out.println("Starting test for Eraser");
        System.out.println("Emptying Associated Folders");
        TestUtils.FlushFolder("Logs");
        TestUtils.FlushFolder("Receipts");
        TestUtils.FlushSpecificFolder(LogsFolder);
        TestUtils.FlushSpecificFolder(ReceiptsFolder);
        TestUtils.FlushSpecificFolder(CommandsFolder);


        while (input < 7) {
            try {


                switch (input) {
                    case 1:
                        System.out.println("1. Erasing Logs Folder Contents");
                        TestUtils.saveDummyFile(LogsFolder);
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Eraser-Java.jar\" C:\\CloudCoin");
                        if(!Files.exists(Paths.get(LogsFolder + "TestDummyFile.txt"))) {
                            System.out.println("TEST 1 SUCCESS");
                        }
                        else{
                            System.out.println("TEST 1 FAILED:  Files Still in Folder ");
                        }
                        break;
                    case 2:
                        System.out.println("2. Erasing Default Account Logs Folder Contents");
                        TestUtils.saveDummyFile(UserLogsFolder);
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Eraser-Java.jar\" C:\\CloudCoin");
                        if(!Files.exists(Paths.get(UserLogsFolder + "TestDummyFile.txt"))) {
                            System.out.println("TEST 2 SUCCESS");
                        }
                        else{
                            System.out.println("TEST 2 FAILED:  Files Still in Folder ");
                        }
                        break;
                    case 3:
                        System.out.println("3. Erasing Receipts Folder Contents");
                        TestUtils.saveDummyFile(ReceiptsFolder);
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Eraser-Java.jar\" C:\\CloudCoin");
                        if(!Files.exists(Paths.get(ReceiptsFolder + "TestDummyFile.txt"))) {
                            System.out.println("TEST 3 SUCCESS");
                        }
                        else{
                            System.out.println("TEST 3 FAILED:  Files Still in Folder ");
                        }
                        break;
                    case 4:
                        System.out.println("4. Erasing Default Account Receipts Folder Contents");
                        TestUtils.saveDummyFile(UserReceiptsFolder);
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Eraser-Java.jar\" C:\\CloudCoin");
                        if(!Files.exists(Paths.get(UserReceiptsFolder + "TestDummyFile.txt"))) {
                            System.out.println("TEST 4 SUCCESS");
                        }
                        else{
                            System.out.println("TEST 4 FAILED:  Files Still in Folder ");
                        }
                        break;
                    case 5:
                        System.out.println("5. Erasing Command Folder Contents");
                        TestUtils.saveDummyFile(CommandsFolder);
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Eraser-Java.jar\" C:\\CloudCoin");
                        if(!Files.exists(Paths.get(CommandsFolder + "TestDummyFile.txt"))) {
                            System.out.println("TEST 5 SUCCESS");
                        }
                        else{
                            System.out.println("TEST 5 FAILED:  Files Still in Folder ");
                        }
                        break;
                    case 6:
                        System.out.println("All Tests Complete. Clearing out Folders used in testing.");
                        TestUtils.FlushFolder("Logs");
                        TestUtils.FlushFolder("Receipts");
                        TestUtils.FlushSpecificFolder(LogsFolder);
                        TestUtils.FlushSpecificFolder(ReceiptsFolder);
                        TestUtils.FlushSpecificFolder(CommandsFolder);
                        return;
                }
                input++;
            } catch (Exception e) {
                System.out.println("Uncaught exception - " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }
}
