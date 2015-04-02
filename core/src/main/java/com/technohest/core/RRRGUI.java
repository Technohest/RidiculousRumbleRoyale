package com.technohest.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;

/**
 * Created by vilddjur on 2015-04-02.
 */
public class RRRGUI implements ApplicationListener {
    private boolean inMenu=true;

    @Override
    public void create() {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        if(inMenu) {
            float r = (float) (9 / 255.0);
            float g = (float) (205 / 255.0);
            float b = (float) (218 / 255.0);

            Gdx.gl.glClearColor(r, g, b, 1);
            Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        }
    }
    private void startGame(){
        inMenu=false;
        RRRModel model = new RRRModel();
        RRRController controller = new RRRController(model);
        RRRView view = new RRRView(controller);
    }
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
