package com.technohest.core.controller;

import com.badlogic.gdx.utils.TimeUtils;
import com.technohest.LibgdxService.ILevel;
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
    private int blocking = 0;

    private double accumulator = 0.0;
    private double currentTime;
    private float step = 1.0f/60.0f;

    //TMP
    private long time = 0;

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
        if (this.isPressed(InputHandler.SPECIAL_ATTACK)) {
            if (listener != null)
                listener.sync();
        }

        /*if (blocking > 10)
            return;*/
        if (this.isPressed(InputHandler.RIGHT)) {
            model.performAction(new Action(model.getmyID(), Action.ActionID.MOVE_RIGHT));
            blocking++;
            if (listener != null)
                listener.addAction(Action.ActionID.MOVE_RIGHT);
        }
        if (this.isPressed(InputHandler.LEFT)) {
            model.performAction(new Action(model.getmyID(), Action.ActionID.MOVE_LEFT));
            blocking++;
            if (listener != null)
                listener.addAction(Action.ActionID.MOVE_LEFT);
        }
        if (this.isPressed(InputHandler.JUMP)) {
            model.performAction(new Action(model.getmyID(), Action.ActionID.JUMP));
            blocking++;
            if (listener != null)
                listener.addAction(Action.ActionID.JUMP);
        }
        if (this.isPressed(InputHandler.BASE_ATTACK)) {}
    }

    public void update(float v) {
        double  newTime = TimeUtils.millis() / 1000.0;
        double  frameTime = Math.min(newTime - currentTime, 0.25);
        float   deltaTime = (float)frameTime;

        currentTime = newTime;

        accumulator += frameTime;

        if (this.hasInput()) {
            this.handleInput();
        }

        if (listener != null) {
            listener.sendActionsToServerIfNecessary();
        }
        while(accumulator >= step) {
            model.step(step);
            accumulator -= step;
        }
        view.update(v);
    }
}
