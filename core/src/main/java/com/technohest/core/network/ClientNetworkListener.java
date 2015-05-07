package com.technohest.core.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.technohest.core.model.*;
import com.technohest.core.network.Packet;
import com.technohest.core.network.RClient;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages how the client responds to input from the server.
 * Created by time on 2015-05-05.
 */
public class ClientNetworkListener extends Listener {
    private Client client;
    private RClient rclient;
    //Ska vara <Integer, CharType>
    private HashMap<Integer, Integer> playerIdTypeMap = new HashMap<Integer, Integer>();
    private Integer id = null;


    public void init(RClient rclient, Client client) {
        this.client = client;
        this.rclient = rclient;
    }

    @Override
    public void connected(Connection connection) {
        Log.info("Client: Trying to connect.");
    }

    @Override
    public void disconnected(Connection connection) {
        Log.info("Client: Trying to disconnect.");
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof Packet.Packet0PlayerID) {
            id = ((Packet.Packet0PlayerID)object).id;
            rclient.startGame(playerIdTypeMap, id);
        } else if (object instanceof Packet.Packet0PlayerIdJoined) {
            Integer newId = ((Packet.Packet0PlayerIdJoined)object).id;

            //-1 is a placeholder and should probably be an entry in CharType like NONE.
            playerIdTypeMap.put(newId, -1);
            Log.info("[Client]--" + playerIdTypeMap.toString());
        }
    }
}
