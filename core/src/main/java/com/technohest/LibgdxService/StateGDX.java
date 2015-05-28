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
    private HashMap<Integer, ArrayList<Vector2>> characterVectormap;
    private HashMap<Attack,ArrayList<Vector2>> attackVectorMap;

    private static StateGDX     instance = null;
    protected StateGDX(){
        characterVectormap = new HashMap<Integer, ArrayList<Vector2>>();
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
        for(Integer i: characterVectormap.keySet()) {
            temp.put(i, characterVectormap.get(i));
        }
        return temp;
    }

    @Override
    public HashMap<Integer, ArrayList<Vector2>> getAttackIdStates() {
        HashMap<Integer,ArrayList<Vector2>> temp = new HashMap<Integer, ArrayList<Vector2>>();
        for(Attack a:attackVectorMap.keySet()) {
            temp.put(a.getSourcePlayerId(),attackVectorMap.get(a));
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
    public void setAlivePlayers(ArrayList<Integer> players) {
        for(Integer i:players) {
            characterVectormap.put(i,null);
        }
    }

    @Override
    public void setState(HashMap<Integer, ArrayList<Vector2>> map,HashMap<Attack,ArrayList<Vector2>> attackVectorMap) {
        this.characterVectormap = map;
        this.attackVectorMap = attackVectorMap;
    }
    @Override
    public void setCharacterIdVectorMap(HashMap<Integer,ArrayList<Vector2>> idVectorMap) {
        for(Integer i: characterVectormap.keySet()) {
            if(idVectorMap.containsKey(i)) {
                characterVectormap.put(i,idVectorMap.get(i));
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
    public boolean equals(HashMap<Integer, ArrayList<Vector2>> map, HashMap<Attack,ArrayList<Vector2>> attackBodyVectorMap){
        return this.characterVectormap.equals(map) && attackBodyVectorMap.equals(this.attackVectorMap);
    }
}