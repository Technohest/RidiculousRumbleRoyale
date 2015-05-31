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

    public Attack() {}

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

    /**
     * @return
     * true if there is time left before the next attack can begin.
     */
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
    public void setIsReady(boolean b){
        this.ready = b;
    }
    @Override
    public boolean equals(Object o){
        if(o == null){
            return false;
        }else if(o instanceof Attack){
            Attack attack =  (Attack)o;
            return this.getElapsedTime() == attack.getElapsedTime() &&
                    this.getDamage() == attack.getDamage() &&
                    this.getName().equals(attack.getName()) &&
                    this.getSourcePlayerId().equals(attack.getSourcePlayerId()) &&
                    this.getDuration() == attack.getDuration() &&
                    this.isReady() == attack.isReady() &&
                    this.isEnabled() == attack.isEnabled() &&
                    this.getHasImpacted() == attack.getHasImpacted();
        }
        return false;
    }
    @Override
    public int hashCode(){
        int hash = 0;
        hash += damage * 2;
        hash += sourcePlayerId * 3;
        hash += duration * 5;
        hash += elapsedTime * 7;
        return hash;
    }
}