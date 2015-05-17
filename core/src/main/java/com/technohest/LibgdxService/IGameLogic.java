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
     * Makes the character perform a base attack
     */
    public void attack_base(Character player);

    /**
     * Makes the character perform a special attack
     */
    public void attack_special(Character player);

    /**
     * Sets the players attributes
     * @param player
     * target Character
     * @param pos
     * new position
     * @param vel
     * new velocity
     */
    public void setCharacterState(Character player, Vector2 pos, Vector2 vel);

    public void correct(IState state);

    HashMap<Character, ArrayList<Vector2>> generateState();

    void setIsClient();

    void setIsServer();
}
