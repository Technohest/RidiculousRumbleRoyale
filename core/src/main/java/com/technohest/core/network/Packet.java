package com.technohest.core.network;

import com.technohest.core.handlers.InputHandler;
import com.technohest.core.model.*;
import com.technohest.core.model.Character;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * All the different packets which can be sent over the network.
 * Created by time on 2015-05-05.
 */
public class Packet {
    public static class Packet0PlayerID {
        public Integer id = null;
    }

    public static class Packet0PlayerTypeIdMap {
        public DualHashBidiMap<Integer, Integer> map = new DualHashBidiMap<Integer, Integer>();
    }

    public class Packet0PlayerSetCharacter {
        //CharType type;
    }

    public static class Packet2Start {}

}
