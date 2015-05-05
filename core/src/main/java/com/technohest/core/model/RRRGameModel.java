package com.technohest.core.model;

import com.technohest.core.interfaces.IGameLogic;
import com.technohest.core.interfaces.ILevel;
import com.technohest.core.handlers.LevelHandler;

/**
 * Created by Oskar on 2015-03-24.
 */
public class RRRGameModel {
    private LevelHandler    levelHandler;
    private Character[] players;


    private IGameLogic      gameLogic;

    public RRRGameModel(){
        this.levelHandler = new LevelHandler();
        //Temp code for player creation
        this.players = new Character[4];
        for (int i = 0;i<4;i++) {
            players[i] = new Character(Integer.toString(i),new Projectile("attack",100,10f,10f),new Projectile("attack",100,10f,10f));
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
    public Character[] getPlayers() {
        return this.players;
    }
    public Character getPlayer(String name) {
        for(int i = 0; i<players.length;i++) {
            if(players[i].getName().equals(name)) {
                return players[i];
            }
        }
        return null;
    }

    public void step(float v) {
        gameLogic.update(v);
    }
}
