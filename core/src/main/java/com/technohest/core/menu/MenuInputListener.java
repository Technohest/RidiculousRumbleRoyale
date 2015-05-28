package com.technohest.core.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.technohest.core.menuState.SCREEN;
import com.technohest.core.menu.ScreenHandler;

/**
 * A class handling input for the menu.
 * @author Oskar Jedvert.
 */
public class MenuInputListener extends InputListener {
    public final SCREEN target;

    /**
     * Ok here we might not need the param game, since we might be able to override it from RRRMenuView
     * target is what we want to switch to on a click event, this is good cause if we want to add options
     * we only need to pass the target as "options" and add such a state to @class{RRRMain}
     * @param target
     * The target switch state
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
