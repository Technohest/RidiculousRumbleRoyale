package com.technohest.core.model;

import com.technohest.LibgdxService.GameLogicGDX;
import com.technohest.LibgdxService.IGameLogic;
import com.technohest.LibgdxService.ILevel;
import com.technohest.core.handlers.LevelHandler;
import com.technohest.core.network.IState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * The main model for Ridiculous Rumble Royale.
 * @author Oskar Jedvert
 * @author Tobias Alldén
 */
public class RRRGameModel {
    private Integer myID;
    private LevelHandler levelHandler;
    private HashMap<Integer,Character> idCharacterMap;
    private ArrayList<Attack> activeAttacks;
    private IGameLogic gameLogic;
    private boolean respawnEnabled;

    //TMP
    private Boolean isClient;

    public RRRGameModel(){
        this.levelHandler = new LevelHandler();
        setGameLogic(new GameLogicGDX(this));
        this.idCharacterMap = new HashMap<Integer, Character>();
        activeAttacks = new ArrayList<Attack>();
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
            //System.out.println("THE PLAYER ID " + i);
            this.idCharacterMap.put(i,new Character(i, "Allden " + idChararcerMap.get(i)));
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
        updatePlayers(v);
        updateAttacks(v);
    }

    public void setMyID(int id) {
        this.myID = id;
    }

    public Integer getMyID() {
        return myID;
    }


    /**
     * Updates all the players.
     */
    public void updatePlayers(float v) {
        for (Character c : idCharacterMap.values()) {
            if (c.isAlive()) {
                if (c.getHealthPoints() < 0) {
                    c.die();
                    gameLogic.killPlayer(c);
                    c.setShouldRespawn(this.respawnEnabled);
                } else {
                    gameLogic.updatePlayer(c);
                }
            } else {
                c.addRespawnTimer(v);
                if (c.respawnTimeDone()) {
                    c.respawn();
                    gameLogic.respawnPlayer(c);
                }
            }
        }
    }

    /**
     * Updates all enabled attacks.
     * @param v
     */
    public void updateAttacks(float v) {
        ArrayList<Attack> attackstoberemoved = new ArrayList<Attack>();
        for (Attack a:activeAttacks) {
            if (a.getHasInpacted()) {
                a.reset();
                gameLogic.destroyAttack(a);
                attackstoberemoved.add(a);
            } else if (!a.isReady()) {
                if (a.timeLeft()) {
                    a.incrementTime(v);
                } else {
                    a.reset();
                    gameLogic.destroyAttack(a);
                    attackstoberemoved.add(a);
                }
            }
        }
        this.activeAttacks.remove(attackstoberemoved);
    }
    /**
     * Performes an action on the specified player connected to playerId
     * @param action
     */
    public void performAction(Action action) {
        Character player = getPlayerFromID(action.getPlayerID());
        switch(action.getActionID()) {
            case JUMP:
                gameLogic.jump(player);
                break;
            case MOVE_RIGHT:
                gameLogic.moveRight(player);
                break;
            case MOVE_LEFT:
                gameLogic.moveLeft(player);
                break;
            case ATTACK_BASE:
                if(player.getBaseAttack().isReady()) {
                    player.getBaseAttack().perform();
                    gameLogic.attack_base(player);
                    this.activeAttacks.add(player.getBaseAttack());
                }



                break;
            case ATTACK_SPECIAL:
                if(player.getSpecialAttack().isReady()) {
                    gameLogic.attack_special(player);
                    player.getSpecialAttack().perform();
                    this.activeAttacks.add(player.getSpecialAttack());
                }
                break;

        }

    }

    public Collection<Character> getPlayers() {
        return this.idCharacterMap.values();
    }

    public ArrayList<Attack> getActiveAttacks() {
        return this.activeAttacks;
    }


    public void correct(IState state) {
        gameLogic.correct(state);
    }
    public ArrayList<Character> getAlivePlayers() {
        ArrayList<Character> alivePlayers = new ArrayList<Character>();
        for(Character c: idCharacterMap.values()) {
            if (c.isAlive()) {
                alivePlayers.add(c);
            }
        }
        return alivePlayers;
    }


    public Integer getmyID() {
        return myID;
    }
    public void setMyID(Integer id) {
        myID = id;
    }

    public void setEnabledAttacks(Set<Attack> attackSet) {
        ArrayList<Attack> temp = new ArrayList<Attack>();
        temp.addAll(attackSet);
        this.activeAttacks = temp;
    }

    public void setRespawnEnabled(boolean value) {
        this.respawnEnabled = value;
    }
}
