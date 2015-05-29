package com.technohest.LibgdxService;

import com.badlogic.gdx.math.Vector2;
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
    HashMap<Integer, Vector2> getCharacterIdStates();
    /**
     * Sets the state properties.
     * @param characterVectorMap map of player id, position, and velocity.
     * @param attackVectorTypeMap - map of position vector and attack Type (0 = melee, 1 = projectile)
     * */
    void setState(HashMap<Integer, Vector2> characterVectorMap,HashMap<Vector2,Integer> attackVectorTypeMap);

    /**
     * Returns all the values of the active attacks.
     * @return
     */
    HashMap<Vector2,Integer> getAttackVectorTypeMap();

    /**
     * Sets the id/vector map which will merge with characters
     * @param idVectorMap
     */
    void setCharacterIdVectorMap(HashMap<Integer,Vector2> idVectorMap);

    /**
     * Sets the id/vector map which will merge with attacks.
     * @param attackVectorTypeMap
     */
    void setAttackVectorTypeMap(HashMap<Vector2,Integer> attackVectorTypeMap);

    /**
     * Compare with another state
     * @param attackVectorTypeMap
     * remote state
     * @return
     * true if same, false else
     */
    boolean equals(HashMap<Integer,Vector2> characterIdVectorMap, HashMap<Vector2,Integer> attackVectorTypeMap);

}
