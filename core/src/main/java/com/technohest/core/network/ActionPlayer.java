package com.technohest.core.network;

/**
 * Keeps track of the what player performed what action.
 * Created by time on 5/12/15.
 */
public class ActionPlayer {
    private Action action;
    private Integer id;

    public ActionPlayer(final Action action, final Integer id) {
        this.action = action;
        this.id = id;
    }

    public ActionPlayer() {}

    public void setAction(Action action) {
        this.action = action;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Action getAction() {
        return action;
    }

    public Integer getId() {
        return id;
    }
}
