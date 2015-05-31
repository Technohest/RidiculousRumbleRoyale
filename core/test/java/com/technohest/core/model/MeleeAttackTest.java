package com.technohest.core.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test for the MeleeAttack class.
 * @author Oskar Jedvert
 */
public class MeleeAttackTest {
    @Test
    public void meleeAttackTest() throws Exception{
        MeleeAttack attack = new MeleeAttack();
        assertNotNull(attack);
    }
}