package com.technohest.core;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Listens to the option field
 * @author Oscar Boking
 */
public class OptionsFieldListener extends InputListener {
    private OptionsField optionsField;
    private String direction;

    /**
     * Creates the listener for the buttons for an OptionsField
     * @param optionsField
     * @param direction
     */
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
