package com.technohest.core.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

/**
 * Manages how the server behaves depending on input from connected clients.
 * Created by time on 2015-05-05.
 */
public class ServerNetworkListener extends Listener {
    @Override
    public void connected(Connection connection) {
        Log.info("Server: Someone is connecting.");
    }

    @Override
    public void disconnected(Connection connection) {
        Log.info("Server: Someone is disconnecting.");
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof Packet.Packet0PlayerID) {
            Log.info("YELO");
            connection.sendTCP(new Packet.Packet0PlayerID());
        }
    }
}
