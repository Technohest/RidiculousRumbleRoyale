package com.technohest.core.controller;

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

    private ArrayList<Action> performedActions = new ArrayList<Action>();

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
            time = System.currentTimeMillis();
            model.performAction(new Action(model.getmyID(), Action.ActionID.MOVE_RIGHT, time));
            performedActions.add(new Action(model.getmyID(), Action.ActionID.MOVE_RIGHT, time));
            blocking++;
            if (listener != null)
                listener.addAction(Action.ActionID.MOVE_RIGHT, time);
        }
        if (this.isPressed(InputHandler.LEFT)) {
            time = System.currentTimeMillis();
            model.performAction(new Action(model.getmyID(), Action.ActionID.MOVE_LEFT, time));
            performedActions.add(new Action(model.getmyID(), Action.ActionID.MOVE_LEFT, time));
            blocking++;
            if (listener != null)
                listener.addAction(Action.ActionID.MOVE_LEFT, time);
        }
        if (this.isPressed(InputHandler.JUMP)) {
            time = System.currentTimeMillis();
            model.performAction(new Action(model.getmyID(), Action.ActionID.JUMP, time));
            performedActions.add(new Action(model.getmyID(), Action.ActionID.JUMP, time));
            blocking++;
            if (listener != null)
                listener.addAction(Action.ActionID.JUMP, time);
        }
        if (this.isPressed(InputHandler.BASE_ATTACK)) {}
    }

    public void update(float v) {
        if (this.hasInput()) {
            this.handleInput();
        }

        if (listener != null) {
            listener.sendActionsToServerIfNecissary();
        }

        model.step(v);
        view.update(v);
    }
}
