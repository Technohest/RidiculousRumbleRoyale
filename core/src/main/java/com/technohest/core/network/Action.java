package com.technohest.core.network;

import com.technohest.core.model.Character;

/**
 * Created by oskar on 2015-05-10.
 */
public abstract class Action {
    private Character target;
    public Character getTarget(){
        return target;
    }
    public void setTarget(Character character){
        target = character;
    }

    public abstract void act();
}
