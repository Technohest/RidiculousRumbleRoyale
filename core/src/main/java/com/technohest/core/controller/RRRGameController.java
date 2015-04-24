package com.technohest.core.controller;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.technohest.constants.Controls;
import com.technohest.core.model.RRRGameModel;
import com.technohest.core.view.RRRGameView;
import com.technohest.core.handlers.InputHandler;
import com.technohest.core.menu.ScreenHandler;

/**
 * Created by Oskar on 2015-03-24.
 */
public class RRRGameController extends InputHandler{
    private final RRRGameModel  model;
    private final RRRGameView   view;

    public RRRGameController(RRRGameModel model, RRRGameView view) {
        super();
        this.model = model;
        this.view = view;
    }

    public TiledMap getLevel() {
        return model.getLevel();
    }

    public void handleInput() {
        if(this.isPressed(InputHandler.ESCAPE)) {
            ScreenHandler.getInstance().setScreen("menu");
            this.releaseAllKeys();
        }
       /*if (handler.isPressed(InputHandler.RIGHT)) {
            model.movePlayer(Controls.RIGHT);

        } if(handler.isPressed(InputHandler.LEFT)) {
            model.movePlayer(Controls.LEFT);

        }
        if(handler.isPressed(InputHandler.JUMP)) {
            model.movePlayer(Controls.UP);

        }
        if(handler.isPressed(InputHandler.BASE_ATTACK)) {
            model.movePlayer(Controls.BASE_ATTACK);
        }*/
    }


    public void updateGame(float v) {
        if(this.hasInput()) {
            this.handleInput();
        }
        model.step(v);
    }
}
