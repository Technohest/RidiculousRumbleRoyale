package com.technohest.core;

import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * Created by Oskar on 2015-03-24.
 */
public class RRRGameController {
    private final RRRGameModel model;
    public RRRGameController(RRRGameModel model) {
        this.model = model;
    }

    public TiledMap getLevel() {
        return model.getLevel();
    }
}
