package com.technohest.core.controller;

import com.badlogic.gdx.utils.TimeUtils;
import com.technohest.LibgdxService.levels.ILevel;
import com.technohest.core.model.Correction;
import com.technohest.core.menu.SCREEN;
import com.technohest.core.model.RRRGameModel;
import com.technohest.core.model.Action;
import com.technohest.core.network.ClientNetworkListener;
import com.technohest.core.handlers.InputHandler;
import com.technohest.core.menu.ScreenHandler;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * The controller handling the input from the user and mapping the input to actions.
 * @author Oskar Jedvert
 * @author Tobias Alldén
 */
public class RRRGameController extends InputHandler {
    private final RRRGameModel model;

    private ArrayList<Action.ActionID> actions = new ArrayList<Action.ActionID>();
    private ArrayList<RActionListener> listeners = new ArrayList<RActionListener>();

    private double accumulator = 0.0;
    private double currentTime;
    private float step = 1.0f/60.0f;


    public RRRGameController(RRRGameModel model) {
        super();
        this.model = model;
        model.generateWorld();
    }

    /**
     * Initializes the controller with a networklistener.
     */
    public void init() {
    }

    /**
     * Returns the current level.
     * @return
     */
    public ILevel getLevel() {
        return model.getGameLogic().getLevel();
    }


    public void handleInput() {
        if (this.isPressed(InputHandler.ESCAPE)) {
            /*if (listener != null)
                listener.killServer();*/
            ScreenHandler.getInstance().setScreen(SCREEN.MAIN);
            this.releaseAllKeys();
        }

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

        while(accumulator >= step) {
            if (this.hasInput())
                this.handleInput();

            for (RActionListener listener: listeners) {
                listener.actionReceived();
            }

            accumulator -= step;
        }

        Correction.getInstance().correctState(model);
    }

    public ArrayList<Action.ActionID> getActions() {
        return actions;
    }

    public void addActionListener(RActionListener listener) {
        listeners.add(listener);
    }

    public void clearActions() {
        actions.clear();
    }
}
