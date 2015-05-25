package com.technohest.LibgdxService;

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
    public HashMap<Integer, ArrayList<Vector2>> getCharacterIdStates() {
        HashMap<Integer,ArrayList<Vector2>> temp = new HashMap<Integer, ArrayList<Vector2>>();
        for(Character c:map.keySet()) {
            temp.put(c.getId(),map.get(c));
        }
        return temp;
    }
    @Override
    public HashMap<Integer,ArrayList<Vector2>> getAttackIdStates() {
        HashMap<Integer,ArrayList<Vector2>> temp = new HashMap<Integer, ArrayList<Vector2>>();
        for(Attack a:attackVectorMap.keySet()) {
            temp.put(a.getSourcePlayerId(),attackVectorMap.get(a));
        }
        return temp;
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