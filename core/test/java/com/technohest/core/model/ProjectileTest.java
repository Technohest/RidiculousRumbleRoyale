package com.technohest.core.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the Projectile class.
 * @author Oskar Jedvert
 */
public class ProjectileTest {

    @Test
    public void testGetVelocityX() throws Exception {
        Projectile p = new Projectile("TEST", 1, 10, 10, 10);
        assertEquals(10, p.getVelocityX(), 0.01);
    }
    @Test
    public void projectileTest() throws Exception{
        Projectile attack = new Projectile();
        assertNotNull(attack);
    }
}