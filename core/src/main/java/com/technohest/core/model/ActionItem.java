package com.technohest.core.model;

/**
 * Created by time on 2015-05-11.
 */
public class ActionItem {
    private int playerId;
    private Action action;
    private float time;

    public void setPlayerId(int id) {
        playerId = id;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Action getAction() {
        return action;
    }

    public float getTime() {
        return time;
    }
}
