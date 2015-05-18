package com.technohest.core.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.technohest.core.handlers.InputHandler;
import com.technohest.core.model.Action;
import com.technohest.core.model.RRRGameModel;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Manages how the server behaves depending on input from connected clients.
 * Created by time on 2015-05-05.
 */
public class ServerNetworkListener extends Listener {
    private RServer server;
    //private HashMap<Integer, Connection> clients = new HashMap<Integer, Connection>();
    private DualHashBidiMap<Integer, Connection> clients = new DualHashBidiMap<Integer, Connection>();
    //Will later be <Integer, CharType>
    private HashMap<Integer, Integer> playerIdTypeMap = new HashMap<Integer, Integer>();
    private int id = 0;

    //The latest
    private HashMap<Integer, Integer> playerIdSequenceMap = new HashMap<Integer, Integer>();

    private ArrayList<Action> actionsToBePerformed = new ArrayList<Action>();

    public void init(RServer server) {
        this.server = server;
    }

    @Override
    public void connected(Connection connection) {
        Log.info("Server: Someone is connecting.");

        Packet.Packet0PlayerID p1 = new Packet.Packet0PlayerID();
        Packet.Packet0PlayerTypeIdMap p2 = new Packet.Packet0PlayerTypeIdMap();

        //Increments the id and sets the player id and connection map.
        id++;
        clients.put(id, connection);
        //-1 is a placeholder and will be changed to be a CharType
        playerIdTypeMap.put(id, -1);

        //Sets the new id to be sent to connecting client and to all other clients.
        p1.id = id;
        playerIdSequenceMap.put(id, 0);
        p2.map = playerIdTypeMap;

        //Send the id to the newly connected client and send the added id to all the clients.
        for (Connection c: clients.values()) {
            if (c.equals(connection)) {
                c.sendTCP(p1);
            }
            c.sendTCP(p2);
        }

        for (Connection c: clients.values()) {
            if (playerIdTypeMap.keySet().size() > 2) {
                server.startGame(playerIdTypeMap);
                c.sendTCP(new Packet.Packet0Start());
            }
        }

        Log.info(playerIdTypeMap.toString());
    }

    @Override
    public void disconnected(Connection connection) {
        Log.info("Server: Someone is disconnecting.");

        playerIdTypeMap.remove(clients.getKey(connection));
        clients.remove(clients.getKey(connection));

        for (Connection c: clients.values()) {
            Packet.Packet0PlayerTypeIdMap m = new Packet.Packet0PlayerTypeIdMap();
            m.map = playerIdTypeMap;
            c.sendTCP(m);
        }
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof Packet.Packet1ActionList) {
            Packet.Packet1ActionList p = (Packet.Packet1ActionList)object;
            addActionsToBePerformed(p.action);
        } else if (object instanceof Packet.Packet2GameOver) {
            server.gameOver();
        } else if (object instanceof Packet.Packet5SyncEvent) {
            server.sync();
        }
    }

    private void addActionsToBePerformed(Vector<Action> actions) {
        for (Action a: actions)
            actionsToBePerformed.add(a);
    }

    public void performActions(RRRGameModel model) {
        for (Action a: actionsToBePerformed) {
            if (playerIdSequenceMap.get(a.getPlayerID()) < a.getSequenceNumber()) {
                model.performAction(a);
                playerIdSequenceMap.remove(a.getPlayerID());
                playerIdSequenceMap.put(a.getPlayerID(), a.getSequenceNumber());
            }
        }


        actionsToBePerformed.clear();
    }
}
