package com.technohest.core.network;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.technohest.core.model.*;
import com.technohest.core.model.Character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        kryo.register(IState.class);
        kryo.register(StateGDX.class);
        kryo.register(Character.class);
        kryo.register(Vector2.class);
        kryo.register(Character.State.class);
        kryo.register(String.class);
        kryo.register(Attack.class);
        kryo.register(int.class);
        kryo.register(boolean.class);
        kryo.register(Projectile.class);
        kryo.register(Packet.Packet2GameOver.class);
        kryo.register(List.class);
    }
}
