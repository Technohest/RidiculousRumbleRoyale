package com.technohest.core.controller;

import com.badlogic.gdx.utils.TimeUtils;
import com.technohest.LibgdxService.ILevel;
import com.technohest.Tools.Correction;
import com.technohest.core.menu.SCREEN;
import com.technohest.core.model.RRRGameModel;
import com.technohest.core.model.Action;
import com.technohest.core.network.ClientNetworkListener;
import com.technohest.core.view.RRRGameView;
import com.technohest.core.handlers.InputHandler;
import com.technohest.core.menu.ScreenHandler;

import java.util.ArrayList;

/**
 * The controller handling the input from the user and mapping the input to actions.
 * Created by Oskar on 2015-03-24.
 */
public class RRRGameController extends InputHandler {
    private final RRRGameModel model;
    private ClientNetworkListener listener;
    private RRRGameView view;

    private double accumulator = 0.0;
    private double currentTime;
    private float step = 1.0f/60.0f;

    public RRRGameController(RRRGameModel model) {
        super();
        this.model = model;
        model.generateWorld();
    }

    public void init(ClientNetworkListener listener) {
        this.listener = listener;
    }

    public void setView(RRRGameView view) {
        this.view = view;
    }

    public ILevel getLevel() {
        return model.getLevel();
    }

    public void handleInput() {
        if (this.isPressed(InputHandler.ESCAPE)) {
            if (listener != null)
                listener.killServer();
            ScreenHandler.getInstance().setScreen(SCREEN.MAIN);
            this.releaseAllKeys();
        }

        if (listener != null) {
            if (this.isPressed(InputHandler.RIGHT)) {
                listener.addAction(Action.ActionID.MOVE_RIGHT);
            }
            if (this.isPressed(InputHandler.LEFT)) {
                listener.addAction(Action.ActionID.MOVE_LEFT);
            }
            if (this.isPressed(InputHandler.JUMP)) {
                listener.addAction(Action.ActionID.JUMP);
            }
            if (this.isPressed(InputHandler.BASE_ATTACK)) {
                listener.addAction(Action.ActionID.ATTACK_BASE);
            }
            if (this.isPressed(InputHandler.SPECIAL_ATTACK)) {
               // listener.addAction(Action.ActionID.ATTACK_SPECIAL);
            }
        }
    }

    /**
     * Updates the input and sends actions to the server every "step". Updates the view and the state as fast as
     * possible.
     */
    public void update(float v) {
        double  newTime = TimeUtils.millis() / 1000.0;
        double  frameTime = Math.min(newTime - currentTime, 0.25);

        currentTime = newTime;
        accumulator += frameTime;

        while(accumulator >= step) {
            if (this.hasInput())
                this.handleInput();

            if (listener != null)
                listener.sendActionsToServerIfNecessary();

            accumulator -= step;
        }

        Correction.getInstance().correctState(model);
        view.update(v);
    }
}
