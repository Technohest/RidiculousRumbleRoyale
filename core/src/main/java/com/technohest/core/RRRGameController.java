package com.technohest.core;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.technohest.assets.characters.Character;
import com.technohest.constants.Constants;
import com.technohest.constants.Controls;
import com.technohest.core.handlers.InputHandler;
import com.technohest.core.handlers.ScreenHandler;

import static com.technohest.constants.Constants.PPM;

/**
 * Created by Oskar on 2015-03-24.
 */
public class RRRGameController {
    private final RRRGameModel model;
    private RRRGameView view;
    private InputHandler inputHandler;
    public RRRGameController(RRRGameModel model) {
        this.model = model;
    }

    public TiledMap getLevel() {
        return model.getLevel();
    }



    public void handleInput(InputHandler handler) {
        if(handler.isPressed(InputHandler.ESCAPE)) {
            ScreenHandler.getInstance().setScreen("menu");
            handler.releaseAllKeys();
        }
        if (handler.isPressed(InputHandler.RIGHT)) {
            model.movePlayer(Controls.RIGHT);

        } if(handler.isPressed(InputHandler.LEFT)) {
            model.movePlayer(Controls.LEFT);

        }
        if(handler.isPressed(InputHandler.JUMP)) {
            model.movePlayer(Controls.UP);

        }
        if(handler.isPressed(InputHandler.BASE_ATTACK)) {
            model.movePlayer(Controls.BASE_ATTACK);
        }
    }


}
