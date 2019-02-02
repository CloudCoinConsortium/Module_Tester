package com.cloudcoin.moduletester;



import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

public class LossFixer {
    static String TestCoinName = "100.CloudCoin.1.6377544..stack";
    static String CommandFolder = Main.RootPath + "Command\\";

    public LossFixer(){

        Instant start = Instant.now();
        StartLossFixTest();
        Instant end = Instant.now();
        System.out.println("One Tests,Time Elapsed: " + Duration.between(start, end).toMillis() + "ms");
    }

    public static void StartLossFixTest(){
        try{
            System.out.println("Starting LostFixer Test");

            System.out.println("Flushing Lost, Predetect, and Detected Folders");
            TestUtils.FlushFolder("Lost");
            TestUtils.FlushFolder("Predetect");
            TestUtils.FlushFolder("Detected");

            System.out.println("Creating test CloudCoin files");
            Files.copy(Paths.get("C:\\CloudCoin\\TestCoin\\" + TestCoinName), Paths.get(Main.RootPath + "Lost\\" + TestCoinName));
            Files.copy(Paths.get("C:\\CloudCoin\\TestCoin\\" + TestCoinName), Paths.get(Main.RootPath + "Predetect\\" + TestCoinName));
            /*
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
*/
            System.out.println("Starting LossFixer");
            //runProcess("javac -cp src \"C:\\Program Files\\CloudCoin\\Main.java\"");
            //saveCommand(makeCommand());
            TestUtils.runProcess("java -jar \"C:\\Program Files\\CloudCoin\\CloudCore-LossFixer-Java.jar\" C:\\CloudCoin\\Accounts\\DefaultUser\\ singleRun");

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

            //Files.write(Paths.get("C:\\Windows\\system32\\drivers\\etc\\hosts"), ("#" + System.getProperty("line.separator")).getBytes());
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.println("LossFixer Test Completed. Clearing out Folder used in testing.");
        TestUtils.FlushFolder("Lost");
        TestUtils.FlushFolder("Predetect");
        TestUtils.FlushFolder("Detected");

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
                "      \"sn\": 6377544,\n" +
                "      \"an\": [\n" +
                "        \"ab98e1ecbe63bd8fadad02341080718b\",\n" +
                "        \"9a212b38b54425aac32298fb63bce34e\",\n" +
                "        \"04a2bad6561d9d3c40463e9753fad423\",\n" +
                "        \"65c0f1c2584dfa4daba4baa06c4aa278\",\n" +
                "        \"81803a48bbf65659153410cc53784c50\",\n" +
                "        \"b1bb85acb0e453a0587b85c7647e160a\",\n" +
                "        \"0d49ff61df95a2a7995cdf6402bbb66f\",\n" +
                "        \"91b85297853275b0721c7fc6eba58805\",\n" +
                "        \"d7c2674ed4d7679432f964b8ecf12d83\",\n" +
                "        \"fca0c2ce230c336865d60dcf65f005e5\",\n" +
                "        \"76207bc9c02b751b661c60a0117d2b54\",\n" +
                "        \"89572faa8ed256fc088eb7630e789407\",\n" +
                "        \"295d842960fc00f36e401ebc32f7411a\",\n" +
                "        \"97663ef430407cea253609b8a99ee511\",\n" +
                "        \"b66e5dd7672fe82527463b6d1696cd60\",\n" +
                "        \"fca55a99ecb537364709d4b0a03f2c77\",\n" +
                "        \"2de742abdf508010c2b6be4312a1a755\",\n" +
                "        \"1f3105a65eddef6e4898c2e69bc8c37b\",\n" +
                "        \"0eb32add188fec5d149c0a1421fdedb3\",\n" +
                "        \"7567fe47b64e5de70e656416757043cc\",\n" +
                "        \"aa8f3800374f41c89ea4eccaf5824042\",\n" +
                "        \"f33ff75320e4a52d319d0afc62d43fff\",\n" +
                "        \"745df4fc716aecb08c6a41c381950643\",\n" +
                "        \"759afad4289e3bf3195fd0ec3635d797\",\n" +
                "        \"553691fe8159674c2aede0040df35465\"\n" +
                "      ],\n" +
                "      \"ed\": \"9-2020\",\n" +
                "      \"pown\": \"pppppppppppppppppfppppppp\",\n" +
                "      \"aoid\": []\n" +
                "    }\n" +
                "  ]\n" +
                "}").getBytes();
    }

    public static void saveCommand(byte[] command) throws IOException {
        String filename = "lossfixer.command";
        Files.write(Paths.get(CommandFolder + filename), command);
    }

    public static byte[] makeCommand() {
        return ("{\n"
                + "  \"command\": \"lossfixer\",\n"
                + "  \"account\": \"default\"\n"
                + "}").getBytes();
    }
}
