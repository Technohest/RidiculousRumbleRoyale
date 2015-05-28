package com.technohest.core.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by oskar on 2015-05-28.
 */
public class MeleeAttackTest {
    @Test
    public void meleeAttackTest() throws Exception{
        MeleeAttack attack = new MeleeAttack();
        assertNotNull(attack);
    }
}