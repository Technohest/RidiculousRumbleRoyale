package com.technohest.core.network;

import com.badlogic.gdx.math.Vector2;
import com.technohest.core.model.Character;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vilddjur on 2015-05-15.
 */
public class StateGDX implements IState {
    private HashMap<Character, ArrayList<Vector2>> map;

    private static StateGDX     instance = null;
    protected StateGDX(){
        map = new HashMap<Character, ArrayList<Vector2>>();
    }

    public static StateGDX getInstance(){
        if(instance == null){
            instance = new StateGDX();
        }
        return instance;
    }

    @Override
    public HashMap<Character, ArrayList<Vector2>> getState() {
        return map;
    }

    @Override
    public void setState(HashMap<Character, ArrayList<Vector2>> map) {
        this.map = map;
    }

    @Override
    public boolean equals(HashMap<Character, ArrayList<Vector2>> map){
        return this.map.equals(map);
    }
}