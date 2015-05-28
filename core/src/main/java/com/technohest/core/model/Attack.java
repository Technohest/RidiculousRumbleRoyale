package com.technohest.core.model;

/**
 *  A abstract class for dealing damage.
 *  @author Tobias Alldén
 *  @version 1.0
 */
public abstract class Attack  {
    private int damage;
    private String name;
    public Integer sourcePlayerId;
    private float duration;
    private float elapsedTime;
    private boolean ready;
    private boolean hasImpacted;
    private boolean enabled;




    public Attack() {

    }

    public Attack(String name,Integer sourcePlayerId,int damage,float duration) {
        this.damage = damage;
        this.name = name;
        this.duration = duration;
        this.sourcePlayerId = sourcePlayerId;
        this.elapsedTime = 0;
        this.ready = true;
        this.hasImpacted = false;
        this.enabled = true;
        }


    /**
     * Resets the attack.
     */
    public void reset() {
        resetElapsedTime();
        ready = true;
        this.hasImpacted = false;
    }

    /**
     * Increments the attacks' timer
     * @param v
     */
    public void incrementTime(float v) {
        this.elapsedTime += v;
    }


    /**
     * Performs the attack
     */
    public void perform() {
        resetElapsedTime();
        ready = false;
    }

    /**
     * Sets the attributes for the attack, copying another attacks properties.
     * @param attack
     */
    public void setAttributes(Attack attack ) {
        this.damage = attack.getDamage();
        this.name = attack.getName();
        this.duration = attack.getDuration();
        this.sourcePlayerId = attack.getSourcePlayerId();
        this.elapsedTime = attack.getElapsedTime();
        this.ready = attack.isReady();
        this.hasImpacted = attack.hasImpacted;
    }

    public boolean timeLeft() {
        return elapsedTime < duration;
    }
    public Integer getSourcePlayerId() {
        return sourcePlayerId;
    }
    private void resetElapsedTime() {
        this.elapsedTime = 0;
    }
    public boolean isReady() {
        return ready;
    }
    public float getDuration() {
        return duration;
    }
    public void setSourcePlayerId(Integer Id) {
        this.sourcePlayerId = Id;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public float getElapsedTime() {
        return elapsedTime;
    }
    public boolean getHasImpacted() {
        return this.hasImpacted;
    }
    public void setImpacted(boolean impacted) {
        this.hasImpacted = impacted;

    }
    public int getDamage() {
        return damage;
    }
    public String getName() {
        return name;
    }
}