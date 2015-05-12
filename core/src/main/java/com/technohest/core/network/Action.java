package com.technohest.core.network;

/**
 * Created by oskar on 2015-05-10.
 */
public class Action {
    private int timestamp;
    private ActionID actionID;

    public Action(ActionID actionID, int timestamp){
        this.actionID = actionID;
        this.timestamp = timestamp;
    }

    /**
     * Types of actions
     */
    public enum ActionID {
        MoveLeft, MoveRight, Jump
    }

    public ActionID getActionID() {
        return actionID;
    }

    public void setActionID(ActionID actionID) {
        this.actionID = actionID;
    }

    public void setTimestamp(int timestamp){
        this.timestamp = timestamp;
    }

    public int getTimestamp(){
        return timestamp;
    }
}
