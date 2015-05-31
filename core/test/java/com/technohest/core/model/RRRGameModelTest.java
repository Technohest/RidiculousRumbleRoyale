package com.technohest.core.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.technohest.LibgdxService.IGameLogic;
import com.technohest.LibgdxService.levels.ILevel;
import com.technohest.constants.Constants;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by oskar on 2015-05-28.
 * @author Oskar Jedvert
 * @author Tobias Alldén
 */
public class RRRGameModelTest {
    RRRGameModel model;
    TestLogic logic;

    private class TestLogic implements IGameLogic{
        private ArrayList<Integer> idlist;
        public Set<Integer> aliveCharacterIds;
        public HashMap<Integer, Integer> attackIDTypeMap;

        @Override
        public void update(float v) {
        }

        @Override
        public void generate(ArrayList<Integer> characterIdArray) {
            this.idlist = characterIdArray;
        }

        @Override
        public ILevel getLevel() {
            return new ILevel() {
                @Override
                public TiledMap getMap() {
                    return null;
                }

                @Override
                public void generate(World world) {

                }

                @Override
                public String getName() {
                    return null;
                }

                @Override
                public Vector2 getSpawnPoint() {
                    return null;
                }
            };
        }

        @Override
        public Integer getStateOfPlayer(Integer playerId) {
            return playerId;
        }

        @Override
        public void killPlayer(Integer playerId) {

        }

        @Override
        public void respawnPlayer(Integer playerId) {

        }

        @Override
        public Integer getPlayerTakenDamage(Integer playerId) {
            return 0;
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
            return true;
        }

        @Override
        public void destroyAttack(Integer attackId) {

        }

        @Override
        public void generateState(Set<Integer> aliveCharacterIds, HashMap<Integer, Integer> attackIDTypeMap) {
            this.aliveCharacterIds = aliveCharacterIds;
            this.attackIDTypeMap = attackIDTypeMap;
        }
    }

    @Before
    public void initialize(){
        logic = new TestLogic();
        model = new RRRGameModel(logic);
        assertNotNull(model.getAliveCharacters());
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i=0;i<5;i++){
            map.put(i, i);
        }
        model.init(map);
        ArrayList<Integer> idList = new ArrayList<Integer>();
        for(int i=0;i<5;i++){
            idList.add(i,i);
        }
        model.getGameLogic().generate(idList);
        ArrayList<Attack> attackMap = new ArrayList<Attack>();
        Attack a = new Projectile("Projectilea",1,10,1,10);
        Attack b = new Projectile("Projectileb",1,10,1,10);
        Attack c = new MeleeAttack("Meleec",1,10,1);
        Attack d = new MeleeAttack("Meleed",1,10,1);
        a.setImpacted(false);
        b.setImpacted(false);
        c.setImpacted(true);
        d.setIsReady(true);
        a.setIsReady(true);
        b.setIsReady(false);
        c.setIsReady(false);
        d.setIsReady(true);
        attackMap.add(0, a);
        attackMap.add(1, b);
        attackMap.add(2, c);
        attackMap.add(3, d);
        model.setEnabledAttacks(attackMap);
    }

    @Test
    public void testInit() throws Exception {
        assertTrue(model.getAliveCharacters().size() == 5);
    }

    @Test
    public void testGetPlayers() throws Exception {
        Collection<Character> collection = model.getPlayers();
        assertTrue(collection.size() == 5);
    }

    @Test
    public void testGetActiveAttacks() throws Exception {
        assertNotNull(model.getActiveAttacks());
    }

    @Test
    public void testGetPlayer() throws Exception {
        assertNotNull(model.getPlayer("Name 1"));
        assertNotNull(model.getPlayer("Name 2"));
        assertNull(model.getPlayer("asdf"));
    }

    @Test
    public void testGetPlayerFromID() throws Exception {
        assertNotNull(model.getPlayerFromID(1));
        assertNotNull(model.getPlayerFromID(2));
        assertNull(model.getPlayerFromID(10));
    }

    @Test
    public void testStep() throws Exception {
        Character[] chars = new Character[5];
        for(int i=0;i<5;i++){
            chars[i] = model.getPlayerFromID(i);
        }
        model.step(1f);

        assertEquals(chars[0].getState(), Character.State.Falling);
        assertEquals(chars[1].getState(), Character.State.Jumping);
        assertEquals(chars[2].getState(), Character.State.Running);
        assertEquals(chars[3].getState(), Character.State.Running);
        assertEquals(chars[4].getState(), Character.State.Standing);

        //test respawn
        chars[0].setHealthPoints(-1);
        model.step(1f);
        assertFalse(chars[0].isAlive());
        model.step(Constants.RESPAWN_TIME + 1f);
        assertTrue(chars[0].isAlive());

    }
    @Test
    public void testGenerate() {
        model.generateWorld();
    }


    @Test
    public void testGetAlivePlayersId() throws Exception {
        HashSet<Integer> set = model.getAlivePlayersId();
        for(int i=0;i<5;i++){
            assertTrue(set.contains(i));
        }
    }

    @Test
    public void testGenerateState() throws Exception{
        model.generateState();
        assertNotNull(logic.aliveCharacterIds);
        assertNotNull(logic.attackIDTypeMap);
    }
    @Test
    public void testPerformAction() throws Exception{
        ArrayList<Action.ActionID> actionids = new ArrayList<Action.ActionID>();
        model.getPlayerFromID(0).setGrounded(true);
        actionids.add(0, Action.ActionID.JUMP);
        actionids.add(1, Action.ActionID.MOVE_RIGHT);
        actionids.add(2, Action.ActionID.MOVE_LEFT);
        actionids.add(3, Action.ActionID.ATTACK_BASE);
        actionids.add(4, Action.ActionID.ATTACK_SPECIAL);
        for(int i=0;i<5;i++){
            Action action = new Action(i, actionids.get(i), i);
            model.performAction(action);
        }
    }
    @Test
    public void testRespawnEnabled() {
        boolean respawnEnabled1 = model.getRespawnEnabled();
        if(respawnEnabled1 == true) {
            model.setRespawnEnabled(false);
        } else {
            model.setRespawnEnabled(true);
        }
        assertTrue(respawnEnabled1 != model.getRespawnEnabled());
    }
    @Test
    public void testMyID() {
        Integer i = model.getMyID();
        Integer newId = 100;
        model.setMyID(newId);
        assertTrue(i != model.getMyID());
    }
}