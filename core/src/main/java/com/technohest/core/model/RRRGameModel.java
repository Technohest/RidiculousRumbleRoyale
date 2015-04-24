package com.technohest.core.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.technohest.core.handlers.LevelHandler;

/**
 * Created by Oskar on 2015-03-24.
 */
public class RRRGameModel {
    private LevelHandler levelHandler;
    public RRRGameModel(){
        this.levelHandler = new LevelHandler();
    }
    public TiledMap getLevel() {
        return levelHandler.getLevel();
    }
}
