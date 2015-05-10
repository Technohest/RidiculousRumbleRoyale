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
import com.technohest.core.menu.ScreenHandler;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Text;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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
    private OptionsField width;
    private OptionsField height;
    private OptionsField displayMode;
    private OptionsField soundEffects;
    private OptionsField music;
    private OptionsField damageDone;

    //Filehandler
    private File file;
    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document doc;
    private NodeList list;
    private NodeList list1;
    private NodeList list2;
    private org.w3c.dom.Element element1;
    private Node node;
    private org.w3c.dom.Element element2;

    //HashMaps
    private BidiMap<Integer, String> musicMap;
    private BidiMap<Integer, String> soundMap;
    private BidiMap<Integer, String> displayDamageMap;
    private BidiMap<Integer, String> displayModeMap;
    private BidiMap<Integer, String> widthMap;
    private BidiMap<Integer, String> heightMap;
    public OptionsMenuView(){

        //Game and stage
        stage = new Stage();
        navigationTable = new Table();
        mainTable = new Table();

        //FileHandle


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

        //File initialization
        try {
            file = new File("assets/config.xml");
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            list = doc.getElementsByTagName("options");
            element2 = (Element) list.item(0);
            node = list.item(0);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        element1 = (Element) node;

        //Settings
        setHashMaps();
        width = new OptionsField("Width:", widthMap);
        height = new OptionsField("Height:", heightMap);
        displayMode = new OptionsField("Display Mode:", displayModeMap);
        soundEffects = new OptionsField("Sound Effects:", soundMap);
        music = new OptionsField("Music:", musicMap);
        damageDone = new OptionsField("Display Damage:", displayDamageMap);

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
        backButton.addListener(new MenuInputListener("backFromOptions"));
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
        graphicsTable.add(width);
        graphicsTable.row();
        graphicsTable.add(height);

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
        //Does not work for some reason
        list1 = element2.getElementsByTagName("displaymode");
        element1 = (Element) list1.item(0);
        list2 = element1.getChildNodes();
        list2.item(0).setNodeValue(displayMode.getCurrent());
    }

    public void setHashMaps(){
        //Music
        musicMap = new DualHashBidiMap<Integer, String>();
        musicMap.put(1,"enabled");
        musicMap.put(2,"disabled");

        //Resolution
        widthMap= new DualHashBidiMap<Integer, String>();
        widthMap.put(1, "960");
        widthMap.put(2, "1280");

        heightMap = new DualHashBidiMap<Integer, String>();
        heightMap.put(1,"720");
        heightMap.put(2, "1080");

        //Sound
        soundMap = new DualHashBidiMap<Integer, String>();
        soundMap.put(1,"enabled");
        soundMap.put(2,"disabled");

        //DisplayMode
        displayModeMap = new DualHashBidiMap<Integer, String>();
        displayModeMap.put(1, "windowed");
        displayModeMap.put(2, "fullscreen");

        //DisplayDamageDone
        displayDamageMap = new DualHashBidiMap<Integer, String>();
        displayDamageMap.put(1,"enabled");
        displayDamageMap.put(2,"disabled");
    }
}
