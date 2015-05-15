package com.technohest.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.technohest.core.controller.MenuInputListener;
import com.technohest.core.menu.SCREEN;
import com.technohest.core.menu.ScreenHandler;

import javax.xml.soap.Text;

/**
 * The options menu, able to change the options of the game and save them.
 * Created by Oscar on 2015-04-11.
 */
public class OptionsMenuView implements Screen{

    //Screen
    private Stage stage;
    private float x,y;

    //Text
    private TextureAtlas buttonAtlas2;
    private Skin smallSkin;
    private TextButtonStyle smallStyle;
    private TextButtonStyle style;
    private Skin skin;
    private TextureAtlas buttonAtlas;
    private BitmapFont font;

    //Buttons
    private TextButton graphicsButton;
    private TextButton soundButton;
    private TextButton gameplayButton;
    private TextButton backButton;
    private TextButton saveButton;

    //Tables
    private Table mainTable;
    private Table navigationTable;
    private Table graphicsTable;
    private Table soundTable;
    private Table gameplayTable;
    private Table currentTable;

    //Settings
    private OptionsField resolution;
    private OptionsField displayMode;
    private OptionsField soundEffects;
    private OptionsField music;
    private OptionsField damageDone;

    //Filehandler
    private FileHandle optionsFile;
    private String[] parts;

    public OptionsMenuView(){

        //Game and stage
        stage = new Stage();
        navigationTable = new Table();
        mainTable = new Table();

        //FileHandle
        optionsFile = Gdx.files.local("assets/options.txt");

        //Fontsize
        font = new BitmapFont();
        font.scale(1.3f);

        //ButtonStyle for big buttons
        buttonAtlas = new TextureAtlas("assets/menuButtons.pack");
        skin = new Skin();
        skin.addRegions(buttonAtlas);
        style = new TextButtonStyle();
        style.up = skin.getDrawable("menuButton");
        style.down = skin.getDrawable("pressedMenuButton");
        style.font=font;

        //ButtonStyle for smaller buttons
        buttonAtlas2 = new TextureAtlas("assets/optionArrows.pack");
        smallSkin = new Skin();
        smallSkin.addRegions(buttonAtlas2);
        smallStyle = new TextButtonStyle();
        smallStyle.up =smallSkin.getDrawable("menuButton");
        smallStyle.down = smallSkin.getDrawable("pressedMenuButton");
        smallStyle.font=font;

        //Screen x,y
        x = (Gdx.graphics.getWidth())/2.0f;
        y = (Gdx.graphics.getHeight())/2.0f;

        //Settings
        resolution = new OptionsField("Resolution:",new String[]{"960x720", "1024x768","1280x720", "1152x864", "1280x960", "1280x1024", "1440x1050","1600x900", "1600x1200", "1920x1080"});
        displayMode = new OptionsField("Display Mode:", new String[]{"fullscreen", "windowed fullscreen", "windowed"});
        soundEffects = new OptionsField("Sound Effects:", new String[]{"enabled", "disabled"});
        music = new OptionsField("Music:", new String[]{"enabled", "disabled"});
        damageDone = new OptionsField("Display Damage:", new String[]{"enabled", "disabled"});

        //Tables
        createGraphicsTable();
        createSoundTable();
        createGameplayTable();

        currentTable = graphicsTable;

        //Initialize buttons in navigationview
        graphicsButton = new TextButton("Graphics", style);
        soundButton = new TextButton("Sound", style);
        gameplayButton = new TextButton("Gameplay", style);
        backButton = new TextButton("Back", smallStyle);
        saveButton = new TextButton("Save", smallStyle);

        //Adding listeners to navigationsview buttons.
        graphicsButton.addListener(new OptionsInputListener(this, "graphics"));
        soundButton.addListener(new OptionsInputListener(this, "sound"));
        gameplayButton.addListener(new OptionsInputListener(this, "gameplay"));
        backButton.addListener(new MenuInputListener(SCREEN.MAIN));
        saveButton.addListener(new OptionsInputListener(this, "save"));

        //Setting the stage
        mainTable.setPosition(x, y+(y/2f));
        navigationTable.add(graphicsButton);
        navigationTable.add(soundButton);
        navigationTable.add(gameplayButton);
        navigationTable.add(backButton);

        updateStage();

        stage.addActor(mainTable);
    }
    public void createGraphicsTable(){
        graphicsTable = new Table();

        //Design the table here
        graphicsTable.add(displayMode);
        graphicsTable.row();
        graphicsTable.add(resolution);
        graphicsTable.row();

    }
    public void createSoundTable(){
        soundTable = new Table();

        //Design the table here
        soundTable.add(music);
        soundTable.row();
        soundTable.add(soundEffects);
        soundTable.row();
    }
    public void createGameplayTable(){
        gameplayTable = new Table();

        //Design the table here
        gameplayTable.add(damageDone);
        gameplayTable.row();
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
        buttonAtlas.dispose();
        skin.dispose();
        font.dispose();
        stage.dispose();
    }

    public void switchTo(String target){
        if(target.equals("graphics")) {
            currentTable = graphicsTable;
        }else if (target.equals("sound")){
            currentTable = soundTable;
        }else if (target.equals("gameplay")){
            currentTable = gameplayTable;
        }
        updateStage();
    }
    private void updateStage(){
        mainTable.clear();
        mainTable.add(navigationTable);
        mainTable.row();
        mainTable.add(currentTable);
        mainTable.row();
        mainTable.add(backButton);
        mainTable.add(saveButton);
    }

    public void saveOptions(){
        //Write to optionsfile
        //[1] = Displaymode
        //[3] = Resolution
        //[5] = Music
        //[7] = Soundeffects
        //[9] = Display Damage

        parts = optionsFile.readString().split(":");
        optionsFile.writeString("displayMode:" + displayMode.getCurrent() + ":resolution:" + resolution.getCurrent() + ":music:" + music.getCurrent() + ":soundEffects:" + soundEffects.getCurrent() + ":displayDamage:" + damageDone.getCurrent() ,false);
    }
}
