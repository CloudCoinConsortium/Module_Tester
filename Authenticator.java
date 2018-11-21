public class Authenticator {

    private static String RootPath = Main.RootPath;


    public Authenticator() {
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
                System.out.println("1. Authenticate 1 CloudCoin (Counterfeit)");
                System.out.println("2. Authenticate 10 CloudCoins (Counterfeit)");
                System.out.println("3. Authenticate 100 CloudCoins (Counterfeit)");
                System.out.println("4. Authenticate 400 CloudCoins (Counterfeit)");
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
                        saveFile(makeCloudCoinCounterfeit(1), 1);
                        break;
                    case "2":
                        for (int i = 0; i < 10; i++)
                            saveFile(makeCloudCoinCounterfeit(1 + i), 1 + i);
                        break;
                    case "3":
                        for (int i = 0; i < 100; i++)
                            saveFile(makeCloudCoinCounterfeit(1 + i), 1 + i);
                        break;
                    case "4":
                        for (int i = 0; i < 1000; i++)
                            saveFile(makeCloudCoinCounterfeit(1 + i), 1 + i);
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
        Files.write(Paths.get(RootPath + "Suspect\\" + filename), cloudCoin);
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
