package com.technohest.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import sun.java2d.windows.GDIBlitLoops;

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
