package com.technohest.core.model;

import com.technohest.LibgdxService.GameLogicGDX;
import com.technohest.LibgdxService.IGameLogic;
import com.technohest.LibgdxService.ILevel;
import com.technohest.core.handlers.LevelHandler;
import java.util.HashMap;

/**
 * The main model for Ridiculous Rumble Royale.
 * @author Oskar Jedvert
 * @author Tobias Alldén
 */
public class RRRGameModel {
    private LevelHandler levelHandler;
    private HashMap<Integer,Character> idCharacterMap;
    private IGameLogic gameLogic;

    public RRRGameModel(){
        this.levelHandler = new LevelHandler();
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
        gameLogic.generate(levelHandler.getLevel(),idCharacterMap);
    }

    /**
     * Initializes the model with a id/player List
     * @param idChararcerList
     */
    public void init(HashMap<Integer,Character> idChararcerList) {
        this.idCharacterMap = idChararcerList;
    }
    public Character getPlayer(String name) {
        for(Integer i: idCharacterMap.keySet()) {
            if(idCharacterMap.get(i).getName() == name) {
                return idCharacterMap.get(i);
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

    /**
     * Moves the player(Body with fixtures) left in the and plays the run animation.
     */
    public void moveLeft(){
        
    }

    /**
     * Moves the player(Body with fixtures) left in the game,and plays the run animation.
     */
    public void moveRight(){

    }

    /**
     * Makes the player(Body with fixtures) jump in the game, and plays the jump animation.
     */
    public void jump() {

    }

    /**
     * Makes the character perform a base attack
     */
    public void attack_base() {

    }

    /**
     * Makes the character perform a special attack
     */
    public void attack_special() {

    }



}
