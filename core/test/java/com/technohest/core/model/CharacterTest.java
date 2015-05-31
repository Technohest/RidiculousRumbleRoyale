package com.technohest.core.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by oskar on 2015-05-28.
 * @author Oskar Jedvert
 */
public class CharacterTest {

    @Test
    public void testGenerateAttacks() throws Exception {
        Character c = new Character();
        assertNull(c.getBaseAttack());
        assertNull(c.getSpecialAttack());
        c.generateAttacks();
        assertNotNull(c.getBaseAttack());
        assertNotNull(c.getSpecialAttack());
    }

    @Test
    public void testDie() throws Exception {
        Character c = new Character(1, "TEST");
        assertTrue(c.isAlive());
        c.die();
        assertFalse(c.isAlive());
    }

    @Test
    public void testIncrementKill() throws Exception {
        Character c = new Character(1, "TEST");
        assertTrue(c.getKills() == 0);
        c.incrementKill();
        assertTrue(c.getKills() == 1);
    }

    @Test
    public void testRespawnTimeDone() throws Exception {
        Character c = new Character(1, "TEST");
        c.die();
        assertFalse(c.respawnTimeDone());
        c.addRespawnTimer(100);
        assertTrue(c.respawnTimeDone());
    }

    @Test
    public void testRespawn() throws Exception {
        Character c = new Character(1, "TEST");
        c.die();
        assertFalse(c.isAlive());
        c.respawn();
        assertTrue(c.isAlive());
    }

    @Test
    public void testSetAttributes() throws Exception {
        Character c = new Character(1, "TEST");
        Character b = new Character(1, "test1");
        assertFalse(b.getName().equals(c.getName()));
        b.setAttributes(c);
        assertEquals(b.getName(), c.getName());
    }

    @Test
    public void testSetShouldRespawn() throws Exception {
        Character c = new Character(1, "TEST");
        assertFalse(c.getShouldRespawn());
        c.setShouldRespawn(true);
        assertTrue(c.getShouldRespawn());
    }

    @Test
    public void testSetState() throws Exception {
        Character c = new Character(1, "TEST");
        Character.State s = c.getState();
        c.setState(Character.State.Falling);
        assertNotEquals(c.getState(), s);
    }

    @Test
    public void testGetBaseAttack() throws Exception {
        Character c = new Character(1, "TEST");
        assertNotNull(c.getBaseAttack());
    }

    @Test
    public void testGetSpecialAttack() throws Exception {
        Character c = new Character(1, "TEST");
        assertNotNull(c.getSpecialAttack());
    }

    @Test
    public void testSetIsFacingRight() throws Exception {
        Character c = new Character(1, "TEST");
        assertFalse(c.isFacingRight());
        c.setIsFacingRight(true);
        assertTrue(c.isFacingRight());
    }

    @Test
    public void testSetGrounded() throws Exception {
        Character c = new Character(1, "TEST");
        assertFalse(c.isGrounded());
        c.setGrounded(true);
        assertTrue(c.isGrounded());
    }

    @Test
    public void testTakeDamage() throws Exception {
        Character c = new Character(1, "TEST");
        assertEquals(c.getHealthPoints(), 100);
        c.takeDamage(10);
        assertEquals(c.getHealthPoints(), 90);
    }

    @Test
    public void testSetHealthPoints() throws Exception {
        Character c = new Character(1, "TEST");
        assertEquals(c.getHealthPoints(), 100);
        c.setHealthPoints(90);
        assertEquals(c.getHealthPoints(), 90);
    }

    @Test
    public void testGetDeaths() throws Exception {
        Character c = new Character(1, "TEST");
        assertEquals(c.getDeaths(), 0);
        c.die();
        assertEquals(c.getDeaths(), 1);
    }

    @Test
    public void testGetName() throws Exception {
        Character c = new Character(1, "TEST");
        assertEquals(c.getName(), "TEST");
    }

    @Test
    public void testSetId() throws Exception {
        Character c = new Character(1, "TEST");
        assertEquals(c.getId(), 1);
        c.setId(2);
        assertEquals(c.getId(), 2);
    }
}