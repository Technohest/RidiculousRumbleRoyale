package com.technohest.core.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by oskar on 2015-05-28.
 */
public class AttackTest {

    @Test
    public void testReset() throws Exception {
        Attack attack = new Attack() {
        };
        attack.incrementTime(10.0f);
        assertTrue(attack.getElapsedTime() == 10.0f);
        attack.reset();
        assertTrue(attack.getElapsedTime() == 0);
    }

    @Test
    public void testPerform() throws Exception {
        Attack attack = new Attack() {
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
        Attack a = new Attack() {
        };
        Attack b = new Attack() {
        };
    }

    @Test
    public void testTimeLeft() throws Exception {

    }

    @Test
    public void testGetSourcePlayerId() throws Exception {

    }

    @Test
    public void testIsReady() throws Exception {

    }

    @Test
    public void testGetDuration() throws Exception {

    }

    @Test
    public void testSetSourcePlayerId() throws Exception {

    }

    @Test
    public void testIsEnabled() throws Exception {

    }

    @Test
    public void testSetEnabled() throws Exception {

    }

    @Test
    public void testGetElapsedTime() throws Exception {

    }

    @Test
    public void testGetHasImpacted() throws Exception {

    }

    @Test
    public void testSetImpacted() throws Exception {

    }

    @Test
    public void testGetDamage() throws Exception {

    }

    @Test
    public void testGetName() throws Exception {

    }
}