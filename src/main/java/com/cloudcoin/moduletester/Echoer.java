package com.cloudcoin.moduletester;

import java.nio.file.*;

public class Echoer {

    public  Echoer(){
        try {
            TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-Echoer-Java.jar\" C:\\CloudCoin test");
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

}
