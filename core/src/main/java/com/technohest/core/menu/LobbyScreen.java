package com.technohest.core.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.technohest.core.network.NetworkMenuUtility;

/**
 * Created by time on 2015-04-21.
 */
public class LobbyScreen implements Screen {
    @Override
    public void show() {
        System.out.println("IP: " + NetworkMenuUtility.getInstance().getIp());
        System.out.println("PORT: " + NetworkMenuUtility.getInstance().getPort());
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            ScreenHandler.getInstance().setScreen("menu");
        }
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

    }

    @Override
    public void dispose() {

    }
}
