package com.technohest.core.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.technohest.Tools.Debugg;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by oskar on 2015-04-03.
 */
public class RRRMain extends Game implements Observer {

    @Override
    public void create() {
        // Start listening to ScreenHandler and set the current screen to menu
        ScreenHandler.getInstance().addObserver(this);
        ScreenHandler.getInstance().setScreen(SCREEN.MAIN);

        Debugg.debugging = false;
    }


    /**
     * Listens for calls to change screen
     * @param o The Observable stating a change
     * @param arg Argument passed from the Observable
     */
    public void update(Observable o, Object arg) {
        if (arg instanceof Screen) {
            setScreen((Screen) arg);
        }
    }
}
