package com.cloudcore.desktop.core;

import com.cloudcore.desktop.utils.SimpleLogger;
import com.cloudcore.desktop.utils.Utils;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class RAIDA {
    /* Fields */

    public static SimpleLogger logger;

    public static RAIDA mainNetwork;
    public static ArrayList<RAIDA> networks = new ArrayList<>();

    public Node[] nodes = new Node[Config.nodeCount];

    public ArrayList<CloudCoin> coins;
    public Response[] responseArray = new Response[Config.nodeCount];

    public int networkNumber = 1;


    /* Constructors */

    private RAIDA() {
        for (int i = 0; i < Config.nodeCount; i++) {
            nodes[i] = new Node(i + 1);
        }
    }

    private RAIDA(Network network) {
        nodes = new Node[network.raida.length];
        this.networkNumber = network.nn;
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node(i + 1, network.raida[i]);
        }
    }

    // Return Main RAIDA Network populated with default Nodes Addresses(Network 1)
    public static RAIDA getInstance() {
        if (mainNetwork != null)
            return mainNetwork;
        else {
            mainNetwork = new RAIDA();
            return mainNetwork;
        }
    }

    public static RAIDA getInstance(Network network) {
        return new RAIDA(network);
    }

    public ArrayList<CompletableFuture<Response>> getEchoTasks() {
        ArrayList<CompletableFuture<Response>> echoTasks = new ArrayList<>();
        for (int i = 0; i < nodes.length; i++) {
            echoTasks.add(nodes[i].echo());
        }
        return echoTasks;
    }

    // This method was introduced breaking the previously used Singleton pattern.
    // This was done in order to support multiple networks concurrently.
    // We can now have multiple RAIDA objects each containing different networks
    // RAIDA details are read from Directory URL first.
    // In case of failure, it falls back to a file on the file system
    public static ArrayList<RAIDA> instantiate() {
        String nodesJson = "";
        networks.clear();

        try {
            nodesJson = Utils.getHtmlFromURL(Config.URL_DIRECTORY);
        } catch (Exception e) {
            System.out.println(": " + e.getLocalizedMessage());
            e.printStackTrace();
            if (!Files.exists(Paths.get("directory.json"))) {
                System.out.println("RAIDA instantiation failed. No Directory found on server or local path");
                System.exit(-1);
                return null;
            }
            try {
                nodesJson = new String(Files.readAllBytes(Paths.get(Paths.get("").toAbsolutePath().toString()
                        + File.separator + "directory.json")));
            } catch (IOException e1) {
                System.out.println("| " + e.getLocalizedMessage());
                e1.printStackTrace();
            }
        }

        try {
            Gson gson = Utils.createGson();
            RAIDADirectory dir = gson.fromJson(nodesJson, RAIDADirectory.class);

            for (Network network : dir.networks) {
                System.out.println("Available Networks: " + network.raida[0].urls[0].url + " , " + network.nn);
                networks.add(RAIDA.getInstance(network));
            }
        } catch (Exception e) {
            System.out.println("RAIDA instantiation failed. No Directory found on server or local path");
            e.printStackTrace();
            System.exit(-1);
        }

        if (networks == null || networks.size() == 0) {
            System.out.println("RAIDA instantiation failed. No Directory found on server or local path");
            System.exit(-1);
            return null;
        }
        return networks;
    }








    public int getReadyCount() {
        int counter = 0;
        for (Node node : nodes) {
            if (Node.NodeStatus.Ready == node.RAIDANodeStatus)
                counter++;
        }
        return counter;
    }
    public int getNotReadyCount() {
        int counter = 0;
        for (Node node : nodes) {
            if (Node.NodeStatus.NotReady == node.RAIDANodeStatus)
                counter++;
        }
        return counter;
    }

    public static void updateLog(String message) {
        System.out.println(message);
        //logger.Info(message);
    }
}
