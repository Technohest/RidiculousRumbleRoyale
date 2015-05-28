package com.technohest.core.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.technohest.core.controller.RRRGameController;
import com.technohest.core.menuState.SCREEN;
import com.technohest.core.model.RRRGameModel;
import com.technohest.core.network.NetworkMenuUtility;
import com.technohest.core.view.RRRGameView;

import java.util.Observable;

import static com.technohest.core.menuState.SCREEN.*;

/**
 * ScreenHandler is an easy way to set the screen of the program.
 * @author David Ström
 */
public class ScreenHandler extends Observable {
    // All the different possible screens
    private Screen menuScreen = new RRRMenuView();
    private Screen ipPortInputScreen = new IPPortInputScreen();
    private Screen lobbyScreen = new LobbyScreen();
    private Screen optionsScreen = new OptionsMenuView();
    private SCREEN currentScreen;
    private RRRGameView gameScreen;

    // The singleton
    private static ScreenHandler instance = null;

    //-------------THE SCREENHANDLER----------------\\
    protected ScreenHandler() {
        RRRGameModel model = new RRRGameModel();
        gameScreen = new RRRGameView(
                        new RRRGameController(model),
                        model);
    }

    public static ScreenHandler getInstance() {
        if (instance == null) {
            instance = new ScreenHandler();
        }

        return instance;
    }

    /**
     * Set the screen of the game.
     */
    public void setGameScreen(RRRGameView view) {
        this.gameScreen = view;
    }

    /**
     * Sets the screen to be displayed.
     * @param target The desired screen.
     */
    public void setScreen(SCREEN target) {
        switch (target) {
            case GAME:
                currentScreen = GAME;
                setChanged();
                notifyObservers(gameScreen);
                break;
            case MAIN:
                NetworkMenuUtility.getInstance().reset();
                currentScreen = MAIN;
                setChanged();
                notifyObservers(menuScreen);
                break;
            case JOIN:
                if (currentScreen == MAIN)
                    NetworkMenuUtility.getInstance().setIsServer(false);
                currentScreen = JOIN;
                setChanged();
                notifyObservers(ipPortInputScreen);
                break;
            case OPTIONS:
                currentScreen = OPTIONS;
                setChanged();
                notifyObservers(optionsScreen);
                break;
            case HOST:
                if (currentScreen == MAIN)
                    NetworkMenuUtility.getInstance().setIsServer(true);
                currentScreen = HOST;
                setChanged();
                notifyObservers(ipPortInputScreen);
                break;
            case LOBBY:
                currentScreen = LOBBY;
                setChanged();
                notifyObservers(lobbyScreen);
                break;
            case EXIT:
                Gdx.app.exit();
                break;
        }
    }

    public SCREEN getCurrentScreen() {
        return currentScreen;
    }
}
