package com.technohest.core.network;

import com.badlogic.gdx.math.Vector2;
import com.technohest.core.model.Attack;
import com.technohest.core.model.Character;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class representing the current state of the game.
 * @author Oskar Jedvert.
 * @author Tobias Alldén.
 */
public class StateGDX implements IState {
    private HashMap<Character, ArrayList<Vector2>> map;
    private HashMap<Attack,ArrayList<Vector2>> attackVectorMap;

    private static StateGDX     instance = null;
    protected StateGDX(){
        map = new HashMap<Character, ArrayList<Vector2>>();
        attackVectorMap = new HashMap<Attack, ArrayList<Vector2>>();
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
    public HashMap<Attack,ArrayList<Vector2>> getAttackStates() {
        return attackVectorMap;
    }

    @Override
    public void setState(HashMap<Character, ArrayList<Vector2>> map,HashMap<Attack,ArrayList<Vector2>> attackVectorMap) {
        this.map = map;
        this.attackVectorMap = attackVectorMap;
    }

    @Override
    public boolean equals(HashMap<Character, ArrayList<Vector2>> map, HashMap<Attack,ArrayList<Vector2>> attackBodyVectorMap){
        return this.map.equals(map) && attackBodyVectorMap.equals(this.attackVectorMap);
    }
}