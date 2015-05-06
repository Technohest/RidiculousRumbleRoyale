package com.technohest.core.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.technohest.core.model.Character;

import java.io.IOException;
import java.util.HashMap;

/**
 * Creates a server with specified port and registers packets the server will be listening to.
 * Created by time on 2015-05-05.
 */
public class RServer {
    private HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    private Server server;

    public RServer(String port) {
        server = new Server();
        registerPackets();

        ServerNetworkListener serverNetworkListener = new ServerNetworkListener();
        serverNetworkListener.init(this);

        server.addListener(serverNetworkListener);
        server.start();

        try {
            server.bind(Integer.parseInt(port));
        } catch (IOException e) {
            e.printStackTrace();
            server.stop();
        }

        Log.set(Log.LEVEL_INFO);
    }

    private void registerPackets() {
        Kryo kryo = server.getKryo();
        kryo.register(Packet.Packet0PlayerID.class);
        kryo.register(Packet.Packet1PlayerIdMap.class);
        kryo.register(HashMap.class);
    }

    public HashMap<Integer, Integer> getPlayerIdMap() {
        //return model.getPlayerIdMap();
        return map;
    }

    public void setPlayerIdMap(int id) {
        //Map will be moved down into the model at a later point in time as to move the game-state outside of the server.
        map.put(id, 0);
        //model.setPlayerIdMap(playerIdMap);
    }
}
