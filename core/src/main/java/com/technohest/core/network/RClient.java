package com.technohest.core.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Creates a client with specified port and/or ip and registers the packets the client will listen to.
 * Created by time on 2015-05-05.
 */
public class RClient {
    private Client client;

    public RClient(String ip, String port) {
        client = new Client();
        registerPackets();

        ClientNetworkListener clientNetworkListener = new ClientNetworkListener();
        clientNetworkListener.init(this, client);

        client.addListener(clientNetworkListener);
        client.start();

        try {
            client.connect(5000, ip, Integer.parseInt(port));
        } catch (IOException e) {
            e.printStackTrace();
            client.stop();
        }

        Log.set(Log.LEVEL_INFO);
    }

    public RClient(String port) {
        client = new Client();
        registerPackets();

        ClientNetworkListener clientNetworkListener = new ClientNetworkListener();
        clientNetworkListener.init(this, client);

        client.addListener(clientNetworkListener);
        client.start();

        try {
            client.connect(5000, "127.0.0.1", Integer.parseInt(port));
        } catch (IOException e) {
            e.printStackTrace();
            client.stop();
        }

        Log.set(Log.LEVEL_INFO);
    }

    private void registerPackets() {
        Kryo kryo = client.getKryo();
        kryo.register(Packet.Packet0PlayerID.class);
        kryo.register(Packet.Packet0PlayerTypeIdMap.class);
        kryo.register(HashMap.class);
        kryo.register(Integer.class);
    }

    public void startGame(HashMap<Integer, Integer> playerIdTypeMap, Integer id) {
        //model.startGame(playerIdTypeMap, id);
        Log.info("STARTING GAME ON CLIENT.");
    }
}
