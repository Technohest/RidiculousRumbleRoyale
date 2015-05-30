package com.technohest.core.model;

import com.badlogic.gdx.math.Vector2;
import com.technohest.LibgdxService.IGameLogic;
import com.technohest.LibgdxService.levels.ILevel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by oskar on 2015-05-28.
 * @author Oskar Jedvert
 */
public class RRRGameModelTest {
    RRRGameModel model;
    private class TestLogic implements IGameLogic{

        @Override
        public void update(float v) {

        }

        @Override
        public void generate(ArrayList<Integer> characterIdArray) {

        }

        @Override
        public ILevel getLevel() {
            return null;
        }

        @Override
        public Integer getStateOfPlayer(Integer playerId) {
            return null;
        }

        @Override
        public void killPlayer(Integer playerId) {

        }

        @Override
        public void respawnPlayer(Integer playerId) {

        }

        @Override
        public Integer getPlayerTakenDamage(Integer playerId) {
            return null;
        }

        @Override
        public void resetDamageTaken(Integer playerId) {

        }

        @Override
        public void moveLeft(Integer playerId) {

        }

        @Override
        public void moveRight(Integer playerId) {

        }

        @Override
        public void jump(Integer playerId) {

        }

        @Override
        public void attack_base(Integer playerId, boolean isFacingRight) {

        }

        @Override
        public void attack_special(Integer playerId, boolean isFacingRight) {

        }

        @Override
        public void setCharacterState(Integer playerId, Vector2 pos, Vector2 vel) {

        }

        @Override
        public void setAttackState(Integer attackId, Vector2 position, Vector2 velocity) {

        }

        @Override
        public boolean getAttackHasInpacted(Integer playerId, String attackType) {
            return false;
        }

        @Override
        public boolean canAttack(Integer playerId) {
            return false;
        }

        @Override
        public void destroyAttack(Integer attackId) {

        }

        @Override
        public void generateState(Set<Integer> aliveCharacterIds, HashMap<Integer, Integer> attackIDTypeMap) {

        }
    }
    @Before
    public void initialize(){
        model = new RRRGameModel(new TestLogic());
        assertNotNull(model.getAliveCharacters());
    }
    @Test
    public void testInit() throws Exception {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1,1);
        model.init(map);
        assertTrue(model.getAliveCharacters().size() == 1);
    }

    @Test
    public void testGetPlayers() throws Exception {

        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 1);
        model.init(map);
        Collection<Character> collection = model.getPlayers();
        assertTrue(collection.size() == 1);
    }

    @Test
    public void testGetActiveAttacks() throws Exception {
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
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 1);
        map.put(2, 2);
        model.init(map);
        assertNotNull(model.getPlayerFromID(1));
        assertNotNull(model.getPlayerFromID(2));
        assertNull(model.getPlayerFromID(4));
    }
}