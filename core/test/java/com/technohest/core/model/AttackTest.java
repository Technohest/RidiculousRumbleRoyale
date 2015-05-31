package com.technohest.core.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the attack class.
 * @author Oskar Jedvert
 */
public class AttackTest {

    @Test
    public void testReset() throws Exception {
        Attack attack = new Attack("A",1, 1, 1) {
        };
        attack.incrementTime(10.0f);
        assertTrue(attack.getElapsedTime() == 10.0f);
        attack.reset();
        assertTrue(attack.getElapsedTime() == 0);
    }

    @Test
    public void testPerform() throws Exception {
        Attack attack = new Attack("A",1, 1, 1) {
        };
        attack.incrementTime(10.0f);
        assertTrue(attack.getElapsedTime() == 10.0f);
        assertTrue(attack.isReady());
        attack.perform();
        assertFalse(attack.isReady());
        assertTrue(attack.getElapsedTime() ==  0);
    }

    @Test
    public void testSetAttributes() throws Exception {
        Attack a = new Attack("A",1, 1, 1) {
        };
        Attack b = new Attack("B", 2, 2, 2) {
        };
        assertFalse(b.equals(a));
        b.setAttributes(a);
        assertTrue(b.equals(a));
    }

    @Test
    public void testTimeLeft() throws Exception {
        Attack a = new Attack("A",1, 1, 10) {
        };
        assertTrue(a.timeLeft());
        a.incrementTime(5);
        assertTrue(a.timeLeft());
        a.incrementTime(5);
        assertFalse(a.timeLeft());
    }

    @Test
    public void testSetSourcePlayerId() throws Exception {
        Attack a = new Attack("A",1, 1, 10) {
        };
        assertTrue(a.getSourcePlayerId() == 1);
        a.setSourcePlayerId(10);
        assertTrue(a.getSourcePlayerId() == 10);
    }

    @Test
    public void testGetDuration() throws Exception {
        Attack a = new Attack("A",1, 1, 10) {
        };
        assertTrue(a.getDuration() == 10);
    }

    @Test
    public void testIsEnabled() throws Exception {
        Attack a = new Attack("A",1, 1, 10) {
        };
        assertTrue(a.isEnabled());
    }

    @Test
    public void testSetEnabled() throws Exception {
        Attack a = new Attack("A",1, 1, 10) {
        };
        assertTrue(a.isEnabled());
        a.setEnabled(false);
        assertFalse(a.isEnabled());
    }

    @Test
    public void testGetElapsedTime() throws Exception {
        Attack a = new Attack("A",1, 1, 10) {
        };
        a.incrementTime(1);
        assertTrue(a.getElapsedTime() == 1);
    }

    @Test
    public void testGetHasImpacted() throws Exception {
        Attack a = new Attack("A",1, 1, 10) {
        };
        assertFalse(a.getHasImpacted());
        a.setImpacted(true);
        assertTrue(a.getHasImpacted());
    }

    @Test
    public void testGetDamage() throws Exception {
        Attack a = new Attack("A",1, 20, 10) {
        };
        assertTrue(a.getDamage() == 20);
    }

    @Test
    public void testGetName() throws Exception {
        Attack a = new Attack("A",1, 1, 10) {
        };
        assertTrue(a.getName().equals("A"));
    }

    @Test
    public void testHashCode() throws Exception {
        Attack a = new Attack("A",1, 20, 10) {
        };
        Attack b = new Attack("A",1, 20, 10) {
        };
        assertEquals(b, a);
        assertEquals(b.hashCode(), a.hashCode());
    }

    @Test
    public void testEquals() throws Exception {
        Attack a = new Attack("A",1, 20, 10) {
        };
        Attack b = new Attack("A",1, 20, 10) {
        };
        assertTrue(b.equals(a));
        Attack c = null;
        assertFalse(b.equals(c));
        assertFalse(b.equals(new Object()));
    }
}