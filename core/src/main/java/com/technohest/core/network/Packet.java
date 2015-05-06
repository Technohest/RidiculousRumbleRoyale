package com.technohest.core.network;

import com.technohest.core.model.*;
import com.technohest.core.model.Character;

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

    public static class Packet1PlayerIdMap {
        //Will maybe be Integer, Character or possibly only Integer ID contained in this later.
        public ArrayList<Integer> playerList = new ArrayList<Integer>();
    }

    public static class Packet2Start {}
}
