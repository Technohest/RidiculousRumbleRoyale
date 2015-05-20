package com.technohest.core.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.technohest.core.network.NetworkMenuUtility;
import com.technohest.core.network.RClient;
import com.technohest.core.network.RServer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * A lobby where all clients connect before a game and can set the game options before starting.
 * Created by time on 2015-04-21.
 */
public class LobbyScreen implements Screen {

    private NetworkMenuUtility nmu;
    private TextButton.TextButtonStyle style;
    private Skin skin;
    private TextureAtlas buttonAtlas;
    private BitmapFont font;
    private float x;
    private float y;
    private TextButton startButton;

    private ImageButton char1Button;
    private Texture char11Texture;
    private SpriteDrawable char1Draw;
    private Sprite char1Sprite;
    private ImageButton character2;
    private ImageButton character3;
    private ImageButton character4;
    private Stage stage;
    private Table characterTable;
    private Table mainTable;

    public LobbyScreen(){

        nmu = NetworkMenuUtility.getInstance();

        //Fontsize
        font = new BitmapFont();
        font.scale(1.3f);

        //Screen Properties
        x = (Gdx.graphics.getWidth())/2.0f;
        y = (Gdx.graphics.getHeight())/2.0f;

        //ButtonStyle
        buttonAtlas = new TextureAtlas("assets/menuButtons.pack");
        skin = new Skin();
        skin.addRegions(buttonAtlas);
        style = new TextButton.TextButtonStyle();
        style.up = skin.getDrawable("menuButton");
        style.down = skin.getDrawable("pressedMenuButton");
        style.font=font;

        //CharacterButtons
        char11Texture = new Texture(new FileHandle("assets/allden.png"));
        char1Sprite = new Sprite(char11Texture,100,200);
        char1Draw = new SpriteDrawable(char1Sprite);
        char1Button = new ImageButton(char1Draw);

        startButton = new TextButton("Start", style);
        startButton.setPosition(x/(3/4), y/5);
        startButton.setDisabled(true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);


        if (nmu.isServer()) {
            new RServer(nmu.getPort());
            new RClient(nmu.getPort());
        } else {
            new RClient(nmu.getIp(), nmu.getPort());
        }

        //If the player is host, show start button
        startButton.setDisabled(false);

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            ScreenHandler.getInstance().setScreen(SCREEN.MAIN);
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
