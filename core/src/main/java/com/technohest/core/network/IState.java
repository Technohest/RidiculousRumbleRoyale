package com.technohest.core.network;

import com.badlogic.gdx.math.Vector2;
import com.technohest.core.model.Character;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vilddjur on 2015-05-15.
 */
public interface IState {
    HashMap<Character, ArrayList<Vector2>>  getState();
    void                                    setState(HashMap<Character, ArrayList<Vector2>> map);

}
