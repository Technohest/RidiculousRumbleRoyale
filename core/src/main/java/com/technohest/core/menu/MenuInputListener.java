package com.technohest.core.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * A class handling input for the menu.
 * @author Oskar Jedvert.
 */
public class MenuInputListener extends InputListener {
    public final SCREEN target;

    /**
     * @param target the target SCREEN which will be set when the user calls the touchUp method when pressing a button.
     */
    public MenuInputListener(SCREEN target){
        this.target = target;
    }
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        ScreenHandler.getInstance().setScreen(target);
    }
}
