package com.technohest.characters;
import com.badlogic.gdx.math.Vector2;
import com.technohest.attacks.Attack;
import com.technohest.core.Constants.*;

/**
 * A template for characters.
 * @author Tobias Alldén
 * @version 1.0
 */
public  class Character {

    //Constants, can be changed to feel.
    public static final float MAX_VELOCITY = 5f;
    public static final float JUMP_VELOCITY = 2f;
    public static final float DAMPING = 0.87f;
    public static final float GRAVITY = -22.0f;
    public static final float MAX_JUMP_SPEED = 9f;
    public static final  long LONG_JUMP_PRESS = 1501;

    //State of character,may be reimplemented in future versions.
    public enum State {
        Satnding,Running,Jumping,Falling
    }


    //Variables
    private Vector2 position,velocity,acceleration;
    private State state;
    private Attack baseAttack,specialAttack;
    private boolean isFacingRight;
    private boolean grounded;
    private float height;
    private float width;
    private float stateTime;

    //Game specific variables;
    private int healthPoints;
    private int kills;
    private int deaths;

    public Character(Vector2 position) {
        this.position = position;
        this.healthPoints = 100;
        this.velocity = new Vector2(0,GRAVITY);
        acceleration = new Vector2(2,0);
        this.kills = 0;
        this.deaths = 0;
    }

    /**
     * Updates the character.
     */
    public void update(float delta) {
        stateTime += delta;
        velocity.sub(acceleration.scl(delta));
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


    public Vector2 getPosition() {
        return position;
    }
    public float getX() {return position.x;}
    public float getY() {return position.y;}
    public void setX(float x) {position.x = x;}
    public void setY(float y) {position.y = y;}
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Attack getBaseAttack() {
        return baseAttack;
    }

    public void setBaseAttack(Attack baseAttack) {
        this.baseAttack = baseAttack;
    }

    public Attack getSpecialAttack() {
        return specialAttack;
    }

    public void setSpecialAttack(Attack specialAttack) {
        this.specialAttack = specialAttack;
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

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
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

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }
}
