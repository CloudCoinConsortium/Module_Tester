public class Unpacker {

    private static String RootPath = Main.RootPath;


    public Unpacker() {
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
            Files.createDirectories(Paths.get(RootPath + "Suspect\\"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ShowCommandLineOutput() {
        Scanner reader = new Scanner(System.in);

        while (true) {
            try {
                System.out.println();
                System.out.println("1. Unpack CloudCoin (Single Stack)");
                System.out.println("2. Unpack CloudCoins (Multi Stack: 2)");
                System.out.println("3. Unpack CloudCoin (JPG)");
                System.out.println("4. Unpack 4 CloudCoin files (one of each type)");
                System.out.println("5. Unpack CloudCoins (Multi Stack: 100)");
                System.out.println("6. Unpack CloudCoins (Multi Stack: 1000)");
                System.out.println("7. Unpack CloudCoins (Multi Stack: 10000)");
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
                        saveFile(makeCloudCoinSingle(1), 1, ".stack");
                        break;
                    case "2":
                        saveFile(makeCloudCoinStack(1), 1, ".stack");
                        break;
                    case "3":
                        saveFile(makeCloudCoinJpg(), 1, ".jpg");
                        break;
                    case "4":
                        saveFile(makeCloudCoinSingle(1), 1, ".stack");
                        saveFile(makeCloudCoinStack(2), 2, ".stack");
                        saveFile(makeCloudCoinJpg(), 3, ".jpg");
                        break;
                    case "5":
                        saveFile(makeCloudCoinStackCustom(2, 100), 2, ".stack");
                        break;
                    case "6":
                        saveFile(makeCloudCoinStackCustom(2, 1000), 2, ".stack");
                        break;
                    case "7":
                        saveFile(makeCloudCoinStackCustom(2, 10000), 2, ".stack");
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

    public static void saveFile(byte[] cloudCoin, int sn, String extension) throws IOException {
        String filename = ensureFilenameUnique("1.CloudCoin.1.0000" + sn + ".e054a34f2790fd3353ea26e5d92d9d2f",extension, RootPath + "Detected\\");
        Files.write(Paths.get(RootPath + "Import\\" + filename), cloudCoin);
    }

    public static byte[] makeCloudCoinSingle(int sn) {
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

    public static byte[] makeCloudCoinStack(int sn) {
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
                "    },\n" +
                "    {\n" +
                "      \"nn\": 1,\n" +
                "      \"sn\": " + sn + 100 + ",\n" +
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
                "    },\n" +
                "    {\n" +
                "      \"nn\": 1,\n" +
                "      \"sn\": " + sn + 200 + ",\n" +
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

    public static byte[] makeCloudCoinJpg() throws IOException {
        return Files.readAllBytes(Paths.get("1.CloudCoin.1.1346931.jpg"));
    }

    public static byte[] makeCloudCoinStackCustom(int sn, int size) {
        StringBuilder cloudCoin = new StringBuilder("{\n" +
                "  \"cloudcoin\": [\n");
        for (int i = 0; i < size; i++) {
            cloudCoin.append("    {\n" +
                    "      \"nn\": 1,\n" +
                    "      \"sn\": " + (sn + i) + ",\n" +
                    "      \"an\": [\n");
            for (int j = 0; j < 24; j++)
                cloudCoin.append("        \"00000000000000000000000000000000\",\n");
            cloudCoin.append("        \"00000000000000000000000000000000\"\n");
            cloudCoin.append("      ],\n" +
                    "      \"ed\": \"11-2020\",\n" +
                    "      \"pown\": \"ppppppppppppppppppppppppp\",\n" +
                    "      \"aoid\": []\n" +
                    "    }");
            if (i != size - 1)
                cloudCoin.append(",\n");
        }
        cloudCoin.append("\n" +
                "  ]\n" +
                "}");
        return cloudCoin.toString().getBytes();
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
