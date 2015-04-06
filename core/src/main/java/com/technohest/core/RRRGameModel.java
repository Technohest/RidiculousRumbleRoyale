package com.technohest.core;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.uwsoft.editor.renderer.Overlap2DStage;

/**
 * Created by Oskar on 2015-03-24.
 */
public class RRRGameModel {
    private LevelHandler levelHandler;
    public RRRGameModel(){
        this.levelHandler = new LevelHandler();
    }
    public Overlap2DStage getStage() {
        return levelHandler;
    }
}
