package com.technohest.core.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by oskar on 2015-05-28.
 * @author Oskar Jedvert
 */
public class RRRGameModelTest {


    @Test
    public void testInit() throws Exception {
        RRRGameModel model = new RRRGameModel(true);
        assertNotNull(model.getAliveCharacters());
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1,1);
        model.init(map);
        assertTrue(model.getAliveCharacters().size() == 1);
    }

    @Test
    public void testGetPlayers() throws Exception {
        RRRGameModel model = new RRRGameModel(true);
        assertNotNull(model.getAliveCharacters());
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 1);
        model.init(map);
        Collection<Character> collection = model.getPlayers();
        assertTrue(collection.size() == 1);
    }

    @Test
    public void testGetActiveAttacks() throws Exception {
        RRRGameModel model = new RRRGameModel(true);
        assertNotNull(model.getAliveCharacters());
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 1);
        model.init(map);
        ArrayList<Attack> attackMap = new ArrayList<Attack>();
        attackMap.add(0, new Attack() {
        });
        attackMap.add(1, new Attack() {
        });
        attackMap.add(2, new Attack() {
        });
        model.setEnabledAttacks(attackMap);
        assertEquals(model.getActiveAttacks(), attackMap);
    }

    @Test
    public void testGetPlayer() throws Exception {
        RRRGameModel model = new RRRGameModel(true);
        assertNotNull(model.getAliveCharacters());
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 1);
        map.put(2, 2);
        model.init(map);
        assertNotNull(model.getPlayer("Name 1"));
        assertNotNull(model.getPlayer("Name 2"));
        assertNull(model.getPlayer("asdf"));
    }

    @Test
    public void testGetPlayerFromID() throws Exception {
        RRRGameModel model = new RRRGameModel(true);
        assertNotNull(model.getAliveCharacters());
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 1);
        map.put(2, 2);
        model.init(map);
        assertNotNull(model.getPlayerFromID(1));
        assertNotNull(model.getPlayerFromID(2));
        assertNull(model.getPlayerFromID(4));
    }
}