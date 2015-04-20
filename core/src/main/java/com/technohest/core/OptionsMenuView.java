package com.technohest.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

/**
 * Created by Oscar on 2015-04-11.
 */
public class OptionsMenuView implements Screen{

    private Skin skin;
    private TextureAtlas buttonAtlas;
    private BitmapFont font;
    private Stage stage;
    private final RRRMain game;
    private Skin labelSkin;

    public OptionsMenuView(RRRMain game){
        this.game = game;
        stage = new Stage();

        font = new BitmapFont();
        font.scale(1.3f);
        buttonAtlas = new TextureAtlas("assets/menuButtons.pack");

        skin = new Skin();
        skin.addRegions(buttonAtlas);

        labelSkin = new Skin();
        labelSkin.add("default", new BitmapFont());

        TextButtonStyle style = new TextButtonStyle();
        style.up = skin.getDrawable("menuButton");
        style.down = skin.getDrawable("pressedMenuButton");
        style.font=font;

        TextButton graphicsButton = new TextButton("Graphics", style);
        TextButton soundButton = new TextButton("Sound", style);
        TextButton gamePlayButton = new TextButton("Gameplay", style);
        TextButton textButton = new TextButton("Here there will be information corresponding to each button.", style);
        TextButton backButton = new TextButton("Back", style);

        graphicsButton.addListener(new MenuInputListener(this.game, "graphics"));
        soundButton.addListener(new MenuInputListener(this.game, "sound"));
        gamePlayButton.addListener(new MenuInputListener(this.game, "gamePlay"));
        textButton.addListener(new MenuInputListener(this.game, "swag"));
        backButton.addListener(new MenuInputListener(this.game, "backFromOptions"));

        Table buttonsTable = new Table();
        Table optionsTable = new Table();

        buttonsTable.add(graphicsButton);
        buttonsTable.add(soundButton);
        buttonsTable.add(gamePlayButton);
        buttonsTable.add(backButton);
        optionsTable.add(textButton);

        float x = (Gdx.graphics.getWidth())/2.0f;
        float y = (Gdx.graphics.getHeight())/2.0f;

        buttonsTable.setPosition(x, y+300);
        stage.addActor(buttonsTable);
        optionsTable.setPosition(x,y);
        stage.addActor(optionsTable);

    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        float r = (float) (9 / 255.0);
        float g = (float) (205 / 255.0);
        float b = (float) (218 / 255.0);

        Gdx.gl.glClearColor(r, g, b, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.draw();
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
        buttonAtlas.dispose();
        skin.dispose();
        font.dispose();
        stage.dispose();
    }
}
