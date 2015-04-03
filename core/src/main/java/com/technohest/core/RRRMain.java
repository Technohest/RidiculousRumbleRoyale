package com.technohest.core;

import com.badlogic.gdx.Game;

/**
 * Created by oskar on 2015-04-03.
 */
public class RRRMain extends Game {
    private RRRGUI menuScreen;
    private RRRView gameScreen;
    @Override
    public void create() {
        menuScreen = new RRRGUI(this);
        RRRModel model = new RRRModel();
        RRRController controller = new RRRController(model);
        this.gameScreen = new RRRView(controller, this);
        setScreen(menuScreen);
    }
    public void switchTo(String screen){
        if(screen.equals("game")){
            setScreen(gameScreen);
        }else if(screen.equals("menu")){
            setScreen(menuScreen);
        }
    }
}
