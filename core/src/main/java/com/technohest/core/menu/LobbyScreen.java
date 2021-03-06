package com.technohest.core.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.technohest.core.network.RClient;
import com.technohest.core.network.RServer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.technohest.core.view.RRRGameView;

/**
 * A lobby where all clients connect before a game and can set the game options before starting.
 * @author David Ström
 * @author Oskar Boking
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
    private Texture char1Texture;
    private SpriteDrawable char1Draw;
    private Sprite char1Sprite;

    private ImageButton char2Button;
    private Texture char2Texture;
    private SpriteDrawable char2Draw;
    private Sprite char2Sprite;

    private ImageButton char3Button;
    private Texture char3Texture;
    private SpriteDrawable char3Draw;
    private Sprite char3Sprite;

    private ImageButton char4Button;
    private Texture char4Texture;
    private SpriteDrawable char4Draw;
    private Sprite char4Sprite;

    private Stage stage;
    private Table characterTable;
    private Table mainTable;

    private RClient client;

    private ScreenHandler screenHandler;

    public LobbyScreen() {}
    /**
     * Creates a lobby with buttons for each character. Also creates a start button only visible to the hosting client.
     * @param screenHandler used to change the screen.
     */
    public LobbyScreen(ScreenHandler screenHandler){
        this.screenHandler = screenHandler;

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

        //Tables
        characterTable = new Table(skin);
        mainTable = new Table(skin);

        //Stage
        stage = new Stage();

        //CharacterButtons
        char1Texture = new Texture(new FileHandle("assets/player.png"));
        char1Sprite = new Sprite(char1Texture,100,200);
        char1Draw = new SpriteDrawable(char1Sprite);
        char1Button = new ImageButton(char1Draw);
        characterTable.add(char1Button).padRight(20);

        char2Texture = new Texture(new FileHandle("assets/player.png"));
        char2Sprite = new Sprite(char2Texture,100,200);
        char2Draw = new SpriteDrawable(char2Sprite);
        char2Button = new ImageButton(char2Draw);
        characterTable.add(char2Button).padRight(20);

        char3Texture = new Texture(new FileHandle("assets/player.png"));
        char3Sprite = new Sprite(char3Texture,100,200);
        char3Draw = new SpriteDrawable(char3Sprite);
        char3Button = new ImageButton(char3Draw);
        characterTable.add(char3Button).padRight(20);

        char4Texture = new Texture(new FileHandle("assets/player.png"));
        char4Sprite = new Sprite(char4Texture,100,200);
        char4Draw = new SpriteDrawable(char4Sprite);
        char4Button = new ImageButton(char4Draw);
        characterTable.add(char4Button).padRight(20);

        /**
         * Adding listeners to the ImageButtons
         * @see CharacterSelectlistener
         */
        char1Button.addListener(new CharacterSelectListener(this, "allden"));
        char2Button.addListener(new CharacterSelectListener(this, "boking"));
        char3Button.addListener(new CharacterSelectListener(this, "vilddjur"));
        char4Button.addListener(new CharacterSelectListener(this, "schtek"));


        mainTable.setPosition(x, y);
        characterTable.setPosition(x, y);
        characterTable.add(char1Button);

        startButton = new TextButton("Start", style);
        //startButton.setDisabled(true);

        mainTable.add(characterTable);
        mainTable.add(startButton).padLeft(150).padTop(375);

        stage.addActor(mainTable);
    }

    /**
     * When a player clicks on a character the listener class (CharacterSelectListener) executes this method
     * with the parameter different depending on which character the player has clicked on.
     * @param character the name of the selected character.
     */
    public void selectCharacter(String character){
        if(character.equals("allden") && !char1Button.isDisabled()){
            System.out.println("You have chosen allden");
            char1Button.setDisabled(true);
        }else if (character.equals("boking") && !char2Button.isDisabled()){
            System.out.println("You have chosen wisely");
            char2Button.setDisabled(true);
        }else if (character.equals("vilddjur") && !char3Button.isDisabled()){
            System.out.println("You have chosen Vilddjur");
            char3Button.setDisabled(true);
        }else if (character.equals("schtek") && !char4Button.isDisabled()){
            System.out.println("You have chosen Schtek");
            char4Button.setDisabled(true);
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        //If hosting: create both a server and a client, else, just a client.
        if (nmu.isServer()) {
            new RServer(nmu.getPort());
            client = new RClient(nmu.getPort());
            client.addEventListener(screenHandler);
        } else {
            client = new RClient(nmu.getIp(), nmu.getPort());
            client.addEventListener(screenHandler);
        }
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            screenHandler.setScreen(SCREEN.MAIN);
        }
        stage.draw();

        //TODO: add function client.clientsLockedIn
        if(client != null && client.isHost() && startButton.isDisabled()){
            startButton.setDisabled(false);
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

    /**
     * Used to get the clients game view.
     * @return the game view.
     */
    public RRRGameView getGameView() {
        return client.getView();
    }
}
