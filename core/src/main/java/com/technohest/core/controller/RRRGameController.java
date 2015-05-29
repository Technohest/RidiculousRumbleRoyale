package com.technohest.core.controller;

import com.badlogic.gdx.utils.TimeUtils;
import com.technohest.LibgdxService.levels.ILevel;
import com.technohest.core.event.REventListener;
import com.technohest.core.model.Correction;
import com.technohest.core.model.RRRGameModel;
import com.technohest.core.model.Action;
import com.technohest.core.handlers.InputHandler;

import java.util.ArrayList;

/**
 * The controller handling the input from the user and mapping the input to actions.
 * @author Oskar Jedvert
 * @author Tobias Alldén
 * @author David Ström
 */
public class RRRGameController extends InputHandler {
    private final RRRGameModel model;

    private ArrayList<Action.ActionID> actions = new ArrayList<Action.ActionID>();
    private ArrayList<REventListener> listeners = new ArrayList<REventListener>();

    private double accumulator = 0.0;
    private double currentTime;
    private float step = 1.0f/60.0f;


    public RRRGameController(RRRGameModel model) {
        //Initialize the model state
        super();
        this.model = model;
        model.generateWorld();
    }

    /**
     * Returns the current level.
     * @return level
     */
    public ILevel getLevel() {
        return model.getGameLogic().getLevel();
    }

    /**
     * Handles the user input
     */
    public void handleInput() {
        if (this.isPressed(InputHandler.RIGHT)) {
            actions.add(Action.ActionID.MOVE_RIGHT);
        }
        if (this.isPressed(InputHandler.LEFT)) {
            actions.add(Action.ActionID.MOVE_LEFT);
        }
        if (this.isPressed(InputHandler.JUMP)) {
            actions.add(Action.ActionID.JUMP);
        }
        if (this.isPressed(InputHandler.BASE_ATTACK)) {
            actions.add(Action.ActionID.ATTACK_BASE);
        }
        if (this.isPressed(InputHandler.SPECIAL_ATTACK)) {
            actions.add(Action.ActionID.ATTACK_SPECIAL);
        }
    }

    /**
     * Updates the input and sends actions to the server every "step".
     */
    public void update(float v) {
        double  newTime = TimeUtils.millis() / 1000.0;
        double  frameTime = Math.min(newTime - currentTime, 0.25);

        currentTime = newTime;
        accumulator += frameTime;

        //Gathers input and notifies all listeners it is time to update.
        while(accumulator >= step) {
            if (this.hasInput())
                this.handleInput();

            for (REventListener listener: listeners) {
                listener.fireEvent();
            }

            accumulator -= step;
        }
    }

    /**
     * @return ArrayList of all actions.
     */
    public ArrayList<Action.ActionID> getActions() {
        return actions;
    }

    /**
     * @param listener The REventListener to subscribe to this class.
     */
    public void addEventListener(REventListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes all old actions.
     */
    public void clearActions() {
        actions.clear();
    }
}
