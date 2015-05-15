package com.technohest.core.network;

import com.badlogic.gdx.math.Vector2;
import com.technohest.core.model.Character;

import java.util.HashMap;

/**
 * Created by vilddjur on 2015-05-15.
 */
public class StateGDX implements IState {
    private HashMap<Character, Vector2> map;

    private static StateGDX     instance = null;
    protected StateGDX(){
        map = new HashMap<Character, Vector2>();
    }

    public StateGDX getInstance(){
        if(instance == null){
            instance = new StateGDX();
        }
        return instance;
    }

    @Override
    public HashMap<Character, Vector2> getState() {
        return map;
    }

    @Override
    public void setState(HashMap<Character, Vector2> map) {
        this.map = map;
    }

    public boolean equals(HashMap<Character, Vector2> map){
        return this.map.equals(map);
    }
}