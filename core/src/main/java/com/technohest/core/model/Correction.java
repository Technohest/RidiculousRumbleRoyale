package com.technohest.core.model;


import com.technohest.LibgdxService.IState;

/**
 * A singleton for correction if client gets unsynced.
 * @author David Ström.
 */
public class Correction {

    private static Correction instance = null;
    private IState newState = null;

    public Correction() {}

    public synchronized static Correction getInstance() {
        if (instance == null)
            instance = new Correction();

        return instance;
    }

    public void setNewState(IState state) {
        this.newState = state;
    }

    public void correctState(RRRGameModel model) {
        if (newState == null || model == null)
            return;

        model.correct(newState);
    }
}
