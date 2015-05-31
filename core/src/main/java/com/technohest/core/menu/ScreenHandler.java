package com.technohest.core.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.technohest.LibgdxService.GameLogicGDX;
import com.technohest.core.controller.RRRGameController;
import com.technohest.core.event.REventListener;
import com.technohest.core.model.RRRGameModel;
import com.technohest.core.view.RRRGameView;

import java.util.Observable;

import static com.technohest.core.menu.SCREEN.*;

/**
 * ScreenHandler is an easy way to set the screen of the program.
 * @author David Ström
 */
public class ScreenHandler extends Observable implements REventListener {
    // All the different possible screens
    private Screen menuScreen = new RRRMenuView();
    private Screen ipPortInputScreen = new IPPortInputScreen();
    private LobbyScreen lobbyScreen = new LobbyScreen(this);
    private Screen optionsScreen = new OptionsMenuView();
    private SCREEN currentScreen;
    private RRRGameView gameScreen;

    // The singleton
    private static ScreenHandler instance = null;

    protected ScreenHandler() {
        RRRGameModel model = new RRRGameModel(new GameLogicGDX());
        gameScreen = new RRRGameView(
                        new RRRGameController(model),
                        model);
    }

    /**
     * @return
     * the singleton instance.
     */
    public static ScreenHandler getInstance() {
        if (instance == null) {
            instance = new ScreenHandler();
        }

        return instance;
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

    /**
     * @return
     * the current screen being displayed.
     */
    public SCREEN getCurrentScreen() {
        return currentScreen;
    }

    @Override
    public void fireEvent() {
        gameScreen = lobbyScreen.getGameView();
        setChanged();
        notifyObservers(gameScreen);
    }
}
