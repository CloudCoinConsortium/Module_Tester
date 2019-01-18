package com.cloudcoin.moduletester;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

public class FrackFixer {
    static String TestCoinName = "100.CloudCoin.1.6377544..stack";
    public FrackFixer(){



        Instant start = Instant.now();
        StartFrackFixTest();
        Instant end = Instant.now();
        System.out.println("One Tests,Time Elapsed: " + Duration.between(start, end).toMillis() + "ms");

    }

    public static void StartFrackFixTest(){
        try{
            System.out.println("Starting LostFixer Test");

            System.out.println("Flushing Fracked, Bank, and Detected Folders");
            TestUtils.FlushFolder("Bank");
            TestUtils.FlushFolder("Fracked");


            System.out.println("Creating test CloudCoin files");

            Files.copy(Paths.get("C:\\CloudCoin\\TestCoin\\" + TestCoinName), Paths.get(Main.RootPath + "Fracked\\" + TestCoinName));

            System.out.println("Starting FrackFixer");
            //runProcess("javac -cp src \"C:\\Program Files\\CloudCoin\\Main.java\"");
            TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-FrackFixer-Java.jar\" C:\\CloudCoin\\Accounts\\DefaultUser\\ singleRun");

            System.out.println("Asserting that test CloudCoin has been fixed");
            String[] BankFileNames = TestUtils.selectFileNamesInFolder(Main.RootPath + "Bank" + File.separator);
            if(BankFileNames.length > 0 && Files.exists(Paths.get(Main.RootPath + "Bank" +File.separator + BankFileNames[0]))){
                System.out.println("Found file in Bank Folder");

                if(BankFileNames[0] == TestCoinName){
                    System.out.println("Test coin is fixed. TEST SUCCESSFUL");
                }else{
                    System.out.println("Test coin not found in Bank Folder. TEST INCONCLUSIVE OR FAILED");

                }
                Files.deleteIfExists(Paths.get(Main.RootPath + "Bank" +File.separator + BankFileNames[0]));
            } else {
                System.out.println("No files in Bank Folder. TEST FAILED");
            }


        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.println("FrackFixer Test Completed. Clearing out Folder used in testing.");
        TestUtils.FlushFolder("Fracked");
        TestUtils.FlushFolder("Bank");


    }



    public static byte[] makeCloudCoinFixed(int sn) {
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
}
