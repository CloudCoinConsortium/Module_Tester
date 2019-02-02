package com.cloudcoin.moduletester;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

public class Eraser {
    private static String RootPath = Main.RootPath;
    private static String LogsFolder = "C:\\CloudCoin\\Logs\\";
    private static String ReceiptsFolder = "C:\\CloudCoin\\Receipts\\";
    private static String CommandsFolder = RootPath + "Command\\";
    private static String UserLogsFolder = RootPath + "Logs\\";
    private static String UserReceiptsFolder = RootPath + "Receipts\\";


    public Eraser() {
        createDirectories();

        Instant start = Instant.now();
        ShowCommandLineOutput();
        Instant end = Instant.now();
        System.out.println("One Test,Time Elapsed: " + Duration.between(start, end).toMillis() + "ms");
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

        //TestUtils.FlushFolder("Logs");
        //TestUtils.FlushFolder("Receipts");
        //TestUtils.FlushFolder("Command");


        while (input < 5) {
            try {

                switch (input) {
                    case 0:
                        System.out.println("1. Erasing Logs Folder Contents");
                        TestUtils.saveDummyFile(LogsFolder);
                        saveCommand(makeCommand());
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Eraser-Java.jar\" C:\\CloudCoin\\Accounts\\DefaultUser\\ singleRun");
                        if(!Files.exists(Paths.get(LogsFolder + "TestDummyFile.txt"))) {
                            System.out.println("TEST 1 SUCCESS");
                        }
                        else{
                            System.out.println("TEST 1 FAILED:  Files Still in Folder ");
                        }
                        break;
                    case 1:
                        System.out.println("1. Erasing Default Account Logs, Command, and Receipts Folder");
                        TestUtils.saveDummyFile(UserLogsFolder);
                        saveCommand(makeCommand());
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Eraser-Java.jar\" C:\\CloudCoin\\Accounts\\DefaultUser\\ singleRun");
                        if(!Files.exists(Paths.get(UserLogsFolder+ "TestDummyFile.txt"))) {
                            System.out.println("TEST 1 SUCCESS");
                        }
                        else{
                            System.out.println("TEST 1 FAILED:  One or more Folders still Exist");
                        }
                        break;
                    case 7:
                        System.out.println("3. Erasing Receipts Folder Contents");
                        TestUtils.saveDummyFile(ReceiptsFolder);
                        saveCommand(makeCommand());
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Eraser-Java.jar\" C:\\CloudCoin\\Accounts\\DefaultUser\\ singleRun");
                        if(!Files.exists(Paths.get(ReceiptsFolder + "TestDummyFile.txt"))) {
                            System.out.println("TEST 3 SUCCESS");
                        }
                        else{
                            System.out.println("TEST 3 FAILED:  Files Still in Folder ");
                        }
                        break;
                    case 2:
                        System.out.println("2. Erasing Default Account Receipts Folder Contents");
                        TestUtils.saveDummyFile(UserReceiptsFolder);
                        saveCommand(makeCommand());
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Eraser-Java.jar\" C:\\CloudCoin\\Accounts\\DefaultUser\\ singleRun");
                        if(!Files.exists(Paths.get(UserReceiptsFolder + "TestDummyFile.txt"))) {
                            System.out.println("TEST 2 SUCCESS");
                        }
                        else{
                            System.out.println("TEST 2 FAILED:  Files Still in Folder ");
                        }
                        break;
                    case 3:
                        System.out.println("3. Erasing Default Account Command Folder Contents");
                        TestUtils.saveDummyFile(CommandsFolder);
                        saveCommand(makeCommand());
                        TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Eraser-Java.jar\" C:\\CloudCoin\\Accounts\\DefaultUser\\ singleRun");
                        if(!Files.exists(Paths.get(CommandsFolder + "TestDummyFile.txt"))) {
                            System.out.println("TEST 3 SUCCESS");
                        }
                        else{
                            System.out.println("TEST 3 FAILED:  Files Still in Folder ");
                        }
                        break;
                    case 4:
                        System.out.println("All Tests Complete. Clearing out Folders used in testing.");

                        TestUtils.FlushFolder("Logs");
                        TestUtils.FlushFolder("Receipts");
                        TestUtils.FlushFolder("Command");


                        return;
                }
                input++;
            } catch (Exception e) {
                System.out.println("Uncaught exception - " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }


    public static void saveCommand(byte[] command) throws IOException {
        String filename = TestUtils.ensureFilenameUnique("eraser"// + LocalDateTime.now().format(timestampFormat)
                , ".command", CommandsFolder);
        Files.createDirectories(Paths.get(CommandsFolder));
        Files.write(Paths.get(CommandsFolder + filename), command);
    }

    public static byte[] makeCommand() {
        return ("{\n" +
                "  \"command\": \"eraser\",\n" +
                "  \"account\": \"default\"\n" +

                "}").getBytes();
    }
}
