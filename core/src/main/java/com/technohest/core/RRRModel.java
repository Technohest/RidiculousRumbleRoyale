package com.technohest.core;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Oskar on 2015-03-24.
 */
public class RRRModel {
    private Level level;
    public RRRModel(){
        this.level = new Level();
    }
    public Stage getStage() {
        return level;
    }
}
