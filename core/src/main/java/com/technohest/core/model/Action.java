package com.technohest.core.model;

/**
 * A class representing an action to be sent over the network.
 * @author Oskar Jedvert
 * @author David Ström
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

    /**
     * Needed for serialization with KryoNet.
     */
    public Action() {}

    /**
     * Types of actions
     */
    public enum ActionID {
        MOVE_LEFT, MOVE_RIGHT, JUMP, ATTACK_BASE, ATTACK_SPECIAL
    }

    public Integer getPlayerID() {
        return this.playerID;
    }

    public ActionID getActionID() {
        return actionID;
    }

    public Integer getSequenceNumber(){
        return sequenceNumber;
    }
}
