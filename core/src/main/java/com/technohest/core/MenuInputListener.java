package com.technohest.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by vilddjur on 2015-04-02.
 */
public class MenuInputListener extends InputListener {
    public final RRRMain    game;
    public final String     target;

    /**
     * Ok here we might not need the param game, since we might be able to override it from RRRGUI
     * target is what we want to switch to on a click event, this is good cause if we want to add options
     * we only need to pass the target as "options" and add such a screen to @class{RRRMain}
     * @param game
     * @param target
     */
    public MenuInputListener(RRRMain game, String target){
        this.game = game;
        this.target = target;
    }
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        game.switchTo(target);
    }
}
