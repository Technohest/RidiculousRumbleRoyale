package com.technohest.core.controller;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.technohest.constants.Constants;
import com.technohest.constants.Controls;
import com.technohest.core.model.RRRGameModel;
import com.technohest.core.view.RRRGameView;
import com.technohest.core.handlers.InputHandler;
import com.technohest.core.menu.ScreenHandler;

import java.io.Console;

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
        model.generateWorld();
    }

    public TiledMap getLevel() {
        return model.getLevel();
    }

    public void handleInput() {
        if(this.isPressed(InputHandler.ESCAPE)) {
            ScreenHandler.getInstance().setScreen("menu");
            this.releaseAllKeys();
        }
       if (this.isPressed(InputHandler.RIGHT)) {
            model.getPlayers().move(new Vector2(Constants.INITIAL_MOVEMENT_SPEED,0));

        } if(this.isPressed(InputHandler.LEFT)) {
            model.getPlayers().move(new Vector2(-Constants.INITIAL_MOVEMENT_SPEED, 0));


        }
        if(this.isPressed(InputHandler.JUMP)) {
            model.getPlayers().jump();

        }
        if(this.isPressed(InputHandler.BASE_ATTACK)) {
        }
    }


    public void updateGame(float v) {
        if(this.hasInput()) {
            this.handleInput();
        }
        model.step(v);
    }
}
