package com.technohest.core;

import com.badlogic.gdx.Game;

/**
 * Created by oskar on 2015-04-03.
 */
public class RRRMain extends Game {
    /**
     * These are the two screen we will be switching between
     */
    private RRRGUI menuScreen;
    private RRRView gameScreen;
    @Override
    public void create() {
        menuScreen = new RRRGUI(this);
        /**
         * we initialize the MVC here
         */
        RRRModel model = new RRRModel();
        RRRController controller = new RRRController(model);
        this.gameScreen = new RRRView(controller, this);
        setScreen(menuScreen);
    }

    /**
     * The screen param is a string which will be called through, switchTo("game")
     * @param screen
     */
    public void switchTo(String screen){
        if(screen.equals("game")){
            setScreen(gameScreen);
        }else if(screen.equals("menu")){
            setScreen(menuScreen);
        }
    }
}
