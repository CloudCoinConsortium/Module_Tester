package com.cloudcoin.moduletester;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LossFixer {
    public LossFixer(){
        try{
            System.out.println("Starting LostFixer Test");

            System.out.println("Flushing Lost, Predetect, and Detected Folders");
            TestUtils.FlushFolder("Lost");
            TestUtils.FlushFolder("Predetect");
            TestUtils.FlushFolder("Detected");

            System.out.println("Creating test CloudCoin files");
            byte [] cc = makeCloudCoinLost( 1);
            TestUtils.saveFile(cc, 1, "Lost");
            TestUtils.saveFile(cc, 1, "Predetect");

            System.out.println("Activating Fake Raida for the last 6 Nodes");
            byte[] hostsTextBytes = ("13.91.224.175 raida19.cloudcoin.global" + System.getProperty("line.separator")
                    + "13.91.224.175 raida20.cloudcoin.global" + System.getProperty("line.separator")
                    + "13.91.224.175 raida21.cloudcoin.global" + System.getProperty("line.separator")
                    + "13.91.224.175 raida22.cloudcoin.global" + System.getProperty("line.separator")
                    + "13.91.224.175 raida23.cloudcoin.global" + System.getProperty("line.separator")
                    + "13.91.224.175 raida24.cloudcoin.global" + System.getProperty("line.separator")
            ).getBytes();
            Files.write(Paths.get("C:\\Windows\\system32\\drivers\\etc\\hosts"),hostsTextBytes);

            System.out.println("Starting LossFixer");
            //runProcess("javac -cp src \"C:\\Program Files\\CloudCoin\\Main.java\"");
            TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-LossFixer-Java.jar\" C:\\CloudCoin test");

            System.out.println("Asserting that test CloudCoin has been fixed");
            String[] DetectedFileNames = TestUtils.selectFileNamesInFolder(Main.RootPath + "Detected" +File.separator);
            if(DetectedFileNames.length > 0 && Files.exists(Paths.get(Main.RootPath + "Detected" +File.separator + DetectedFileNames[0]))){
                System.out.println("Found file in Detected Folder");
                String expectedValue = new String(makeCloudCoinFixed(1));
                String actualValue = new String(Files.readAllBytes(Paths.get(Main.RootPath + "Detected" +File.separator + DetectedFileNames[0])));
                if(expectedValue.equals(actualValue)){
                    System.out.println("Test coin is fixed. TEST SUCCESSFUL");
                }else{
                    System.out.println("Test coin doesn't match expected result. TEST INCONCLUSIVE OR FAILED");
                    System.out.println("Printing coin data to console:");
                    System.out.println(actualValue);
                }
                Files.deleteIfExists(Paths.get(Main.RootPath + "Detected" +File.separator + DetectedFileNames[0]));
            } else {
                System.out.println("No files in Detected Folder. TEST FAILED");
            }

            Files.write(Paths.get("C:\\Windows\\system32\\drivers\\etc\\hosts"), ("#" + System.getProperty("line.separator")).getBytes());
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.println("LossFixer Test Completed");

    }

    public static byte[] makeCloudCoinLost(int sn) {
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
                "      \"pown\": \"pppppppppppppppppppnnnnnn\",\n" +
                "      \"aoid\": []\n" +
                "    }\n" +
                "  ]\n" +
                "}").getBytes();
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
