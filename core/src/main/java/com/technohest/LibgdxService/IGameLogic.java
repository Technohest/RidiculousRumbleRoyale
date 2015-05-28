package com.technohest.LibgdxService;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Set;

/**
 * An interface for the gamelogic
 * @author Oskar Jedvert
 * @author Tobias Alldén
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
     * Target level
     * @param characterIdArray
     */

    void generate(ArrayList<Integer> characterIdArray);

    /**
     * Returns the level.
     * @return
     */
    public ILevel getLevel();


    /**
     * Updates the player.
     * @param playerId
     */
    public Integer getStateOfPlayer(Integer playerId);

    /**
     * Kills the player.
     * @param playerId
     */
    public void killPlayer(Integer playerId);

    /**
     * Respawns the player
     * @param playerId
     */
    public void respawnPlayer(Integer playerId);

    /**
     * Returns the damage a specified player has taken.
     * @param playerId
     */
    public Integer getPlayerTakenDamage(Integer playerId);

    /**
     * Resets the damage delt to a specific player.
     * @param playerId
     */
    public void resetDamageTaken(Integer playerId);


    /**
     * Moves the player(Body with fixtures) left in the game,and plays the run animation.
     */
    public void moveLeft(Integer playerId);



    /**
     * Moves the player(Body with fixtures) right in the game,and plays the run animation.
     */
    public void moveRight(Integer playerId);

    /**
     * Makes the player(Body with fixtures) jump in the game, and plays the jump animation.
     */
    public void jump(Integer playerId);

    /**
     * Makes the player perform a base attack.
     * @param playerId
     * @param isFacingRight
     */
    public void attack_base(Integer playerId,boolean isFacingRight);

    /**
     * Makes the player perform a special attack.
     * @param playerId
     * @param isFacingRight
     */
    public void attack_special(Integer playerId,boolean isFacingRight);

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
     * Sets the attack attributes.
     * @param attackId
     * @param position
     * @param velocity
     */
    public void setAttackState(Integer attackId, Vector2 position, Vector2 velocity);

    /**
     * Checks the impacted attack list for the body with attackType user data, then searches it's fixtures for one with
     * the id of the player
     * @param playerId
     * @param attackType
     * @return
     */
    public boolean getAttackHasInpacted(Integer playerId,String attackType);

    /**
     * Returns true if the player can attack.
     * @param playerId
     * @return
     */
    public boolean canAttack(Integer playerId);



    /**
     * Destroys the specified attack
     */
    public void destroyAttack(Integer attackId);

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
    void generateState(Set<Integer> aliveCharacterIds, Set<Integer> activeAttackIds);
}
