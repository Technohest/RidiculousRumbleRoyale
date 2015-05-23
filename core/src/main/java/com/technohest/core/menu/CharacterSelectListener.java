package com.technohest.core.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.technohest.core.OptionsField;

/**
 * Created by Oscar on 2015-05-20.
 */
public class CharacterSelectListener extends InputListener {
    private LobbyScreen lobbyScreen;
    private String character;

    /**
     * Creates the listener for the characters in the lobby
     * @param lobbyScreen
     * @param character
     */
    public CharacterSelectListener(LobbyScreen lobbyScreen, String character){
        this.lobbyScreen = lobbyScreen;
        this.character = character;
    }
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        lobbyScreen.selectCharacter(character);
    }
}
