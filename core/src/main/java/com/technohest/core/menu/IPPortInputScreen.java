package com.technohest.core.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Queries the user to enter an ip and/or port depending on if the current session is creating a server or not.
 * @author David Ström
 */
public class IPPortInputScreen implements Screen {
    private NetworkMenuInputProcessor networkMenuInputProcessor;
    private SpriteBatch batch;
    private BitmapFont font;

    public IPPortInputScreen() {
        init();
    }

    /**
     * Initializes the ip port screen.
     */
    private void init() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        networkMenuInputProcessor = new NetworkMenuInputProcessor();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(networkMenuInputProcessor);
        networkMenuInputProcessor.resetInput();
    }


    /**
     * Renders a simple screen for inputting ip and port.
     * @param v not used
     */
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        batch.begin();

        if (ScreenHandler.getInstance().getCurrentScreen() == SCREEN.JOIN) {
            font.draw(batch, "Enter IP: " + networkMenuInputProcessor.getInput(), 200, 200);
        } else {
            font.draw(batch, "Enter port: " + networkMenuInputProcessor.getInput(), 200, 200);
        }

        batch.end();
    }

    @Override
    public void resize(int i, int i1) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
/**
 * Manages the input done in the IPPortInputScreen, only allows numbers 0-9 and '.' as input which it saves to a
 * variable input.
 * @author David Ström
 */
class NetworkMenuInputProcessor implements InputProcessor {
    private String input = "";

    /**
     * Check if backspace, enter or escape has been pressed and performs an action mapped to each of these inputs.
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
            ScreenHandler.getInstance().setScreen(SCREEN.MAIN);
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
            ScreenHandler.getInstance().setScreen(SCREEN.LOBBY);
        } else if (ScreenHandler.getInstance().getCurrentScreen() == SCREEN.JOIN &&
                !NetworkMenuUtility.getInstance().isServer()){
            NetworkMenuUtility.getInstance().setIp(input);
            ScreenHandler.getInstance().setScreen(SCREEN.HOST);
        }
    }

    /**
     * Removes the last character of a String and returns it.
     * @param input the String to which will have its last character removed.
     * @return the resulting String.
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
     * @param c the keycode for the typed character.
     * @return if a valid key has been typed.
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
