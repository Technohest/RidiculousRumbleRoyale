package com.technohest.core.controller;

import com.technohest.LibgdxService.ILevel;
import com.technohest.core.model.RRRGameModel;
import com.technohest.core.network.Action;
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
            ScreenHandler.getInstance().setScreen("menu");
            this.releaseAllKeys();
        }

        if (listener != null) {
            if (this.isPressed(InputHandler.RIGHT)) {
                model.moveRight(model.getmyID());
                if (listener != null)
                    listener.addAction(Action.ActionID.MoveRight);

            }
            if (this.isPressed(InputHandler.LEFT)) {
                model.moveLeft(model.getmyID());
                if (listener != null)
                    listener.addAction(Action.ActionID.MoveRight);
            }
            if (this.isPressed(InputHandler.JUMP)) {
                model.jump(model.getmyID());
                if (listener != null)
                    listener.addAction(Action.ActionID.MoveRight);
            }
            if (this.isPressed(InputHandler.BASE_ATTACK)) {
                model.attack_base(model.getmyID());
                if (listener != null)
                    listener.addAction(Action.ActionID.MoveRight);
            }
            if (this.isPressed(InputHandler.SPECIAL_ATTACK)) {
                model.attack_special(model.getmyID());
            }
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
