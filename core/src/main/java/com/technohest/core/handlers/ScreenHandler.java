package com.technohest.core.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.technohest.core.controller.RRRGameController;
import com.technohest.core.model.RRRGameModel;
import com.technohest.core.network.IPPortInputScreen;
import com.technohest.core.network.NetworkMenuUtility;
import com.technohest.core.view.RRRGameView;
import com.technohest.core.view.RRRMenuView;

import java.util.Observable;

/**
 * ScreenHandler is an easy way to set the screen of the program.
 * Created by time on 2015-04-13.
 */
public class ScreenHandler extends Observable {
    // All the different possible screens
    private Screen menuScreen = new RRRMenuView();
    private Screen gameScreen = new RRRGameView(new RRRGameController(new RRRGameModel()));
    private Screen ipPortInputScreen = new IPPortInputScreen();
    private Screen lobbyScreen = new LobbyScreen();

    private SCREEN currentScreen;

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
        if (target.equals("game")) {
            currentScreen = SCREEN.GAME;
            setChanged();
            notifyObservers(gameScreen);
        } else if(target.equals("menu")) {
            NetworkMenuUtility.getInstance().reset();
            currentScreen = SCREEN.MAIN;
            setChanged();
            notifyObservers(menuScreen);
        } else if(target.equals("join")) {
            if (currentScreen == SCREEN.MAIN) {
                NetworkMenuUtility.getInstance().setIsServer(false);
            }
            currentScreen = SCREEN.JOIN;
            setChanged();
            notifyObservers(ipPortInputScreen);
        } else if(target.equals("host")) {
            if (currentScreen == SCREEN.MAIN) {
                NetworkMenuUtility.getInstance().setIsServer(true);
            }
            currentScreen = SCREEN.HOST;
            setChanged();
            notifyObservers(ipPortInputScreen);
        } else if(target.equals("lobby")) {
            currentScreen = SCREEN.LOBBY;
            setChanged();
            notifyObservers(lobbyScreen);
        } else if(target.equals("exit")) {
            Gdx.app.exit();
        }
    }

    public SCREEN getCurrentScreen() {
        return currentScreen;
    }
}
