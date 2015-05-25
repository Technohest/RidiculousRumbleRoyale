package com.technohest.core.network;


import com.technohest.LibgdxService.IState;
import com.technohest.core.model.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        public List<Action> action;
    }

    public static class Packet1Correction {
        public List<Action> actions;
        public IState state;
    }

    public class Packet0PlayerSetCharacter {
        //CharType type;
    }

    public static class Packet0Start {}

    public static class Packet2GameOver {}
}
