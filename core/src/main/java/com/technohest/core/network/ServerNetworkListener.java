package com.technohest.core.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import java.util.ArrayList;

/**
 * Manages how the server behaves depending on input from connected clients.
 * Created by time on 2015-05-05.
 */
public class ServerNetworkListener extends Listener {
    private RServer server;
    private ArrayList<Connection> clients = new ArrayList<Connection>();

    public void init(RServer server) {
        this.server = server;
    }

    @Override
    public void connected(Connection connection) {
        Packet.Packet0PlayerID p1 = new Packet.Packet0PlayerID();
        Packet.Packet1PlayerIdMap p2 = new Packet.Packet1PlayerIdMap();

        //Adds connection and sets the id to be the current size of the connected clients.
        clients.add(connection);
        server.setPlayerIdMap(clients.size());

        //Creates a new packet with the id of the client's player and the mapping player and playerId.
        p1.id = clients.size();
        p2.playerList = server.getPlayerIdList();

        //Send the id to the newly connected client and sent the updated id/player-map to all the clients.
        for (Connection c: clients) {
            if (c.equals(connection)) {
                c.sendTCP(p1);
            }
            c.sendTCP(p2);
        }

        Log.info("Server: Someone is connecting.");
    }

    @Override
    public void disconnected(Connection connection) {
        Log.info("Server: Someone is disconnecting.");
    }

    @Override
    public void received(Connection connection, Object object) {
    }
}
