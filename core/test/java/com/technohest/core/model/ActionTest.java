package com.technohest.core.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by oskar on 2015-05-28.
 * @author Oskar Jedvert
 */
public class ActionTest {

    @Test
    public void testSetPlayerID() throws Exception {
        Action action = new Action(1, Action.ActionID.ATTACK_BASE);
        action.setPlayerID(10);
        assertTrue(action.getPlayerID() == 10);
    }

    @Test
    public void testSetActionID() throws Exception {
        Action action = new Action();
        action.setActionID(Action.ActionID.ATTACK_BASE);
        assertTrue(action.getActionID() == Action.ActionID.ATTACK_BASE);
    }

    @Test
    public void testSetSequenceNumber() throws Exception {
        Action action = new Action(1, Action.ActionID.ATTACK_BASE, 1);
        action.setSequenceNumber(10);
        assertTrue(action.getSequenceNumber() == 10);
    }
}