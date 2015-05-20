package com.technohest.core.model;

/**
 *  A class for representing a projectile;
 *  @author Tobias Alldén
 *  @version 1.0
 */
public class Projectile extends Attack {
    private float velocityX;

    public Projectile(String name, int damage,float duration,float velocityX) {
        super(name,damage,duration);
        this.velocityX = velocityX;
    }

    public Projectile() {

    }

    public float getVelocityX() {
        return velocityX;
    }
}
