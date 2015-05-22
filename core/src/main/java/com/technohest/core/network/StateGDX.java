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
public class StateGDX implements IState {
    private HashMap<Character, ArrayList<Vector2>> map;
    private HashMap<Attack,Body> attackBodyMap;

    private static StateGDX     instance = null;
    protected StateGDX(){
        map = new HashMap<Character, ArrayList<Vector2>>();
        attackBodyMap = new HashMap<Attack, Body>();
    }

    public static StateGDX getInstance(){
        if(instance == null){
            instance = new StateGDX();
        }
        return instance;
    }

    @Override
    public HashMap<Character, ArrayList<Vector2>> getCharacterStates() {
        return map;
    }
    @Override
    public HashMap<Attack,Body> getAttackStates() {
        return attackBodyMap;
    }

    @Override
    public void setState(HashMap<Character, ArrayList<Vector2>> map,HashMap<Attack,Body> bodyAttackMap) {
        this.map = map;
        this.attackBodyMap = bodyAttackMap;
    }

    @Override
    public boolean equals(HashMap<Character, ArrayList<Vector2>> map, HashMap<Attack,Body> attackBodyMap){
        return this.map.equals(map) && attackBodyMap.equals(this.attackBodyMap);
    }
}