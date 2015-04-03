package com.technohest.core;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Oskar on 2015-03-24.
 */
public class RRRController {
    private final RRRModel model;
    public RRRController(RRRModel model) {
        this.model = model;
    }

    public Stage getStage() {
        return model.getStage();
    }
}
