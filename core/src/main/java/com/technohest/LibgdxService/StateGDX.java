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
    private HashMap<Integer,Vector2> attackIdVectorMap;

    private static StateGDX     instance = null;
    protected StateGDX(){
        characterIdVectorMap = new HashMap<Integer, Vector2>();
        attackIdVectorMap = new HashMap<Integer, Vector2>();
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
    public HashMap<Integer, Vector2> getAttackIdStates() {
        return attackIdVectorMap;
    }

    @Override
    public void setCharacterIdVectorMap(HashMap<Integer, Vector2> idVectorMap) {
        this.characterIdVectorMap = idVectorMap;
    }

    @Override
    public void setAttackIdVectorMap(HashMap<Integer, Vector2> idVectorMap) {
        this.attackIdVectorMap = idVectorMap;
    }

    @Override
    public void setState(HashMap<Integer, Vector2> characterIdVectorMap,HashMap<Integer,Vector2> attackIdVectorMap) {
        this.characterIdVectorMap = characterIdVectorMap;
        this.attackIdVectorMap = attackIdVectorMap;
    }

    @Override
    public boolean equals(HashMap<Integer, Vector2> map, HashMap<Integer,Vector2> attackIdVectorMap){
        return this.characterIdVectorMap.equals(map) && attackIdVectorMap.equals(this.attackIdVectorMap);
    }
}