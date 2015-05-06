package com.technohest.core;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by Oscar on 2015-04-24.
 */
public class OptionsFieldListener extends InputListener {
    private OptionsField optionsField;
    private String direction;

    public OptionsFieldListener(OptionsField optionsField, String direction){
        this.optionsField = optionsField;
        this.direction = direction;
    }
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        optionsField.switchTo(direction);
    }
}
