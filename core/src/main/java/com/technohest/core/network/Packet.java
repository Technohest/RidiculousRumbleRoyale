package com.technohest.core.network;


import com.technohest.LibgdxService.IState;
import com.technohest.core.model.Action;
import java.util.HashMap;
import java.util.List;

/**
 * All the different packets which can be sent over the network.
 * @author David Ström
 */
public class Packet {
    public static class Packet0PlayerID {
        public Integer id = null;
    }

    public static class Packet0PlayerTypeIdMap {
        public HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    }

    public static class Packet1ActionList {
        public List<Action> action;
    }

    public static class Packet1Correction {
        public int lastSeq;
        public IState state;
    }

    public class Packet0PlayerSetCharacter {
        //CharType type;
    }

    public static class Packet0Start {}

    public static class Packet2GameOver {}
}
