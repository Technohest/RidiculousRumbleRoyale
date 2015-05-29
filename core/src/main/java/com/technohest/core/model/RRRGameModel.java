package com.technohest.core.model;

import com.technohest.LibgdxService.GameLogicGDX;
import com.technohest.LibgdxService.IGameLogic;
import com.technohest.LibgdxService.IState;
import com.technohest.LibgdxService.StateGDX;

import java.util.*;

/**
 * The main model for Ridiculous Rumble Royale.
 * @author Oskar Jedvert
 * @author Tobias Alldén
 */
public class RRRGameModel {
    private Integer myID;
    private HashMap<Integer,Character> idCharacterMap;
    private ArrayList<Attack> activeAttacks;
    private IGameLogic gameLogic;
    private boolean respawnEnabled;

    public RRRGameModel(){
        setGameLogic(new GameLogicGDX());
        this.idCharacterMap = new HashMap<Integer, Character>();
        activeAttacks = new ArrayList<Attack>();
    }
    public RRRGameModel(boolean testing) {
        this.idCharacterMap = new HashMap<Integer, Character>();
        this.activeAttacks = new ArrayList<Attack>();
    }



    /**
     * Initializes all tiles with their corresponding box2d bodies
     */
    public void generateWorld() {
        gameLogic.generate(new ArrayList<Integer>(idCharacterMap.keySet()));
    }

    /**
     * Initializes the model with a id/player List
     * @param idChararcerMap the new idCharacterMap
     */
    public void init(HashMap<Integer,Integer> idChararcerMap) {
        this.idCharacterMap = new HashMap<Integer, Character>();
        for (Integer i: idChararcerMap.keySet()) {
            //Create new character for every id. Set the name to be "Name" + their unique id.
            this.idCharacterMap.put(i,new Character(i, "Name " + idChararcerMap.get(i)));
        }
    }

    /**
     * Step the gameLogic forward.
     * @param v
     * How big of a timestep to be performed.
     */
    public void step(float v) {
        gameLogic.update(v);
        updatePlayers(v);
        updateAttacks(v);
    }

    /**
     * Updates all the players.
     */
    public void updatePlayers(float v) {
        for (Character c : idCharacterMap.values()) {
            if (c.isAlive()) {
                if (c.getHealthPoints() < 0) {
                    c.die();
                    gameLogic.killPlayer(c.getId());
                    c.setShouldRespawn(this.respawnEnabled);
                } else {
                    c.takeDamage(gameLogic.getPlayerTakenDamage(c.getId()));
                    gameLogic.resetDamageTaken(c.getId());
                    setCharacterState(c, gameLogic.getStateOfPlayer(c.getId()));
                }
            } else {
                c.addRespawnTimer(v);
                if (c.respawnTimeDone()) {
                    gameLogic.respawnPlayer(c.getId());
                    c.respawn();
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
            if (attackHasInpacted(a)) {
                a.reset();
                gameLogic.destroyAttack(a.getSourcePlayerId());
                attackstoberemoved.add(a);
            } else if (!a.isReady()) {
                if (a.timeLeft()) {
                    a.incrementTime(v);
                } else {
                    a.reset();
                    a.setEnabled(false);
                    gameLogic.destroyAttack(a.getSourcePlayerId());
                    attackstoberemoved.add(a);
                }
            }
        }
        for(Attack a:attackstoberemoved) {
            a.setEnabled(true);
            activeAttacks.remove(a);

        }
        attackstoberemoved.clear();
    }


    /**
     * Performs an action on the specified player connected to playerId
     * @param action
     */
    public void performAction(Action action) {
        Integer playerid = action.getPlayerID();
        switch(action.getActionID()) {
            case JUMP:
                if(getPlayerFromID(playerid).isGrounded()) {
                    gameLogic.jump(playerid);
                }

                break;
            case MOVE_RIGHT:
                gameLogic.moveRight(playerid);
                break;
            case MOVE_LEFT:
                gameLogic.moveLeft(playerid);
                break;
            case ATTACK_BASE:
                // Do the attack only if player attack is ready.
                if(getPlayerFromID(playerid).getBaseAttack().isReady() && gameLogic.canAttack(playerid)) {
                    getPlayerFromID(playerid).getBaseAttack().perform();
                    gameLogic.attack_base(playerid,getPlayerFromID(playerid).isFacingRight());
                    this.activeAttacks.add(getPlayerFromID(playerid).getBaseAttack());
                }
                break;
            case ATTACK_SPECIAL:
                // Do the attack only if player attack is ready.
                if(getPlayerFromID(playerid).getSpecialAttack().isReady() && gameLogic.canAttack(playerid)) {
                    getPlayerFromID(playerid).getSpecialAttack().perform();
                    this.activeAttacks.add(getPlayerFromID(playerid).getSpecialAttack());
                    gameLogic.attack_special(playerid,getPlayerFromID(playerid).isFacingRight());

                }
                break;
        }
    }

    /**
     * Checks if the specified attack has impacted, both in the gameLogic and in the attack class.
     * @param a the attack to check for collision.
     * @return
     * true if an impact has occurred.
     */
    public boolean attackHasInpacted(Attack a) {
        if(a instanceof MeleeAttack) {
           return( gameLogic.getAttackHasInpacted(a.sourcePlayerId,"baseAttack") || a.getHasImpacted());
        } else {
            return (gameLogic.getAttackHasInpacted(a.sourcePlayerId,"specialAttack") || a.getHasImpacted());
        }
    }

    /**
     * Generates the state of the game so it can be sent across the network.
     */
    public void generateState() {
        HashMap<Integer,Integer> attackIdTypeMap = new HashMap<Integer, Integer>();
        Set<Integer> alivePlayers = this.getAlivePlayersId();
        for(Attack a:activeAttacks) {
            if(a.isEnabled()) {
                if(a instanceof Projectile) {
                    attackIdTypeMap.put(a.getSourcePlayerId(),1);
                } else {
                    attackIdTypeMap.put(a.getSourcePlayerId(),0);
                }
            }
        }
        gameLogic.generateState(alivePlayers, attackIdTypeMap);
    }

    /**
    public void correct(IState state) {
        setEnabledAttacks(state.getActiveAttacks());
        gameLogic.correct(state);
    }
     */


    /**
     * @return
     * A HashSet of the id's of all alive players.
     */
    public HashSet<Integer> getAlivePlayersId() {
        HashSet<Integer> alivePlayers = new HashSet<Integer>();
        for(Character c: idCharacterMap.values()) {
            if (c.isAlive()) {
                alivePlayers.add(c.getId());
            }
        }
        return alivePlayers;
    }

    public ArrayList<Integer> getAliveCharacters() {
        ArrayList<Integer> tmp = new ArrayList<Integer>();
        for(Integer i: idCharacterMap.keySet()) {
            if(idCharacterMap.get(i).isAlive()) {
                tmp.add(i);
            }
        }
        return tmp;
    }

    public Integer getmyID() {
        return myID;
    }
    public Collection<Character> getPlayers() {
        return this.idCharacterMap.values();
    }
    public ArrayList<Attack> getActiveAttacks() {
        return this.activeAttacks;
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
    public Integer getMyID() {
        return myID;
    }

    /**
     * Sets state of player based on a integer representing their state.
     * @param i
     */
    public void setCharacterState(Character c, Integer i) {
        switch(i) {
            case 0:
                c.setState(Character.State.Falling);
                c.setGrounded(false);
                break;
            case 1:
                c.setState(Character.State.Jumping);
                c.setGrounded(false);
                break;
            case 2:
                c.setState(Character.State.Running);
                c.setGrounded(true);
                c.setIsFacingRight(true);
                break;
            case 3:
                c.setState(Character.State.Running);
                c.setGrounded(true);
                c.setIsFacingRight(false);
                break;
            case 4:
                c.setState(Character.State.Standing);
                c.setGrounded(true);
                break;
        }
    }

    public void setGameLogic(IGameLogic gl){
        gameLogic = gl;
    }
    public void setMyID(Integer id) {
        myID = id;
    }
    public void setEnabledAttacks(ArrayList<Attack> activeAttacks) {
        this.activeAttacks = activeAttacks;
    }

    public void setRespawnEnabled(boolean value) {
        this.respawnEnabled = value;
    }
    public void setMyID(int id) {
        this.myID = id;
    }

}
