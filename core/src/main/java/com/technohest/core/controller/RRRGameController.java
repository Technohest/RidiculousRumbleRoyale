package com.technohest.core.controller;

import com.technohest.LibgdxService.ILevel;
import com.technohest.core.model.RRRGameModel;
import com.technohest.core.network.Action;
import com.technohest.core.view.RRRGameView;
import com.technohest.core.handlers.InputHandler;
import com.technohest.core.menu.ScreenHandler;

/**
 * Created by Oskar on 2015-03-24.
 */
public class RRRGameController extends InputHandler {
    private final RRRGameModel model;
    private RRRGameView view;

    public RRRGameController(RRRGameModel model) {
        super();
        this.model = model;
        model.generateWorld();
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
       if (this.isPressed(InputHandler.RIGHT)) {
            model.performAction(model.getmyID(),new Action(Action.ActionID.MoveRight, 0));

        } if(this.isPressed(InputHandler.LEFT)) {
            model.performAction(model.getmyID(),new Action(Action.ActionID.MoveLeft, 0));
        }
        if(this.isPressed(InputHandler.JUMP)) {
            model.performAction(model.getmyID(),new Action(Action.ActionID.Jump, 0));
        }
        if(this.isPressed(InputHandler.BASE_ATTACK)) {
            //To be implemented
        }
        if(this.isPressed(InputHandler.SPECIAL_ATTACK)) {
            //to be implemented
        }
    }


    public void update(float v) {
        if (this.hasInput()) {
            this.handleInput();
        }
        model.step(v);


        view.update(v);
    }
}
