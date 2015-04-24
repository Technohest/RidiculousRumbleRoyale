package com.technohest.core.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.technohest.core.menu.SCREEN;
import com.technohest.core.menu.ScreenHandler;
import com.technohest.core.network.NetworkMenuInputProcessor;

/**
 * Queries the user to enter an ip or port depending on if the current session is creating a server or not.
 * Created by time on 2015-04-21.
 */
public class IPPortInputScreen implements Screen {
    private NetworkMenuInputProcessor networkMenuInputProcessor;
    private SpriteBatch batch;
    private BitmapFont font;

    public IPPortInputScreen() {
        init();
    }

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
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

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
