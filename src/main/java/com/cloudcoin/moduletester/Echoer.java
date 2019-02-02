package com.cloudcoin.moduletester;

import java.io.IOException;
import java.nio.file.*;
import java.time.Duration;
import java.time.Instant;

public class Echoer {
    private static String CommandsFolder = "C:\\CloudCoin\\Command\\";

    public  Echoer(){
        Instant start = Instant.now();
        StartEchoerTest();
        Instant end = Instant.now();
        System.out.println("One Tests,Time Elapsed: " + Duration.between(start, end).toMillis() + "ms");
    }

    public static void StartEchoerTest(){
        try {

            saveCommand(makeCommand());
            TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Echoer-Java.jar\" C:\\CloudCoin\\ singleRun");
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(Main.LogsPath + "Echoer\\")))
            {
                for(Path p: stream)
                {
                    String logfilename = p.getFileName().toString();
                    if(logfilename.contains("ready"))
                        System.out.println("Found log for Raida: " + logfilename.split("_")[0]);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void saveCommand(byte[] command) throws IOException {
        String filename = TestUtils.ensureFilenameUnique("echoer"// + LocalDateTime.now().format(timestampFormat)
                , ".command", CommandsFolder);
        Files.createDirectories(Paths.get(CommandsFolder));
        Files.write(Paths.get(CommandsFolder + filename), command);
    }

    public static byte[] makeCommand() {
        return ("{\n" +
                "  \"command\": \"echo\"\n" +


                "}").getBytes();
    }

}
