package com.technohest.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import java.util.Observable;

/**
 * ScreenHandler is an easy way to set the screen of the program.
 * Created by time on 2015-04-13.
 */
public class ScreenHandler extends Observable {
    // All the different possible screens
    private Screen menuScreen = new RRRMenuView();
    private Screen gameScreen = new RRRGameView(new RRRGameController(new RRRGameModel()));

    // The singleton
    private static ScreenHandler instance = null;


    //-------------THE SCREENHANDLER----------------\\
    protected ScreenHandler() {}

    public static ScreenHandler getInstance() {
        if (instance == null) {
            instance = new ScreenHandler();
        }

        return instance;
    }

    /**
     * Sets the current displaying screen.
     * @param target The type of screen to be set as.
     */
    public void setScreen(final String target) {
        if(target.equals("game")){
            setChanged();
            notifyObservers(gameScreen);
        }else if(target.equals("menu")){
            setChanged();
            notifyObservers(menuScreen);
        }else if(target.equals("exit")){
            Gdx.app.exit();
        }
    }
}
