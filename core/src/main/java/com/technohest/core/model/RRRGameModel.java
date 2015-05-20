package com.technohest.core.model;

import com.technohest.LibgdxService.GameLogicGDX;
import com.technohest.LibgdxService.IGameLogic;
import com.technohest.LibgdxService.ILevel;
import com.technohest.core.handlers.LevelHandler;
import com.technohest.core.network.IState;

import java.util.Collection;
import java.util.HashMap;

/**
 * The main model for Ridiculous Rumble Royale.
 * @author Oskar Jedvert
 * @author Tobias Alldén
 */
public class RRRGameModel {
    private Integer myID;
    private LevelHandler levelHandler;
    private HashMap<Integer,Character> idCharacterMap;
    private IGameLogic gameLogic;

    public RRRGameModel(){
        this.levelHandler = new LevelHandler();
        setGameLogic(new GameLogicGDX());
        this.idCharacterMap = new HashMap<Integer, Character>();
        //Temp character for testing
        /*idCharacterMap.put(1,new Character("Allden",new Projectile("FireBall", 100, 10,10),new Projectile("FireBall", 100, 10,10)));
        idCharacterMap.put(2,new Character("Allden2",new Projectile("FireBall", 100, 10,10),new Projectile("FireBall", 100, 10,10)));
        myID =1;*/
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
     * @param idChararcerMap the new idCharacterMap
     */
    public void init(HashMap<Integer,Integer> idChararcerMap) {
        //The characters will be created at the game start and since the network only knows the type
        //I needed to change input to be <Integer, Integer>. It will be changed to CharType in the future.
        this.idCharacterMap = new HashMap<Integer, Character>();
        for (Integer i: idChararcerMap.keySet()) {
            //Create new character for every id. Make them all the same type "Allden".
            this.idCharacterMap.put(i,new Character(i, "Allden " + idChararcerMap.get(i),new Projectile("FireBall", 100, 10,10),new Projectile("FireBall", 100, 10,10)));
        }

        //this.idCharacterMap = idChararcerList;
    }
    public Character getPlayer(String name) {
        for(Integer i: idCharacterMap.keySet()) {
            if(idCharacterMap.get(i).getName().equals(name)) {
                return idCharacterMap.get(i);
            }
        }
        return null;
    }

    public Character getPlayerFromID(Integer myID) {
        return this.idCharacterMap.get(myID);
    }

    public IGameLogic getGameLogic() {
        return gameLogic;
    }

    public void step(float v) {
        gameLogic.update(v);
    }
    public Integer getmyID() {
        return myID;
    }
    public void setMyID(Integer id) {
        myID = id;
    }


    /**
     * Performes an action on the specified player connected to playerId
     * @param action
     */
    public void performAction(Action action) {
        switch(action.getActionID()) {
            case JUMP:
                gameLogic.jump(getPlayerFromID(action.getPlayerID()));
                break;
            case MOVE_RIGHT:
                gameLogic.moveRight(getPlayerFromID(action.getPlayerID()));
                break;
            case MOVE_LEFT:
                gameLogic.moveLeft(getPlayerFromID(action.getPlayerID()));
        }

    }

    public Collection<Character> getPlayers() {
        return this.idCharacterMap.values();
    }


    public void correct(IState state) {
        gameLogic.correct(state);
    }

    public void setIsClient() {
        gameLogic.setIsClient();
    }

    public void setIsServer() {
        gameLogic.setIsServer();
    }
}
