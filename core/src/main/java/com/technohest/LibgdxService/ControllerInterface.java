package com.technohest.LibgdxService;

/**
 * An interface for the PlayerController
 * @author Tobias Alldén
 * @Version 0.1
 */
public interface ControllerInterface {
    /**
     * Moves the player(Body with fixtures) left in the and plays the run animation.
     */
    public void moveLeft();

    /**
     * Moves the player(Body with fixtures) left in the game,and plays the run animation.
     */
    public void moveRight();

    /**
     * Makes the player(Body with fixtures) jump in the game, and plays the jump animation.
     */
    public void jump();

    /**
     * Makes the character perform a base attack
     */
    public void attack_base();

    /**
     * Makes the character perform a special attack
     */
    public void attack_special();


}
