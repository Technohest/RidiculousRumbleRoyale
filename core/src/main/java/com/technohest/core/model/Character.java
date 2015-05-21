package com.technohest.core.model;

import com.technohest.constants.Constants;

/**
 * A data class for characters.
 * @author Tobias Alldén
 * @version 1.2
 */
public  class Character {


    private int id;
    private Character attributes;

    //State of character,may be reimplemented in future versions.
    public enum State {
        Standing,Running,Jumping,Falling
    }

    //Variables
    private String name;
    private State state;
    private Attack baseAttack,specialAttack;
    private boolean isFacingRight;
    private boolean grounded;
    private float respawnTimer;
    private boolean alive,shouldRespawn;

    //Game specific variables;
    private int healthPoints, kills, deaths;

    /**
     * Creates a character.
     *
     */
    public Character(int id, String name) {
        this.name = name;
        this.healthPoints = 100;
        this.kills = 0;
        this.deaths = 0;
        alive = true;
        respawnTimer = 0;
        this.id = id;

    }



    public Character() {

    }


    /**
     * Generates the player specific attacks.
     */
    public void generateAttacks() {
        this.baseAttack = new MeleeAttack("Kick",id,20,0.3f);
        this.specialAttack = new MeleeAttack("Fireball",id,15,0.3f);
    }



    /**
     * When character dies, redraws and increments deaths
     */
    public void die() {
        //To be implemented further in future versions,
        this.deaths++;
        alive = false;
        respawnTimer = 0;
    }

    /**
     * When chararcter kills another character, increments kills.
     */
    public void incrementKill() {
        kills++;
    }

    /**
     * If respawn timer is greater than respawn time, returns tre, resets respawn timer and sets alive to true.
     */
    public boolean respawnTimeDone() {
       return respawnTimer > Constants.RESPAWN_TIME;
    }

    /**
     * Respawns the character, resetting repawn timer and sets alive to true
     */
    public void respawn() {
        healthPoints = 100;
        alive = true;
        shouldRespawn = false;
        respawnTimer = 0;
    }

    /**
     * Adds specified time to respawn timer.
     * @param v
     */
    public void addRespawnTimer(float v) {
        this.respawnTimer += v;
    }


    //***************************************GETTERS/SETTERS***********************************************//


    public State getState() {
        return state;
    }
    public boolean isAlive() {
        return alive;
    }
    public void setShouldRespawn(boolean value) {
            this.shouldRespawn = value;
    }
    public boolean getShouldRespawn() {
        return shouldRespawn;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Attack getBaseAttack() {
        return baseAttack;
    }

    public Attack getSpecialAttack() {
        return specialAttack;
    }

    public boolean isFacingRight() {
        return isFacingRight;
    }

    public void setIsFacingRight(boolean isFacingRight) {
        this.isFacingRight = isFacingRight;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public void takeDamage(int damage) {
        this.healthPoints -= damage;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }
    public String getName() {
        return name;
    }


    public int getId() {
        return id;
    }

    public void setAttributes(Character newStats) {
        this.name = newStats.name;
        this.baseAttack = newStats.getBaseAttack();
        this.specialAttack = newStats.getSpecialAttack();
        this.healthPoints = newStats.getHealthPoints();
        this.kills = newStats.getKills();
        this.deaths = newStats.getDeaths();

    }

    public void setId(int id) {
        this.id = id;
    }
}
