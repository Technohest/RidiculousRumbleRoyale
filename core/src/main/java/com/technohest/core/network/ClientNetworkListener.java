package com.technohest.core.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.technohest.LibgdxService.StateGDX;
import com.technohest.core.controller.RActionListener;
import com.technohest.core.controller.RRRGameController;
import com.technohest.core.model.Correction;
import com.technohest.core.model.Action;

import java.util.*;

/**
 * Manages how the client responds to input from the server.
 * @author David Ström
 */
public class ClientNetworkListener extends Listener implements RActionListener {
    private Client client;
    private RClient rclient;
    private Connection server;

    private int lastSequenceNumber = 0;

    private RRRGameController controller;

    private int sequenceNumber = 0;

    //Will later be <Integer, CharType>
    private HashMap<Integer, Integer> playerIdTypeMap = new HashMap<Integer, Integer>();
    private Integer id = null;

    //Input list from local client.
    private List<Action> playerActions = new ArrayList<Action>();


    public void init(RClient rclient, Client client, RRRGameController controller) {
        this.client = client;
        this.rclient = rclient;
        this.controller = controller;
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
            clearOldActions(p.lastSeq);
            StateGDX.getInstance().setState(p.state.getCharacterIdStates(), p.state.getAttackIdStates());
        } else if (object instanceof Packet.Packet0PlayerID) {
            id = ((Packet.Packet0PlayerID)object).id;
        } else if (object instanceof Packet.Packet0PlayerTypeIdMap) {
            //Update the idPlayerMap
            playerIdTypeMap = ((Packet.Packet0PlayerTypeIdMap)object).map;
        } else if (object instanceof Packet.Packet0Start) {
            rclient.startGame(playerIdTypeMap, id);
        }
    }

    /**
     * Sets the lastSequenceNumber to be the last sequenceNumber received from the server, performed by the local
     * player. Then removes all the local actions with a sequence number less than the last received from the server.
     */
    private synchronized void clearOldActions(int seq) {
        //Update the lastSequenceNumber to be the last one received.
        lastSequenceNumber = seq;

        //Remove all local actions with a lower sequence number than the last received one.
        while (playerActions.size() > 0 &&
                playerActions.get(0).getSequenceNumber() <= lastSequenceNumber) {
            playerActions.remove(0);
        }
    }

    /**
     * Add action to the list of local actions, increase the sequenceNumber for every new entry.
     */
    public synchronized void addAction(Action.ActionID action) {
        Action a = new Action(id, action, sequenceNumber);
        playerActions.add(a);
        sequenceNumber++;
    }

    /**
     * Send the local actions to the server if there are any.
     */
    public synchronized void sendActionsToServerIfNecessary() {
        if (playerActions.size() > 0) {
            Packet.Packet1ActionList p = new Packet.Packet1ActionList();
            p.action = playerActions;
            server.sendUDP(p);
        }
    }

    /**
     * Send a packet to kill the server.
     */
    public void killServer() {
        server.sendTCP(new Packet.Packet2GameOver());
    }

    @Override
    public void actionReceived() {
        ArrayList<Action.ActionID> actions = controller.getActions();
        for (Action.ActionID a: actions) {
            addAction(a);
        }
        controller.clearActions();

        sendActionsToServerIfNecessary();
    }
}
