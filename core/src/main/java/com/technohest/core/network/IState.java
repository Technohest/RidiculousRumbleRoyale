package com.technohest.core.network;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.technohest.core.model.Attack;
import com.technohest.core.model.Character;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vilddjur on 2015-05-15.
 */
public interface IState {
    /**
     * Gets the state as a hashmap with Character as key and an array of vector2 as value, value[0] is position and value[1] is velocity
     * @return
     * State which needs to be set in gameLogic
     */
    public HashMap<Character, ArrayList<Vector2>> getCharacterStates();

    /**
     * Sets the state properties.
     * @param map
     * @param attackVectorMap
     * */
    void setState(HashMap<Character, ArrayList<Vector2>> map,HashMap<Attack,ArrayList<Vector2>> attackVectorMap);

    /**
     * Returns all the values of the active attacks.
     * @return
     */
    public HashMap<Attack,ArrayList<Vector2>> getAttackStates();

    /**
     * Compare with another state
     * @param map
     * remote state
     * @return
     * true if same, false else
     */
    boolean equals(HashMap<Character, ArrayList<Vector2>> map, HashMap<Attack,ArrayList<Vector2>> attackBodyVectorMap);


}
