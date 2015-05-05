package com.technohest.core.menu;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.technohest.core.network.Packet;

import java.io.IOException;
import java.net.InetAddress;

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
        clientNetworkListener.init(client);

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
        clientNetworkListener.init(client);

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
    }
}
