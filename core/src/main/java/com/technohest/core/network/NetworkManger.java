package com.technohest.core.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.technohest.core.model.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Used to register the packets which will be sent over the network.
 * Created by time on 5/12/15.
 */
public class NetworkManger {
    public static void registerPackets(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(Packet.Packet0PlayerID.class);
        kryo.register(Packet.Packet0PlayerTypeIdMap.class);
        kryo.register(Packet.Packet0Start.class);
        kryo.register(Packet.Packet1ActionList.class);
        kryo.register(Packet.Packet1Correction.class);
        kryo.register(ArrayList.class);
        kryo.register(Action.class);
        kryo.register(Action.class);
        kryo.register(Action.ActionID.class);
        kryo.register(Vector.class);
        kryo.register(HashMap.class);
        kryo.register(Integer.class);
    }
}
