package com.technohest.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.technohest.constants.Constants;
import org.apache.commons.collections4.BidiMap;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;

/**
 * Created by Oscar on 2015-04-24.
 */
public class OptionsField extends Table{
    private TextButton backButton;
    private TextButton forwardButton;
    private Label currentOptionLabel;
    private int currentIndex;
    private TextButtonStyle style;
    private Skin skin;
    private TextureAtlas buttonAtlas;
    private BitmapFont font;
    private Label.LabelStyle labelStyle;
    private BitmapFont font2;
    private int numberOfOptions;
    private Label textLabel;
    private String text;
    private BidiMap<Integer, String> map;

    //FileReader
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


    public OptionsField(String text, BidiMap<Integer,String> map) {
        this.text = text;
        this.map = map;
        this.numberOfOptions = map.size();
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
        currentOptionLabel.setText(map.get(currentIndex+1));
        update();
    }
    public void update(){
        this.clear();
        this.add(textLabel);
        this.add(backButton);
        this.add(currentOptionLabel);
        this.add(forwardButton);
        System.out.println(this.getCurrent());
    }
    public String getCurrent(){
        return map.get(currentIndex+1);
    }

    //Gets the value that is currently in the file
    private String getLabelText(){
        String firstText = "";
        if(text == "Display Mode:"){
            list1 = element2.getElementsByTagName("displaymode");
        }else if(text == "Width:"){
            list1 = element2.getElementsByTagName("width");

        }else if(text == "Height:"){
            list1 = element2.getElementsByTagName("height");

        }else if(text == "Music:"){
            list1 = element2.getElementsByTagName("music");

        }else if(text == "Sound Effects:"){
            list1 = element2.getElementsByTagName("sound");

        }else if(text == "Display Damage:"){
            list1 = element2.getElementsByTagName("sound");
        }
        element1 = (Element) list1.item(0);
        list2 = element1.getChildNodes();
        firstText = list2.item(0).getNodeValue();
        currentIndex = map.getKey(firstText) -1;
        return firstText;
    }
}
