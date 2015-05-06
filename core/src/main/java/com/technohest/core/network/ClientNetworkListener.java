package com.technohest.core.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.technohest.core.network.Packet;
import com.technohest.core.network.RClient;

import java.util.ArrayList;

/**
 * Manages how the client responds to input from the server.
 * Created by time on 2015-05-05.
 */
public class ClientNetworkListener extends Listener {
    private Client client;
    private RClient rclient;
    private Connection server;


    public void init(RClient rclient, Client client) {
        this.client = client;
        this.rclient = rclient;
    }

    @Override
    public void connected(Connection connection) {
        Log.info("Client: Trying to connect.");
        server = connection;
    }

    @Override
    public void disconnected(Connection connection) {
        Log.info("Client: Trying to disconnect.");
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof Packet.Packet0PlayerID) {
            Integer id = ((Packet.Packet0PlayerID)object).id;
            rclient.setPlayerId(id);
        } else if (object instanceof Packet.Packet1PlayerIdMap) {
            ArrayList<Integer> list = ((Packet.Packet1PlayerIdMap)object).playerList;
            rclient.setPlayerIdList(list);
            Log.info("Client " + list.toString());
        }
    }
}
