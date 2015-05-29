package com.technohest.LibgdxService;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class representing the current state of the game.
 * @author Oskar Jedvert.
 * @author Tobias Alldén.
 */
public class StateGDX implements IState {
    private HashMap<Integer, Vector2> characterIdVectorMap;
    private HashMap<Vector2,Integer> attackVectorTypeMap;

    private static StateGDX     instance = null;
    protected StateGDX(){
        characterIdVectorMap = new HashMap<Integer, Vector2>();
        attackVectorTypeMap = new HashMap<Vector2,Integer>();
    }

    public static StateGDX getInstance(){
        if(instance == null){
            instance = new StateGDX();
        }
        return instance;
    }

    @Override
    public HashMap<Integer, Vector2> getCharacterIdStates() {
       return this.characterIdVectorMap;
    }

    @Override
    public HashMap<Vector2,Integer> getAttackVectorTypeMap() {
        return attackVectorTypeMap;
    }

    @Override
    public void setCharacterIdVectorMap(HashMap<Integer, Vector2> idVectorMap) {
        this.characterIdVectorMap = idVectorMap;
    }

    @Override
    public void setAttackVectorTypeMap(HashMap<Vector2,Integer> idVectorMap) {
        this.attackVectorTypeMap = idVectorMap;
    }

    @Override
    public void setState(HashMap<Integer,Vector2> characterIdVectorMap,HashMap<Vector2,Integer> attackVectorTypeMap) {
        this.characterIdVectorMap = characterIdVectorMap;
        this.attackVectorTypeMap = attackVectorTypeMap;
    }

    @Override
    public boolean equals(HashMap<Integer, Vector2> map, HashMap<Vector2,Integer> attackVectorTypeMap){
        return this.characterIdVectorMap.equals(map) && attackVectorTypeMap.equals(this.attackVectorTypeMap);
    }
}