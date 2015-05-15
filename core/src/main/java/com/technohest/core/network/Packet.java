package com.technohest.core.network;


import com.technohest.core.model.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * All the different packets which can be sent over the network.
 * Created by time on 2015-05-05.
 */
public class Packet {
    public static class Packet0PlayerID {
        public Integer id = null;
    }

    public static class Packet0PlayerTypeIdMap {
        public HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    }

    public static class Packet1ActionList {
        public Vector<Action> action;
    }

    public static class Packet1Correction {
        public ArrayList<Action> actions;
    }

    public class Packet0PlayerSetCharacter {
        //CharType type;
    }

    public static class Packet0Start {}

}
