package com.technohest.core.network;

/**
 * Created by oskar on 2015-05-10.
 */
public abstract class Action {
    private int id;
    private int timestamp;

    /**
     * Id of the original player that made the action
     * @return
     * The ID
     */
    public int getId(){
        return id;
    }

    /**
     * Set the id to the id of the original player
     * @param a
     * The ID
     */
    public void setId(int a){
        id = a;
    }

    public void setTimestamp(int timestamp){
        this.timestamp = timestamp;
    }

    public int getTimestamp(){
        return timestamp;
    }

    public abstract void act();
}
