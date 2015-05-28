package com.technohest.LibgdxService;

import com.badlogic.gdx.math.Vector2;
import com.technohest.core.model.Attack;
import com.technohest.core.model.Character;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A interface representing the state of the game, will be sent over the network.
 * @author Oskar Jedvert
 * @author Tobias Alldén
 */
public interface IState {
    /**
     * Gets the state as a hashmap with Character as key and an array of vector2 as value, value[0] is position and value[1] is velocity
     * @return
     * State which needs to be set in gameLogic
     */
    HashMap<Integer, ArrayList<Vector2>> getCharacterIdStates();

    /**
     * Sets the state properties.
     * @param map map of player id, position, and velocity.
     * @param attackVectorMap
     * */
    void setState(HashMap<Integer, ArrayList<Vector2>> map,HashMap<Attack,ArrayList<Vector2>> attackVectorMap);

    /**
     * Returns all the values of the active attacks.
     * @return
     */
    HashMap<Integer,ArrayList<Vector2>> getAttackIdStates();

    /**
     * Returns an arraylist of the enabled attacks
     * @return
     */
    ArrayList<Attack> getActiveAttacks();

    /**
     * Sets all the active attacks
     * @param attacks
     */
    void setActiveAttacks(ArrayList<Attack> attacks);

    /**
     * Sets the id/vector map which will merge with characters
     * @param idVectorMap
     */
    void setCharacterIdVectorMap(HashMap<Integer,ArrayList<Vector2>> idVectorMap);

    /**
     * Sets the id/vector map which will merge with attacks.
     * @param idVectorMap
     */
    void setAttackIdVectorMap(HashMap<Integer,ArrayList<Vector2>> idVectorMap);

    /**
     * Sets the alive players
     */
    void setAlivePlayers(ArrayList<Integer> players );

    /**
     * Compare with another state
     * @param map
     * remote state
     * @return
     * true if same, false else
     */
    boolean equals(HashMap<Integer, ArrayList<Vector2>> map, HashMap<Attack,ArrayList<Vector2>> attackBodyVectorMap);

}
