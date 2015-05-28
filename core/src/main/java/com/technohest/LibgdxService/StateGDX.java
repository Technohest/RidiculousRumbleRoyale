package com.technohest.LibgdxService;

import com.badlogic.gdx.math.Vector2;
import com.technohest.core.model.Attack;
import com.technohest.core.model.Character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A class representing the current state of the game.
 * @author Oskar Jedvert.
 * @author Tobias Alldén.
 */
public class StateGDX implements IState {
    private HashMap<Character, ArrayList<Vector2>> characterVectormap;
    private HashMap<Attack,ArrayList<Vector2>> attackVectorMap;

    private static StateGDX     instance = null;
    protected StateGDX(){
        characterVectormap = new HashMap<Character, ArrayList<Vector2>>();
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
        for(Map.Entry<Character, ArrayList<Vector2>> entry : characterVectormap.entrySet()) {
            temp.put(entry.getKey().getId(),characterVectormap.get(entry.getKey()));
        }
        return temp;
    }
    @Override
    public HashMap<Integer,ArrayList<Vector2>> getAttackIdStates() {
        HashMap<Integer,ArrayList<Vector2>> temp = new HashMap<Integer, ArrayList<Vector2>>();
        for(Map.Entry<Attack, ArrayList<Vector2>> entry : attackVectorMap.entrySet()) {
            temp.put(entry.getKey().getSourcePlayerId(),attackVectorMap.get(entry.getKey()));
        }
        return temp;
    }

    @Override
    public ArrayList<Attack> getActiveAttacks() {
        return new ArrayList<Attack>(attackVectorMap.keySet());
    }
    @Override
    public void setActiveAttacks(ArrayList<Attack> attacks) {
        for(Attack a:attacks) {
            attackVectorMap.put(a,null);
        }
    }
    @Override
    public void setAlivePlayers(ArrayList<Character> players) {
        for(Character c:players) {
            characterVectormap.put(c,null);
        }
    }

    @Override
    public void setState(HashMap<Character, ArrayList<Vector2>> map,HashMap<Attack,ArrayList<Vector2>> attackVectorMap) {
        this.characterVectormap = map;
        this.attackVectorMap = attackVectorMap;
    }
    @Override
    public void setCharacterIdVectorMap(HashMap<Integer,ArrayList<Vector2>> idVectorMap) {
        for(Character c: characterVectormap.keySet()) {
            if(idVectorMap.containsKey(c.getId())) {
                characterVectormap.put(c,idVectorMap.get(c.getId()));
            }
        }
    }

    @Override
    public void setAttackIdVectorMap(HashMap<Integer,ArrayList<Vector2>> idVectorMap) {
        for(Attack a:attackVectorMap.keySet()) {
            if(idVectorMap.containsKey(a.getSourcePlayerId())) {
                attackVectorMap.put(a,idVectorMap.get(a.getSourcePlayerId()));
            }
        }
    }

    @Override
    public boolean equals(HashMap<Character, ArrayList<Vector2>> map, HashMap<Attack,ArrayList<Vector2>> attackBodyVectorMap){
        return this.characterVectormap.equals(map) && attackBodyVectorMap.equals(this.attackVectorMap);
    }
}