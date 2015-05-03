package com.technohest.core.model;

import com.technohest.core.interfaces.IGameLogic;
import com.technohest.core.interfaces.ILevel;
import com.technohest.core.handlers.LevelHandler;

/**
 * Created by Oskar on 2015-03-24.
 */
public class RRRGameModel {
    private LevelHandler    levelHandler;
<<<<<<< HEAD
    private World           world;
    private Character player;


    public RRRGameModel(){
        this.levelHandler = new LevelHandler();
        world = new World(new Vector2(0, Constants.GRAVITY), true);
        this.player = new Character(world,new Vector2(165/PPM,80/PPM),20f,30f,50);
=======
    private IGameLogic      gameLogic;

    public RRRGameModel(){
        this.levelHandler = new LevelHandler();
        setGameLogic(new GameLogicGDX());
>>>>>>> 17b847dbb3dc7f0bdefd384604f496ea3cc6106f
    }
    public ILevel getLevel() {
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
    public Character getPlayer() {
        return this.player;
    }

    public void step(float v) {
        gameLogic.update(v);
    }
}
