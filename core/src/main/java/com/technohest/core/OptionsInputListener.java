package com.technohest.core;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

/**
 * Created by Oscar on 2015-04-20.
 */
public class OptionsInputListener extends InputListener {
    public final OptionsMenuView view;
    public final String target;

    /**
     * Creates a listener for the buttons in the options screen.
     * Mainly made for the SAVE button.
     * @param view
     * @param target
     */
    public OptionsInputListener(OptionsMenuView view, String target){
        this.view = view;
        this.target = target;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if(target == "save") {
            view.saveOptions();
        }else{
            view.switchTo(target);
        }
    }
}
