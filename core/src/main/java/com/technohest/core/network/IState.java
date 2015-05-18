package com.technohest.core.network;

import com.badlogic.gdx.math.Vector2;
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
    HashMap<Character, ArrayList<Vector2>>  getState();
    void                                    setState(HashMap<Character, ArrayList<Vector2>> map);

    /**
     * Compare with another state
     * @param map
     * remote state
     * @return
     * true if same, false else
     */
    boolean equals(HashMap<Character, ArrayList<Vector2>> map);


}
