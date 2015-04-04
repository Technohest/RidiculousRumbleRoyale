package com.technohest.core;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Oskar on 2015-03-24.
 */
public class RRRModel {
    private LevelHandler levelHandler;
    public RRRModel(){
        this.levelHandler = new LevelHandler();
    }
    public Stage getStage() {
        return levelHandler;
    }
}
