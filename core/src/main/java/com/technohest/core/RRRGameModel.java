package com.technohest.core;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Oskar on 2015-03-24.
 */
public class RRRGameModel {
    private LevelHandler levelHandler;
    public RRRGameModel(){
        this.levelHandler = new LevelHandler();
    }
    public Stage getStage() {
        return levelHandler;
    }
}
