package com.technohest.core;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.uwsoft.editor.renderer.Overlap2DStage;

/**
 * Created by Oskar on 2015-03-24.
 */
public class RRRGameController {
    private final RRRGameModel model;
    public RRRGameController(RRRGameModel model) {
        this.model = model;
    }

    public Overlap2DStage getStage() {
        return model.getStage();
    }
}
