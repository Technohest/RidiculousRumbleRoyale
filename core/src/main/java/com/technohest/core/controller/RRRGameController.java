package com.technohest.core.controller;

import com.technohest.LibgdxService.ILevel;
import com.technohest.core.menu.SCREEN;
import com.technohest.core.model.RRRGameModel;
import com.technohest.core.model.Action;
import com.technohest.core.network.ClientNetworkListener;
import com.technohest.core.view.RRRGameView;
import com.technohest.core.handlers.InputHandler;
import com.technohest.core.menu.ScreenHandler;

/**
 * The controller handling the input from the user and mapping the input to actions.
 * Created by Oskar on 2015-03-24.
 */
public class RRRGameController extends InputHandler {
    private final RRRGameModel model;
    private ClientNetworkListener listener;
    private RRRGameView view;

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
            ScreenHandler.getInstance().setScreen(SCREEN.MAIN);
            this.releaseAllKeys();
        }
            if (this.isPressed(InputHandler.RIGHT)) {
                 model.performAction(new Action(model.getmyID(), Action.ActionID.MoveRight, 0));
                if (listener != null) {
                    listener.addAction(Action.ActionID.MoveRight);
                }

            }
            if (this.isPressed(InputHandler.LEFT)) {
                model.performAction(new Action(model.getmyID(), Action.ActionID.MoveLeft,0));
                if (listener != null)
                    listener.addAction(Action.ActionID.MoveRight);
            }
            if (this.isPressed(InputHandler.JUMP)) {
                model.performAction(new Action(model.getmyID(), Action.ActionID.Jump,0));
                if (listener != null)
                    listener.addAction(Action.ActionID.MoveRight);
            }
            if (this.isPressed(InputHandler.BASE_ATTACK)) {
            }
            if (this.isPressed(InputHandler.SPECIAL_ATTACK)) {
            }
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
