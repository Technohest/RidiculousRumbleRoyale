package com.technohest.core.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by oskar on 2015-05-28.
 * @author Oskar Jedvert
 */
public class ActionTest {

    @Test
    public void testGetPlayerID() throws Exception {
        Action action = new Action(10, Action.ActionID.ATTACK_BASE, 1);
        assertTrue(action.getPlayerID() == 10);
    }

    @Test
    public void testGetActionID() throws Exception {
        Action action = new Action(10, Action.ActionID.ATTACK_BASE, 1);
        assertTrue(action.getActionID() == Action.ActionID.ATTACK_BASE);
    }

    @Test
    public void testGetSequenceNumber() throws Exception {
        Action action = new Action(1, Action.ActionID.ATTACK_BASE, 10);
        assertTrue(action.getSequenceNumber() == 10);
    }
}