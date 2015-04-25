package com.technohest.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
    private TextButton graphicsButton;
    private TextButton soundButton;
    private TextButton gameplayButton;
    private TextButton backButton;
    private TextButtonStyle style;
    private float x,y;
    private Label.LabelStyle labelStyle;
    private BitmapFont font2;
    private Label label2;

    //new variables
    private Table mainTable;
    private Table navigationTable;
    private Table graphicsTable;
    private Table soundTable;
    private Table gameplayTable;
    private Table currentTable;

    //Settings
    private OptionsField resolution;
    private OptionsField vilddjur;

    public OptionsMenuView(RRRMain game){
        this.game = game;
        stage = new Stage();
        font = new BitmapFont();
        font.scale(1.3f);

        //ButtonSkin
        buttonAtlas = new TextureAtlas("assets/menuButtons.pack");
        skin = new Skin();
        skin.addRegions(buttonAtlas);
        labelSkin = new Skin();
        labelSkin.add("default", new BitmapFont());

        style = new TextButtonStyle();
        style.up = skin.getDrawable("menuButton");
        style.down = skin.getDrawable("pressedMenuButton");
        style.font=font;
        x = (Gdx.graphics.getWidth())/2.0f;
        y = (Gdx.graphics.getHeight())/2.0f;

        resolution = new OptionsField("Resolution: ",new String[]{"1920x1080", "1337x1337"});
        vilddjur = new OptionsField("Fuck Vilddjur?: ",new String[]{"ja", "nej"});

        //Table
        createGraphicsTable();
        createSoundTable();
        createGameplayTable();

        labelStyle = new Label.LabelStyle(font,Color.WHITE);

        navigationTable = new Table();
        mainTable = new Table();
        currentTable = graphicsTable;

        graphicsButton = new TextButton("Graphics", style);
        soundButton = new TextButton("Sound", style);
        gameplayButton = new TextButton("Gameplay", style);
        backButton = new TextButton("Back", style);

        graphicsButton.addListener(new OptionsInputListener(this, graphicsTable, "graphics"));
        soundButton.addListener(new OptionsInputListener(this, soundTable, "sound"));
        gameplayButton.addListener(new OptionsInputListener(this, gameplayTable, "gameplay"));
        backButton.addListener(new MenuInputListener(this.game, "backFromOptions"));

        mainTable.setPosition(x, y);
        navigationTable.add(graphicsButton);
        navigationTable.add(soundButton);
        navigationTable.add(gameplayButton);
        navigationTable.add(backButton);

        updateStage();

        stage.addActor(mainTable);
    }
    public void createGraphicsTable(){
        graphicsTable = new Table();

        graphicsTable.add(resolution);
        graphicsTable.row();
        graphicsTable.add(vilddjur);

    }
    public void createSoundTable(){
        soundTable = new Table();

        //Design the table here
        soundTable.add(new TextButton("inside sound", style));
    }
    public void createGameplayTable(){
        gameplayTable = new Table();

        //Design the table here
        gameplayTable.add(new TextButton("inside gameplay",style));

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

    public void switchTo(String target){
        if(target=="graphics"){
            currentTable = graphicsTable;
        }else if (target=="sound"){
            currentTable = soundTable;
        }else if (target=="gameplay"){
            currentTable = gameplayTable;
        }
        updateStage();
    }
    private void updateStage(){
        mainTable.clear();
        mainTable.add(navigationTable);
        mainTable.row();
        mainTable.add(currentTable);
    }
}
