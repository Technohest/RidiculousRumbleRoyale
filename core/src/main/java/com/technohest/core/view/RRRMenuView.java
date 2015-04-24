package com.technohest.core.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.technohest.core.controller.MenuInputListener;

/**
 * Created by vilddjur on 2015-04-02.
 */
public class RRRMenuView implements Screen {
    private Stage           stage;

    private Skin            skin;
    private TextureAtlas    buttonAtlas;
    private BitmapFont      font;

    private TextButton exitButton;
    private TextButton playButton;
    private TextButton joinButton;
    private TextButton hostButton;

    private Table buttonTable;

    public RRRMenuView() {
        initVars();
    }

    /**
     * Initializes variables
     */
    private void initVars() {
        stage = new Stage();

        initButtons();

        buttonTable = new Table();
        /**
         * Adding buttons to a Table to align easier
         */
        buttonTable.add(joinButton);
        buttonTable.row();
        buttonTable.add(hostButton);
        buttonTable.row();
        buttonTable.add(playButton);
        buttonTable.row();
        buttonTable.add(exitButton);
        /**
         * Making sure the table is centered
         */
        float x = (Gdx.graphics.getWidth())/2.0f;
        float y = (Gdx.graphics.getHeight())/2.0f;
        buttonTable.setPosition(x,y);

        stage.addActor(buttonTable);
    }

    /**
     * Initializes buttons and sets them to the same skin
     */
    private void initButtons() {
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
        TextButtonStyle style = new TextButtonStyle(); //ITS THE NEW STYLE
        style.up = skin.getDrawable("menuButton");
        style.down = skin.getDrawable("pressedMenuButton");
        style.font=font;

        exitButton = new TextButton("Exit", style);
        playButton = new TextButton("Play", style);
        joinButton = new TextButton("Join", style);
        hostButton = new TextButton("Host", style);
        /**
         * See @class{MenuInputListener}
         */
        playButton.addListener(new MenuInputListener("game"));
        exitButton.addListener(new MenuInputListener("exit"));
        joinButton.addListener(new MenuInputListener("join"));
        hostButton.addListener(new MenuInputListener("host"));
    }

    @Override
    public void resize(int i, int i1) {
        stage.getViewport().update(i,i1,true);
    }

    @Override
    public void show() {
       Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        float r = (float) (9 / 255.0);
        float g = (float) (205 / 255.0);
        float b = (float) (218 / 255.0);

        Gdx.gl.glClearColor(r, g, b, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.draw();
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
