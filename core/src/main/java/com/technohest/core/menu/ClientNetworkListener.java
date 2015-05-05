package com.technohest.core.menu;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.technohest.core.network.Packet;

/**
 * Manages how the client responds to input from the server.
 * Created by time on 2015-05-05.
 */
public class ClientNetworkListener extends Listener {
    private Client client;

    public void init(Client client) {
        this.client = client;
    }

    @Override
    public void connected(Connection connection) {
        Log.info("Client: Trying to connect.");
        client.sendTCP(new Packet.Packet0PlayerID());
    }

    @Override
    public void disconnected(Connection connection) {
        Log.info("Client: Trying to disconnect.");
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof Packet.Packet0PlayerID) {
            Log.info("ELO");
            connection.sendTCP(new Packet.Packet0PlayerID());
        }
    }
}
