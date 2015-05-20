package com.technohest.LibgdxService;

import com.badlogic.gdx.math.Vector2;
import com.technohest.core.model.*;
import com.technohest.core.model.Character;
import com.technohest.core.network.IState;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by oskar on 2015-05-01.
 */
public interface IGameLogic {
    /**
     * Updates the game logic
     * @param v
     * Delta time since last call
     */
    void update(float v);

    /**
     * Generate bodies
     * @param level
     * Target level
     * @param idCharacterMap
     */

    void generate(ILevel level, HashMap<Integer,Character> idCharacterMap);

    /**
     * Updates the player.
     * @param player
     */
    public void updatePlayer(Character player);

    /**
     * Kills the player.
     * @param player
     */
    public void killPlayer(Character player);

    /**
     * Respawns the player
     * @param player
     */
    public void respawnPlayer(Character player);


    /**
     * Moves the player(Body with fixtures) left in the game,and plays the run animation.
     */
    public void moveLeft(Character player);



    /**
     * Moves the player(Body with fixtures) right in the game,and plays the run animation.
     */
    public void moveRight(Character player);

    /**
     * Makes the player(Body with fixtures) jump in the game, and plays the jump animation.
     */
    public void jump(Character player);

    /**
     * Makes the specified player perform a base attack.
     */
    public void attack_base(Character player,Attack attack);

    /**
     * Makes the specified player performs perform a special attack.
     */
    public void attack_special(Character player, Attack attack);

    /**
     * Sets the players movement attributes
     * @param playerId
     * target Character
     * @param pos
     * new position
     * @param vel
     * new velocity
     */
    public void setCharacterState(Integer playerId, Vector2 pos, Vector2 vel);



    /**
     * Destroys the specified attack
     */
    public void destroyAttack(Attack attack);

    /**
     * Compares local state with another state and corrects if needed
     * @param state
     * remote state
     */
    public void correct(IState state);

    /**
     * Generates a hashmap using current players and their bodies
     * @return
     * Character and their pos and velocity.
     */
    HashMap<Character, ArrayList<Vector2>> generateState();
}
