package com.technohest.core.network;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.technohest.core.menu.SCREEN;
import com.technohest.core.menu.ScreenHandler;

/**
 * Manages the input done in the IPPortInputScreen, only allows numbers 0-9 and '.' as input which it saves to a
 * variable input.
 * Created by time on 2015-04-21.
 */
public class NetworkMenuInputProcessor implements InputProcessor {
    private String input = "";

    /**
     * Check if backspace, enter or escape has been pressed and performs an action mapped to each of theese inputs.
     * @param i The keycode for the key that has been pressed.
     * @return If a valid key has been pressed.
     */
    @Override
    public boolean keyDown(int i) {
        // Deletes the last character
        if (i == Input.Keys.BACKSPACE && input.length() > 0) {
            input = shorten(input);
            return true;
        }

        // Goes to the next screen if needed
        if (i == Input.Keys.ENTER && input.length() > 0) {
            goToNextScreenIfNeeded();
            return true;
        }

        // Returnes to the main menu
        if (i == Input.Keys.ESCAPE) {
            ScreenHandler.getInstance().setScreen("menu");
            return true;
        }
        return false;
    }

    /**
     * Changes the screen to either the lobby or the host. The lobby if currently in the host screen and the host screen
     * if in the join screen and this session is not creating a server.
     */
    private void goToNextScreenIfNeeded() {
        if (ScreenHandler.getInstance().getCurrentScreen() == SCREEN.HOST) {
            NetworkMenuUtility.getInstance().setPort(input);
            ScreenHandler.getInstance().setScreen("lobby");
        } else if (ScreenHandler.getInstance().getCurrentScreen() == SCREEN.JOIN &&
                !NetworkMenuUtility.getInstance().isServer()){
            NetworkMenuUtility.getInstance().setIp(input);
            ScreenHandler.getInstance().setScreen("host");
        }
    }

    /**
     * Removes the last character of a String and returns it.
     * @param input The String to which will have its last character removed.
     * @return The resulting String.
     */
    private String shorten(String input) {
        return input.substring(0, input.length()-1);
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    /**
     * Adds the typed character to the input String if it is a number between 0-9 or a full stop character.
     * @param c The keycode for the typed character.
     * @return If a valid key has been typed.
     */
    @Override
    public boolean keyTyped(char c) {
        if (c >= '0' && c <= '9' || c == '.') {
            input += c;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }

    public String getInput() {
        return input;
    }

    /**
     * Resets the input String.
     */
    public void resetInput() {
        input = "";
    }
}
