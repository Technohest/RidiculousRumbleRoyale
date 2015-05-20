package com.technohest.core.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.technohest.core.model.Action;

import java.util.HashMap;
import java.util.Vector;

/**
 * Manages how the client responds to input from the server.
 * Created by time on 2015-05-05.
 */
public class ClientNetworkListener extends Listener {
    private Client client;
    private RClient rclient;
    private Connection server;

    //Will later be <Integer, CharType>
    private HashMap<Integer, Integer> playerIdTypeMap = new HashMap<Integer, Integer>();
    private Integer id = null;

    //Input list from local client.
    private Vector<Action> playerActions = new Vector<Action>();


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
        if (object instanceof Packet.Packet1Correction) {
            Packet.Packet1Correction p = (Packet.Packet1Correction) object;
            rclient.correct(p.state, playerActions, p.actions);
        } else if (object instanceof Packet.Packet0PlayerID) {
            id = ((Packet.Packet0PlayerID)object).id;
        } else if (object instanceof Packet.Packet0PlayerTypeIdMap) {
            //Update the idPlayerMap
            playerIdTypeMap = ((Packet.Packet0PlayerTypeIdMap)object).map;
            Log.info("[Client]--" + playerIdTypeMap.toString());
        } else if (object instanceof Packet.Packet0Start) {
            rclient.startGame(playerIdTypeMap, id);
        }
    }

    public void addAction(Action.ActionID action, long time) {
        Action a = new Action(id, action, time);
        playerActions.add(a);
    }

    public void sendActionsToServerIfNecissary() {
        if (playerActions.size() > 0) {
            Packet.Packet1ActionList p = new Packet.Packet1ActionList();
            p.action = playerActions;
            server.sendUDP(p);
            playerActions.clear();
        }
    }

    public void killServer() {
        server.sendTCP(new Packet.Packet2GameOver());
    }

    public void sync() {
        Log.info("REQUESTING A CORRECTION BE SENT TO THE CLIENT.");
        server.sendTCP(new Packet.Packet5SyncEvent());
    }
}