package com.technohest.core.menu;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import com.technohest.core.model.Character;
import com.technohest.core.network.Packet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Creates a client with specified port and/or ip and registers the packets the client will listen to.
 * Created by time on 2015-05-05.
 */
public class RClient {
    private Client client;
    private Integer id = null;
    private ArrayList<Integer> playerIdList= new ArrayList<Integer>();

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
        kryo.register(Packet.Packet1PlayerIdMap.class);
        kryo.register(ArrayList.class);
    }

    public void setPlayerId(Integer id) {
        this.id = id;
    }

    public void setPlayerIdList(ArrayList<Integer> list) {
        this.playerIdList = list;
    }
}
