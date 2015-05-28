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

    //TMP
    //private Boolean isClient;

    public RRRGameModel(){
        setGameLogic(new GameLogicGDX());
        this.idCharacterMap = new HashMap<Integer, Character>();
        activeAttacks = new ArrayList<Attack>();
        //Temp character for testing
        /*idCharacterMap.put(1,new Character("Allden",new Projectile("FireBall", 100, 10,10),new Projectile("FireBall", 100, 10,10)));
        idCharacterMap.put(2,new Character("Allden2",new Projectile("FireBall", 100, 10,10),new Projectile("FireBall", 100, 10,10)));
        myID =1;*/
    }
    public RRRGameModel(boolean testing) {
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
        //The characters will be created at the game start and since the network only knows the type
        //I needed to change input to be <Integer, Integer>. It will be changed to CharType in the future.
        this.idCharacterMap = new HashMap<Integer, Character>();
        for (Map.Entry<Integer, Integer> entry : idChararcerMap.entrySet()) {
            //Create new character for every id. Make them all the same type "Allden".
            //System.out.println("THE PLAYER ID " + i);
            this.idCharacterMap.put(entry.getKey(),new Character(entry.getKey(), "Name " + idChararcerMap.get(entry.getKey())));
        }

    }

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
                    c.respawn();
                    gameLogic.respawnPlayer(c.getId());
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
     * Performes an action on the specified player connected to playerId
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
                if(getPlayerFromID(playerid).getBaseAttack().isReady() && gameLogic.canAttack(playerid)) {
                    getPlayerFromID(playerid).getBaseAttack().perform();
                    gameLogic.attack_base(playerid,getPlayerFromID(playerid).isFacingRight());
                    this.activeAttacks.add(getPlayerFromID(playerid).getBaseAttack());
                }
                break;
            case ATTACK_SPECIAL:
                if(getPlayerFromID(playerid).getSpecialAttack().isReady() && gameLogic.canAttack(playerid)) {
                    gameLogic.attack_special(playerid,getPlayerFromID(playerid).isFacingRight());
                    getPlayerFromID(playerid).getSpecialAttack().perform();
                    this.activeAttacks.add(getPlayerFromID(playerid).getSpecialAttack());
                }
                break;
            default:
                break;
        }

    }

    /**
     * Checks if the specified attack has inpacted, both in the gameLogic and in the attack class.
     * @param a
     * @return
     */
    public boolean attackHasInpacted(Attack a) {
        if(a instanceof MeleeAttack) {
           return( gameLogic.getAttackHasInpacted(a.sourcePlayerId,"baseAttack") || a.getHasImpacted());
        } else {
            return (gameLogic.getAttackHasInpacted(a.sourcePlayerId,"specialAttack") || a.getHasImpacted());
        }
    }


    public void generateState() {
        StateGDX.getInstance().setAlivePlayers(getAliveCharacters());
        StateGDX.getInstance().setActiveAttacks(getActiveAttacks());
        HashSet<Integer> alivePlayers = getAlivePlayersId();
        Set<Integer> tmp = new HashSet<Integer>();
        for(Attack a:activeAttacks) {
            if(a.isEnabled()) {
                tmp.add(a.getSourcePlayerId());
            }
        }
        gameLogic.generateState(alivePlayers,tmp);
    }
    public void correct(IState state) {
        setEnabledAttacks(state.getActiveAttacks());
        gameLogic.correct(state);
    }



    public HashSet<Integer> getAlivePlayersId() {
        HashSet<Integer> alivePlayers = new HashSet<Integer>();
        for(Character c: idCharacterMap.values()) {
            if (c.isAlive()) {
                alivePlayers.add(c.getId());
            }
        }
        return alivePlayers;
    }
    public ArrayList<Character> getAliveCharacters() {
        ArrayList<Character> tmp = new ArrayList<Character>();
        for(Map.Entry<Integer, Character> entry: idCharacterMap.entrySet()) {
            if(idCharacterMap.get(entry.getKey()).isAlive()) {
                tmp.add(idCharacterMap.get(entry.getKey()));
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
        for(Map.Entry<Integer, Character> entry : idCharacterMap.entrySet()) {
            if(idCharacterMap.get(entry.getKey()).getName().equals(name)) {
                return idCharacterMap.get(entry.getKey());
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
     * Sets state of player based on a integer.
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
            default:
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
