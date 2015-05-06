package com.technohest.core.model;

import com.technohest.LibgdxService.GameLogicGDX;
import com.technohest.LibgdxService.IGameLogic;
import com.technohest.LibgdxService.ILevel;
import com.technohest.core.handlers.LevelHandler;

import java.util.ArrayList;

/**
 * Created by Oskar on 2015-03-24.
 */
public class RRRGameModel {
    private LevelHandler    levelHandler;
    private ArrayList<Character> players = new ArrayList<Character>();


    private IGameLogic      gameLogic;

    public RRRGameModel(){
        this.levelHandler = new LevelHandler();
        //Temp code for player creation
        for (int i = 0;i<4;i++) {
            players.add(new Character(Integer.toString(i),new Projectile("attack",100,10f,10f),new Projectile("attack",100,10f,10f)));
        }
        setGameLogic(new GameLogicGDX());
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
        gameLogic.generate(levelHandler.getLevel(),players);
    }
    public ArrayList<Character> getPlayers() {
        return this.players;
    }
    public Character getPlayer(String name) {
        for (Character c: players) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    public IGameLogic getGameLogic() {
        return gameLogic;
    }

    public void step(float v) {
        gameLogic.update(v);
    }
}
