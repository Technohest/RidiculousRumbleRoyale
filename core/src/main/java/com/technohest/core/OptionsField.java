package com.technohest.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.StringBuilder;
/**
 * Created by Oscar on 2015-04-24.
 */
public class OptionsField extends Table{
    private TextButton backButton;
    private TextButton forwardButton;
    private Label currentOptionLabel;
    private String[] optionsList;
    private int currentIndex;
    private TextButtonStyle style;
    private Skin skin;
    private TextureAtlas buttonAtlas;
    private BitmapFont font;
    private Label.LabelStyle labelStyle;
    private BitmapFont font2;
    private float x;
    private float y;
    private int numberOfOptions;
    private Label textLabel;
    private String[] parts;
    private FileHandle optionsFile;
    private String text;

    public OptionsField(String text,String[] optionsList) {
        this.text = text;
        this.optionsList = optionsList;
        this.numberOfOptions = optionsList.length;
        skin = new Skin();
        buttonAtlas = new TextureAtlas("assets/optionArrows.pack");
        skin.addRegions(buttonAtlas);
        font = new BitmapFont();
        font.scale(1f);
        font2 = new BitmapFont(false);
        font2.scale(0.7f);
        style = new TextButtonStyle();
        style.up = skin.getDrawable("menuButton");
        style.down = skin.getDrawable("pressedMenuButton");
        style.font = font;

        optionsFile = Gdx.files.local("assets/options.txt");
        parts = optionsFile.readString().split(":");
        optionsFile = Gdx.files.local("assets/options.txt");
        labelStyle = new Label.LabelStyle(font2,Color.WHITE);
        textLabel = new Label(text,labelStyle);

        backButton = new TextButton("<", style);
        forwardButton = new TextButton(">", style);

        backButton.addListener(new OptionsFieldListener(this, "back"));
        forwardButton.addListener(new OptionsFieldListener(this, "forward"));
        currentOptionLabel = new Label(getLabelText(), labelStyle);

        this.add(textLabel);
        this.add(textLabel);

        this.add(backButton);
        this.add(currentOptionLabel);
        this.add(forwardButton);
    }

    public void switchTo(String target){
        if(target == "back"){
            currentIndex -=1;
            if(currentIndex == -1){
                currentIndex =numberOfOptions-1;
            }

        }else {
            currentIndex +=1;
            if( numberOfOptions==currentIndex){
                currentIndex=0;
            }
        }
        currentOptionLabel.setText(optionsList[currentIndex]);
        update();
    }
    public void update(){
        this.clear();
        this.add(textLabel);
        this.add(backButton);
        this.add(currentOptionLabel);
        this.add(forwardButton);
    }
    public String getCurrent(){
        return optionsList[currentIndex];
    }

    private String getLabelText(){
        String firstText = "";
        if(text == "Display Mode:"){
            firstText = parts[1];
        }else if(text == "Resolution:"){
            firstText = parts[3];
        }else if(text == "Music:"){
            firstText = parts[5];
        }else if(text == "Sound Effects:"){
            firstText = parts[7];
        }else if(text == "Display Damage:"){
            firstText = parts[9];
        }
        return firstText;
    }
}
