package com.technohest.core.model;

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

    //Game specific variables;
    private int healthPoints;
    private int kills;
    private int deaths;

    /**
     * Creates a character.
     *
     */
    public Character(int id, String name,Attack baseAttack,Attack specialAttack) {
        this.name = name;
        this.baseAttack = baseAttack;
        this.specialAttack = specialAttack;
        this.healthPoints = 100;
        this.kills = 0;
        this.deaths = 0;
        this.id = id;


    }

    public Character() {

    }



    /**
     * When character dies, redraws and increments deaths
     */
    public void die() {
        //To be implemented further in future versions,
        this.deaths++;
    }

    /**
     * When chararcter kills another character, increments kills.
     */
    public void incrementKill() {
        kills++;
    }

    //***************************************GETTERS/SETTERS***********************************************//


    public State getState() {
        return state;
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
