package com.technohest.LibgdxService;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.technohest.LibgdxService.levels.ILevel;
import com.technohest.constants.Constants;
import com.technohest.LibgdxService.levels.LevelHandler;

import java.util.*;

/**
 * The service managing the game physics.
 * @author Oskar Jedvert
 * @author Tobias Alldén
 */
public class GameLogicGDX implements IGameLogic {
    private final World world;
    private LevelHandler levelHandler;
    private HashMap<Integer,Body> attackIdBodyMap;
    private ArrayList<Body> impactedAttacks;
    private HashMap<Integer,Integer> playerDamageTaken;
    private CollisionHandler collisionHandler;
    private HashMap<Integer,Body> characterIdBodyMap;

    public GameLogicGDX(){
        this.levelHandler = new LevelHandler();
        world = new World(new Vector2(0, Constants.GRAVITY), true);
        collisionHandler = new CollisionHandler(this);
        world.setContactListener(collisionHandler);
        this.attackIdBodyMap = new HashMap<Integer, Body>();
        this.characterIdBodyMap = new HashMap<Integer, Body>();
        impactedAttacks = new ArrayList<Body>();
        playerDamageTaken = new HashMap<Integer, Integer>();

    }

    public World getWorld(){
        return world;
    }

    @Override
    public void update(float v){
        world.step(v, 6, 2);

    }

    /**
     * Creates the world and the players.
     * @param characterIdArray
     */
    @Override
    public void generate(ArrayList<Integer> characterIdArray) {
        levelHandler.getLevel().generate(world);
        for (Integer i: characterIdArray) {
            createPlayer(i);
        }
    }
    /**
     * Creates the player body and assigns the player id to it.
     * @param playerId
     */
    public void createPlayer(Integer playerId) {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.gravityScale = 5;
        bdef.position.set(levelHandler.getLevel().getSpawnPoint());
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.PLAYER_WIDTH/32.0f,Constants.PLAYER_HEIGHT/32.0f);
        fdef.shape = shape;
        Body b = world.createBody(bdef);
        b.setLinearDamping(10);
        b.createFixture(fdef).setUserData(playerId);
        shape.setAsBox(4 / 32.0f, 4 / 32.0f, new Vector2(0, -((Constants.PLAYER_HEIGHT) / 32.0f)), 0);
        fdef.isSensor = true;
        b.setUserData("Player");
        b.createFixture(fdef).setUserData(b);
        characterIdBodyMap.put(playerId, b);

    }
    @Override
    public void respawnPlayer(Integer playerId) {
        createPlayer(playerId);
    }
    @Override
    public void resetDamageTaken(Integer playerId) {
        if(playerDamageTaken.containsKey(playerId)) {
           playerDamageTaken.remove(playerId);
        }
    }
    @Override
    public void destroyAttack(Integer attackId) {
        Body b = getBodyFromAttackId(attackId);
        if(b != null) {
            if(impactedAttacks.contains(b)) {
                impactedAttacks.remove(b);
            }
            world.destroyBody(b);
            attackIdBodyMap.remove(attackId);
        }
    }
    @Override
    public void killPlayer(Integer characterId) {
        world.destroyBody(getBodyFromplayerId(characterId));
        characterIdBodyMap.remove(characterId);
    }



    @Override
    public void moveLeft(Integer playerId){
        if(characterIdBodyMap.containsKey(playerId)) {
            Body playerBody = getBodyFromplayerId(playerId);
            playerBody.setLinearVelocity(new Vector2(-Constants.INITIAL_MOVEMENT_SPEED, playerBody.getLinearVelocity().y));
        }
    }
    @Override
    public void moveRight(Integer playerId){
        Body playerBody = getBodyFromplayerId(playerId);
        if(characterIdBodyMap.containsKey(playerId)) {
            playerBody.setLinearVelocity(new Vector2(Constants.INITIAL_MOVEMENT_SPEED, playerBody.getLinearVelocity().y));
        }
    }
    @Override
    public void jump(Integer playerId) {
        if(characterIdBodyMap.containsKey(playerId)) {
            if(getStateOfPlayer(playerId) != 0 || getStateOfPlayer(playerId) != 1) {
                Body playerBody = getBodyFromplayerId(playerId);
                playerBody.applyForceToCenter(0, Constants.JUMP_FORCE_MULTIPLIER, true);
            }
        }

    }
    @Override
    public void attack_base(Integer playerId,boolean isFacingRight) {
        if (characterIdBodyMap.containsKey(playerId)) {
            Body playerBody = getBodyFromplayerId(playerId);
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.DynamicBody;
            bdef.gravityScale = 0;
            if (isFacingRight) {
                bdef.position.set((playerBody.getPosition().x + Constants.PLAYER_WIDTH / 32.0f), (playerBody.getPosition().y));
            } else {
                bdef.position.set((playerBody.getPosition().x - Constants.PLAYER_WIDTH / 32.0f), (playerBody.getPosition().y));
            }
            FixtureDef fdef1 = new FixtureDef();
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(10.0f / 32.0f, 10.0f / 32.0f);
            fdef1.shape = shape;
            Body b = world.createBody(bdef);
            b.setLinearDamping(0);
            b.setUserData("baseAttack");
            b.createFixture(fdef1).setUserData(playerId);
            attackIdBodyMap.put(playerId, b);
        }
    }
    @Override
    public void attack_special(Integer playerId,boolean isFacingRight) {
        if (characterIdBodyMap.containsKey(playerId)) {
            Body playerBody = getBodyFromplayerId(playerId);
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.DynamicBody;
            bdef.gravityScale = 0;
            if (isFacingRight) {
                bdef.position.set((playerBody.getPosition().x + Constants.PLAYER_WIDTH / 32.0f), (playerBody.getPosition().y));
                bdef.linearVelocity.set(10, 0);

            } else {
                bdef.position.set((playerBody.getPosition().x - Constants.PLAYER_WIDTH / 32.0f), (playerBody.getPosition().y));
                bdef.linearVelocity.set(-10, 0);
            }

            FixtureDef fdef1 = new FixtureDef();
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(5.0f / 32.0f, 8.0f / 32.0f);
            fdef1.shape = shape;
            Body b = world.createBody(bdef);
            b.setLinearDamping(0);
            b.setUserData("specialAttack");
            b.createFixture(fdef1).setUserData(playerId);
            attackIdBodyMap.put(playerId, b);
        }
    }



    /**
     * Generates the state to be sent over the network.
     * @param aliveCharacterIds
     * @param attackIDTypeMap
     */
    @Override
    public void generateState(Set<Integer> aliveCharacterIds, HashMap<Integer,Integer> attackIDTypeMap) {
        HashMap<Integer, Vector2> playerIdVectormap = new HashMap<Integer, Vector2>();
        for(Integer i:aliveCharacterIds){
            if(characterIdBodyMap.containsKey(i)) {
                Body playerBody = getBodyFromplayerId(i);
                playerIdVectormap.put(i, playerBody.getPosition());
            }
        }
        HashMap<Integer,Vector2> attackIdVectorMap = new HashMap<Integer,Vector2>();
        for(Map.Entry<Integer,Integer> entry:attackIDTypeMap.entrySet()) {
            attackIdVectorMap.put(entry.getValue(), getBodyFromAttackId(entry.getKey()).getPosition());

        }
        StateGDX state = StateGDX.getInstance();
        state.setState(playerIdVectormap, attackIdVectorMap);
    }

    /**
     * Performs the correction if the client is out of sync with the server
     * @param newState
     */
    /**
    @Override
    public void correct(IState newState) {
        HashMap<Integer, ArrayList<Vector2>> map =  newState.getCharacterIdStates();
        for(Integer i : map.keySet()){
            ArrayList<Vector2> temp = map.get(i);
            setCharacterState(i, temp.get(0), temp.get(1));
        }
        HashMap<Integer,ArrayList<Vector2>> attackVectorMap = newState.getAttackIdStates();
        if(!attackVectorMap.isEmpty()) {
            for (Integer i : attackVectorMap.keySet()) {
                ArrayList<Vector2> temp = attackVectorMap.get(i);
                setAttackState(i, temp.get(0), temp.get(1));
            }
        }
    }
    */




    /**
     * Returns an Integer specifying state of character, 0 = falling, 1 = jumping, 2 = runnning right, 3 = running left, 4 = standing.
     * @param playerId
     * @return
     */
    public Integer getStateOfPlayer(Integer playerId) {
        Body b = getBodyFromplayerId(playerId);
        if (b.getLinearVelocity().y != 0) {
            if (b.getLinearVelocity().y > 0) {
                return 1;
            } else {
                return 0;
            }
        } else if (b.getLinearVelocity().x != 0) {
            if (b.getLinearVelocity().x > 0) {
                return 2;
            } else {
                return 3;
            }
        } else {
            return 4;
        }
    }
    @Override
    public boolean canAttack(Integer playerId) {
        return attackIdBodyMap.get(playerId) == null;

    }

    public Collection<Integer> getCharacterIds() {
        return characterIdBodyMap.keySet();
    }
    /**
     * Returns the associated character to the specified body.
     * @param body the body which will be used to search for the character.
     * @return the corresponding character to a body or null if the character was not found.
     */
    public Integer getCharacterIdfromBody(Body body) {
        if(characterIdBodyMap.containsValue(body)) {
            for (Integer i : characterIdBodyMap.keySet()) {
                if (characterIdBodyMap.get(i).equals(body)) {
                    return i;
                }
            }
        }
        return null;
    }
    public Body getBodyFromplayerId(Integer id) {
        return characterIdBodyMap.get(id);
    }
    @Override
    public ILevel getLevel() {
        return levelHandler.getLevel();
    }
    /**
     * Checks the impacted attack list for the body with attackType user data, then searches it's fixtures for one with
     * the id of the player
     * @param playerId
     * @param attackType
     * @return
     */
    public boolean getAttackHasInpacted(Integer playerId,String attackType) {
        for(Body b:impactedAttacks) {
            if(b.getUserData().equals(attackType)){
                for(Fixture f:b.getFixtureList()) {
                    if (f.getUserData().equals(playerId)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }
    public Body getBodyFromAttackId(Integer attackId) {
        return attackIdBodyMap.get(attackId);
    }
    public Integer getAttackIdFromBody(Body body) {
        if(attackIdBodyMap.containsValue(body)) {
            for (Integer i : attackIdBodyMap.keySet()) {
                if (attackIdBodyMap.get(i).equals(body)) {
                    return i;
                }
            }
        }
        return null;

    }
    @Override
    public Integer getPlayerTakenDamage(Integer playerId) {
        if(playerDamageTaken.containsKey(playerId)) {
            System.out.println("DMG " +  ""+playerDamageTaken.get(playerId));
            return playerDamageTaken.get(playerId);
        } else {
            return 0;
        }
    }

    /**
     * Sets the attack state, and if attack do not exist, it is created in the world
     * @param attackId
     * @param position
     * @param velocity
     */
    @Override
    public void setAttackState(Integer attackId, Vector2 position, Vector2 velocity) {
        Body attackBody = getBodyFromAttackId(attackId);
        if (attackBody != null) {
            attackBody.setLinearVelocity(velocity);
            attackBody.setTransform(position, attackBody.getAngle());
        }
        else {
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.DynamicBody;
            bdef.gravityScale = 0;
            bdef.position.set(position);
            bdef.linearVelocity.set(velocity);
            FixtureDef fdef1 = new FixtureDef();
            PolygonShape shape = new PolygonShape();
            if(velocity.x == 0) {
                shape.setAsBox(5.0f / 32.0f, 8.0f / 32.0f);
            }else {
                shape.setAsBox(10.0f / 32.0f, 10.0f / 32.0f);
            }
            fdef1.shape = shape;
            Body b = world.createBody(bdef);
            b.setLinearDamping(0);
            b.createFixture(fdef1).setUserData(attackId);
            attackIdBodyMap.put(attackId,b);

        }
    }

    @Override
    public void setCharacterState(Integer playerId, Vector2 pos, Vector2 vel) {
        Body playerBody = getBodyFromplayerId(playerId);
        if (playerBody != null) {
            playerBody.setLinearVelocity(vel);
            playerBody.setTransform(pos, playerBody.getAngle());
        }
    }
    public void setAttackInpacted(Body b) {
        this.impactedAttacks.add(b);
    }
    /**
     * Maps the damage dealt to the specified player.
     * @param playerID
     * @param damage
     */
    public void setDamageDealtToPlayer(Integer playerID, Integer damage) {
        playerDamageTaken.put(playerID,damage);

    }

}
