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
    private boolean hasInpacted;




    public Attack() {

    }

    public Attack(String name,Integer sourcePlayerId,int damage,float duration) {
        this.damage = damage;
        this.name = name;
        this.duration = duration;
        this.sourcePlayerId = sourcePlayerId;
        this.elapsedTime = 0;
        this.ready = true;
        this.hasInpacted = false;
        }

    public void reset() {
        resetElapsedTime();
        ready = true;
        this.hasInpacted = false;
    }
    public void incrementTime(float v) {
        this.elapsedTime += v;
    }


    public void perform() {
        resetElapsedTime();
        ready = false;
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
    public void setSourcePlayerId(Integer Id) {
        this.sourcePlayerId = Id;
    }
    public void setInpacted(boolean inpacted) {
        this.hasInpacted = inpacted;

    }
    public boolean getHasInpacted() {
        return this.hasInpacted;
    }

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }
}