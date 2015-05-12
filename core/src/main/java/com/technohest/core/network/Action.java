package com.technohest.core.network;

/**
 *
 * Created by oskar on 2015-05-10.
 */
public class Action {
    private long timestamp;
    private ActionID actionID;

    public Action(ActionID actionID, long timestamp){
        this.actionID = actionID;
        this.timestamp = timestamp;
    }

    public Action() {}

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

    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }

    public long getTimestamp(){
        return timestamp;
    }
}
