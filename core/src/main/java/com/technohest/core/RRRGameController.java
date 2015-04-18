package com.technohest.core;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.technohest.assets.characters.Character;
import com.technohest.core.handlers.InputHandler;
import com.technohest.core.handlers.ScreenHandler;

import static com.technohest.constants.Constants.PPM;

/**
 * Created by Oskar on 2015-03-24.
 */
public class RRRGameController {
    private final RRRGameModel model;
    private PlayerController playerController;
    public RRRGameController(RRRGameModel model) {
        this.model = model;
        playerController = new PlayerController(new Character(new Vector2(160/PPM,250/PPM)));
    }

    public TiledMap getLevel() {
        return model.getLevel();
    }

    public PlayerController getPlayerController() {
        return this.playerController;
    }
    public void update(float delta) {
        playerController.update(delta);
    }




    public void handleInput(InputHandler handler) {
        if(handler.isPressed(InputHandler.ESCAPE)) {
            ScreenHandler.getInstance().setScreen("menu");
            handler.releaseAllKeys();
        } else {
            playerController.handleInput(handler);
        }
    }
}
