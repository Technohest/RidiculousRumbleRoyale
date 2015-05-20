package com.technohest.core.model;

/**
 *
 * Created by oskar on 2015-05-10.
 */
public class Action {
    private Integer sequenceNumber;
    private ActionID actionID;
    private Integer playerID;

    public Action(int playerID, ActionID actionID, int sequenceNumber){
        this.playerID = playerID;
        this.actionID = actionID;
        this.sequenceNumber = sequenceNumber;
    }

    public Action(int playerID, ActionID actionID) {
        this.playerID = playerID;
        this.actionID = actionID;
    }

    public Action() {}

    /**
     * Types of actions
     */
    public enum ActionID {
        MOVE_LEFT, MOVE_RIGHT, JUMP
    }

    public void setPlayerID(int id) {
        this.playerID = id;
    }

    public Integer getPlayerID() {
        return this.playerID;
    }

    public ActionID getActionID() {
        return actionID;
    }

    public void setActionID(ActionID actionID) {
        this.actionID = actionID;
    }

    public void setSequenceNumber(int sequenceNumber){
        this.sequenceNumber = sequenceNumber;
    }

    public Integer getSequenceNumber(){
        return sequenceNumber;
    }
}
