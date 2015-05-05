package com.technohest.core.model;

/**
 *  A class for representing a particle.
 *  @author Tobias Alldén
 *  @version 1.0
 */
public class Projectile extends Attack {
    private float startingTime;
    private float duration;

    public Projectile(String name, int damage,float startingtime,float duration) {
        super(name,damage);
    }

    /**
     * Sets the time of which the projectile was firered.
     * @param startingTime
     */
   public void setStartingTime(float startingTime) {
       this.startingTime = startingTime;
   }
    public float getStartingTime() {
        return startingTime;
    }
    public float duration() {
        return duration;
    }
}
