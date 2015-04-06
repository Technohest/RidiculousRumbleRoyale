package com.technohest.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * Created by oskar on 2015-04-03.
 */
public class RRRMain extends Game {
    /**
     * These are the two screen we will be switching between
     */
    private RRRMenuView menuScreen;
    private RRRGameView gameScreen;

    @Override
    public void create() {
        menuScreen = new RRRMenuView(this);

        /**
         * we initialize the MVC here
         */
        RRRGameModel model = new RRRGameModel();
        RRRGameController controller = new RRRGameController(model);
        this.gameScreen = new RRRGameView(controller, this);

        setScreen(menuScreen);
    }

    /**
     * The target param is a string which will be called through, switchTo("game")
     * @param target
     * The target state
     */
    public void switchTo(String target){
        if(target.equals("game")){
            setScreen(gameScreen);
        }else if(target.equals("menu")){
            setScreen(menuScreen);
        }else if(target.equals("exit")){
            Gdx.app.exit();
        }
    }
}
