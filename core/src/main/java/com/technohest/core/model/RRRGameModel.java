package com.technohest.core.model;

import com.technohest.core.interfaces.IGameLogic;
import com.technohest.core.interfaces.Level;
import com.technohest.core.handlers.LevelHandler;

/**
 * Created by Oskar on 2015-03-24.
 */
public class RRRGameModel {
    private LevelHandler    levelHandler;
    private IGameLogic      gameLogic;

    public RRRGameModel(){
        this.levelHandler = new LevelHandler();
        setGameLogic(new GameLogicGDX());
    }
    public Level getLevel() {
        return levelHandler.getLevel();
    }
    public void setGameLogic(IGameLogic gl){
        gameLogic = gl;
    }
    /**
     * Initializes all tiles with their corresponding box2d bodies
     */
    public void generateWorld() {
        gameLogic.generate(levelHandler.getLevel());
    }

    public void step(float v) {
        gameLogic.update(v);
    }
}
