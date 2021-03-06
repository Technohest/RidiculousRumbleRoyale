package com.technohest.core.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.technohest.LibgdxService.StateGDX;
import com.technohest.constants.Constants;
import com.technohest.core.model.Action;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages how the server behaves depending on input from connected clients.
 * @author David Ström
 */
public class ServerNetworkListener extends Listener {
    private RServer rserver;
    private HashMap<Integer, Connection> clients = new HashMap<Integer, Connection>();

    //Will later be <Integer, CharType>
    private HashMap<Integer, Integer> playerIdTypeMap = new HashMap<Integer, Integer>();
    private int id = 0;

    //The latest sequence number mapped with each player.
    private HashMap<Integer, Integer> playerIdSequenceMap = new HashMap<Integer, Integer>();
    private int numOfPlayers;

    public void init(RServer rserver) {
        this.rserver = rserver;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("assets/numberofplayers")));
            String line = reader.readLine();
            numOfPlayers = Integer.parseInt(line);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            numOfPlayers = 2;
        } catch (IOException e) {
            e.printStackTrace();
            numOfPlayers = 2;
        }
    }

    /**
     * Assignes the connected client an unique id and updates all connected clients playerIdTypeMap. Starts the game
     * when there are there are enough players connected as specified by a constant in constants package.
     * @param connection
     */
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
        playerIdSequenceMap.put(id, -1);
        p2.map = playerIdTypeMap;

        //Send the id to the newly connected client and send the added id to all the clients.
        for (Connection c: clients.values()) {
            if (c.equals(connection)) {
                c.sendTCP(p1);
            }
            c.sendTCP(p2);
        }

        //Start the game on all the clients
        for (Connection c: clients.values()) {
            if (playerIdTypeMap.keySet().size() >= this.numOfPlayers) {
                c.sendTCP(new Packet.Packet0Start());
            }
        }

        //Start the game on the server
        if (playerIdTypeMap.keySet().size() >= this.numOfPlayers) {
            rserver.startGame(playerIdTypeMap);
        }

        Log.info(playerIdTypeMap.toString());
    }

    /**
     * Removes the disconnected player and sends an update to all connected clients with the new playerIdTypeMap.
     * @param connection
     * the connection to be removed.
     */
    @Override
    public void disconnected(Connection connection) {
        Log.info("Server: Someone is disconnecting.");
        playerIdTypeMap.remove(connection.getID());

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
            rserver.gameOver();
        }
    }

    /**
     * Add actions to be performed by the server only if the actions sequenceNumber is a later one than the last one
     * processed. Update the latest sequenceNumber.
     * @param actions
     * the list of actions received over the network.
     */
    private void addActionsToBePerformed(List<Action> actions) {
        for (Action a: actions) {
            if (playerIdSequenceMap.get(a.getPlayerID()) < a.getSequenceNumber()) {
                playerIdSequenceMap.put(a.getPlayerID(), a.getSequenceNumber());
                rserver.addActionToBePerformed(a);
            }
        }
    }

    /**
     * Send the server game state to the clients and the last received sequence number of each respective player.
     */
    public void sendCorrectionToClients() {
        for (Map.Entry<Integer, Integer> entry : playerIdSequenceMap.entrySet()) {
            Packet.Packet1Correction p = new Packet.Packet1Correction();
            p.state = StateGDX.getInstance();
            p.lastSeq = entry.getValue();

            clients.get(entry.getKey()).sendTCP(p);
        }
    }
}
