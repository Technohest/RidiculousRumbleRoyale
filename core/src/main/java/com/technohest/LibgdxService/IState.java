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
     * @param attackIdVectorMap - map of attack id(Type) (0 = melee, 1 = projectile)
     * */
    void setState(HashMap<Integer, Vector2> characterVectorMap,HashMap<Integer,Vector2> attackIdVectorMap);

    /**
     * Returns all the values of the active attacks.
     * @return
     */
    HashMap<Integer,Vector2> getAttackIdStates();

    /**
     * Sets the id/vector map which will merge with characters
     * @param idVectorMap
     */
    void setCharacterIdVectorMap(HashMap<Integer,Vector2> idVectorMap);

    /**
     * Sets the id/vector map which will merge with attacks.
     * @param idVectorMap
     */
    void setAttackIdVectorMap(HashMap<Integer,Vector2> idVectorMap);

    /**
     * Compare with another state
     * @param attackIdVectorMap
     * remote state
     * @return
     * true if same, false else
     */
    boolean equals(HashMap<Integer,Vector2> characterIdVectorMap, HashMap<Integer,Vector2> attackIdVectorMap);

}
