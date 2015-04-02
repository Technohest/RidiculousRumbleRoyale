package com.technohest.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by vilddjur on 2015-04-02.
 */
public class RRRGUI implements ApplicationListener {
    private Stage           stage;

    private boolean         inMenu=true;

    private Skin            skin;
    private TextureAtlas    buttonAtlas;
    private BitmapFont      font;

    @Override
    public void create() {
        //Initialize
        stage = new Stage();
        /**
         * This sets the stage to be able to take in input (?)
         */
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        font.scale(2);
        /**
         * ButtonAtlas is a "Texture Sheet" that gets read through the .pack file,
         * skim the file (its in plaintext) and I think the code is self explanatory
         */
        buttonAtlas = new TextureAtlas("assets/menuButtons.pack");
        skin = new Skin();
        skin.addRegions(buttonAtlas);
        /**
         * We want both buttons to have a texture for when they have been pressed and when they are "idle"
         */
        TextButtonStyle style = new TextButtonStyle();
        style.up = skin.getDrawable("menuButton");
        style.down = skin.getDrawable("pressedMenuButton");
        style.font=font;

        TextButton exitButton = new TextButton("Exit", style);
        TextButton playButton = new TextButton("Play", style);

        playButton.addListener(new MenuInputListener());
        exitButton.addListener(new MenuInputListener());

        Table table = new Table();
        /**
         * Adding buttons to a Table to align easier
         */
        table.add(playButton);
        table.row();
        table.add(exitButton);
        /**
         * Making sure the table is centered
         */
        float x = (Gdx.graphics.getWidth())/2.0f;
        float y = (Gdx.graphics.getHeight())/2.0f;

        table.setPosition(x,y);

        stage.addActor(table);

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
            stage.draw();
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
        buttonAtlas.dispose();
        skin.dispose();
        font.dispose();
        stage.dispose();
    }
}
