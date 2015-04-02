package com.technohest.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by vilddjur on 2015-04-02.
 */
public class MenuInputListener extends InputListener {
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        Gdx.app.log("RidiculousRumbleRoyale", "Pressed");
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        Gdx.app.log("RidiculousRumbleRoyale", "Released");
    }
}
