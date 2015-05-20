package com.technohest.core.model;
import com.technohest.constants.Constants;

/**
 *  A abstract class for dealing damage.
 *  @author Tobias Alldén
 *  @version 1.0
 */
public abstract class Attack  {
    private int damage;
    private String name;
    public Character sourcePlayer;
    private float duration;
    private float elapsedTime;
    private boolean ready;
    private boolean hasInpacted;




    public Attack() {

    }

    public Attack(String name,int damage,float duration) {
        this.damage = damage;
        this.name = name;
        this.duration = duration;
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
    public Character getSourcePlayer() {
        return sourcePlayer;
    }
    private void resetElapsedTime() {
        this.elapsedTime = 0;
    }
    public boolean isReady() {
        return ready;
    }
    public void setSourcePlayer(Character sourcePlayer) {
        this.sourcePlayer = sourcePlayer;
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