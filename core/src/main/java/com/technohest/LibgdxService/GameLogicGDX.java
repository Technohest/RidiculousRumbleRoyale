package com.technohest.LibgdxService;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.esotericsoftware.minlog.Log;
import com.technohest.Tools.Debugg;
import com.technohest.constants.Constants;
import com.technohest.core.model.Attack;
import com.technohest.core.model.Character;
import com.technohest.core.model.Projectile;
import com.technohest.core.model.RRRGameModel;
import com.technohest.core.network.IState;
import com.technohest.core.network.StateGDX;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * The service managing the game physics.
 * @author Oskar Jedvert
 * @author Tobias Alldén
 */
public class GameLogicGDX implements IGameLogic {
    private final World world;
    CollisionHandler collisionHandler;
    RRRGameModel model;
    //A map for bundling bodies with player objects
    private HashMap<Character, Body> characterBodyMap;

    //THIS ONE SHOULD BE IN MODEL!
    private HashMap<Integer,Character> idCharacterMap;


    private HashMap<Attack,Body> attackBodyMap;

    public GameLogicGDX(RRRGameModel model){
        world = new World(new Vector2(0, Constants.GRAVITY), true);
        characterBodyMap = new HashMap<Character,Body>();
        attackBodyMap = new HashMap<Attack, Body>();
        collisionHandler = new CollisionHandler(this);
        world.setContactListener(collisionHandler);
        this.model = model;
    }

    public World getWorld(){
        return world;
    }

    @Override
    public void update(float v){
        world.step(v, 6, 2);

    }

    @Override
    public void generate(ILevel level,HashMap<Integer,Character> idCharacterMap) {
        this.idCharacterMap = idCharacterMap;
        level.generate(world);
        for (Character c: idCharacterMap.values()) {
            createPlayer(c);
        }
    }

    public void createPlayer(Character character) {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.gravityScale = 5;
        bdef.position.set(model.getLevel().getSpawnPoint());
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.PLAYER_WIDTH/32.0f,Constants.PLAYER_HEIGHT/32.0f);
        fdef.shape = shape;
        Body b = world.createBody(bdef);
        b.setLinearDamping(10);
        b.createFixture(fdef).setUserData(character);
        shape.setAsBox(4 / 32.0f, 4 / 32.0f, new Vector2(0, -((Constants.PLAYER_HEIGHT) / 32.0f)), 0);
        fdef.isSensor = true;
        b.createFixture(fdef).setUserData(b);
        characterBodyMap.put(character,b);

    }

    /**
     * Returns the associated character to the specified body.
     * @param body the body which will be used to search for the character.
     * @return the corresponding character to a body or null if the character was not found.
     */
    public Character getCharacterfromBody(Body body) {
        if(characterBodyMap.containsValue(body)) {
            for (Character c : characterBodyMap.keySet()) {
                if (characterBodyMap.get(c).equals(body)) {
                    return c;
                }
            }
        }
        return null;
    }
    public Body getBodyFromCharacter(Character character) {
        return characterBodyMap.get(character);
    }
    public Body getBodyFromAttack(Attack attack) {
        return attackBodyMap.get(attack);
    }
    public Attack getAttackFromBody(Body b) {
        for(Attack a:attackBodyMap.keySet()) {
            if(attackBodyMap.get(a).equals(b)) {
                return a;
            }
        }
        return null;
    }


    public void updatePlayer(Character c) {
        boolean changed = false;
            Body b = getBodyFromCharacter(c);
            if (b.getLinearVelocity().y != 0) {
                if (b.getLinearVelocity().y < 0) {
                    if (Debugg.debugging)
                        System.out.print("1 - ");
                    c.setState(Character.State.Falling);
                    changed = true;
                } else if (b.getLinearVelocity().y > 0) {
                    if (Debugg.debugging)
                        System.out.print("2 - ");
                    c.setState(Character.State.Jumping);
                    changed = true;
                }
            } else if (b.getLinearVelocity().x != 0) {
                if (b.getLinearVelocity().x < 0) {
                    if (Debugg.debugging)
                        System.out.print("3 - ");
                    c.setState(Character.State.Running);
                    c.setIsFacingRight(false);
                    changed = true;
                } else {
                    if (Debugg.debugging)
                        System.out.print("4 - ");
                    c.setState(Character.State.Running);
                    c.setIsFacingRight(true);
                    changed = true;
                }
            } else {
                if (Debugg.debugging)
                    System.out.print("5 - ");
                c.setState(Character.State.Standing);
                changed = true;


            }


            if (changed && Debugg.debugging) {
                Log.info("[S] " + b.getPosition());
            }

        }



    public void respawnPlayer(Character player) {
                createPlayer(player);
    }


    public void destroyAttack(Attack attack) {
        world.destroyBody(getBodyFromAttack(attack));
        attackBodyMap.remove(attack);
    }



    public void killPlayer(Character character) {
        world.destroyBody(getBodyFromCharacter(character));
        characterBodyMap.remove(character);
    }




    @Override
    public void moveLeft(Character player){
        Body playerBody = getBodyFromCharacter(player);
        playerBody.setLinearVelocity(new Vector2(-Constants.INITIAL_MOVEMENT_SPEED, playerBody.getLinearVelocity().y));
    }

    @Override
    public void moveRight(Character player){
        Body playerBody = getBodyFromCharacter(player);
        playerBody.setLinearVelocity(new Vector2(Constants.INITIAL_MOVEMENT_SPEED,playerBody.getLinearVelocity().y));

    }

    @Override
    public void jump(Character player) {
        if(player.isGrounded()) {
            Body playerBody = getBodyFromCharacter(player);
            playerBody.applyForceToCenter(0, Constants.JUMP_FORCE_MULTIPLIER, true);
            player.setGrounded(false);
        }

    }

    @Override
    public void attack_base(Character player,Attack attack) {
        Body playerBody = getBodyFromCharacter(player);
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.DynamicBody;
            bdef.gravityScale = 0;
            if (player.isFacingRight()) {
                bdef.position.set((playerBody.getPosition().x + Constants.PLAYER_WIDTH/32.0f), (playerBody.getPosition().y));
            } else {
                bdef.position.set((playerBody.getPosition().x - Constants.PLAYER_WIDTH/32.0f), (playerBody.getPosition().y));
            }
            FixtureDef fdef1 = new FixtureDef();
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(10.0f / 32.0f, 10.0f / 32.0f);
            fdef1.shape = shape;
            Body b = world.createBody(bdef);
            b.setLinearDamping(0);
            b.createFixture(fdef1).setUserData(attack);
            attackBodyMap.put(attack,b);
    }


    @Override
    public void attack_special(Character player, Attack attack) {
        Body playerBody = getBodyFromCharacter(player);
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.DynamicBody;
            bdef.gravityScale = 0;
            if (player.isFacingRight()) {
                bdef.position.set((playerBody.getPosition().x + Constants.PLAYER_WIDTH/32.0f), (playerBody.getPosition().y));
                bdef.linearVelocity.set(((Projectile)attack).getVelocityX(),0);

            } else {
                bdef.position.set((playerBody.getPosition().x - Constants.PLAYER_WIDTH / 32.0f), (playerBody.getPosition().y));
                bdef.linearVelocity.set(-((Projectile)attack).getVelocityX(), 0);
            }


            FixtureDef fdef1 = new FixtureDef();
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(5.0f / 32.0f, 8.0f / 32.0f);
            fdef1.shape = shape;
            Body b = world.createBody(bdef);
            b.setLinearDamping(0);
            b.createFixture(fdef1).setUserData(attack);
            attackBodyMap.put(attack,b);

    }
    public HashMap<Attack,Body> getAttackMap() {
        return attackBodyMap;
    }


    @Override
    public void setCharacterState(Integer playerId, Vector2 pos, Vector2 vel) {
        //Body playerBody = getBodyFromCharacter(player);
        Body playerBody = getBodyFromId(playerId);

        if (playerBody != null) {
            playerBody.setLinearVelocity(vel);
            playerBody.setTransform(pos, playerBody.getAngle());
        }
    }

    private Body getBodyFromId(Integer playerId) {
        return characterBodyMap.get(idCharacterMap.get(playerId));
    }

    @Override
    public void correct(IState newState) {
        HashMap<Character, ArrayList<Vector2>> map =  newState.getState();
        for(Character c : map.keySet()){
            ArrayList<Vector2> temp = map.get(c);
            setCharacterState(c.getId(), temp.get(0), temp.get(1));
        }
        StateGDX.getInstance().setState(map);
    }

    @Override
    public HashMap<Character, ArrayList<Vector2>> generateState() {
        HashMap<Character, ArrayList<Vector2>> map = new HashMap<Character, ArrayList<Vector2>>();
        Collection<Character> chars = getCharacters();
        for(Character c : chars){
            ArrayList<Vector2> vector2s = new ArrayList<Vector2>();
            Body playerBody = getBodyFromCharacter(c);
            vector2s.add(playerBody.getPosition());
            vector2s.add(playerBody.getLinearVelocity());

            map.put(c, vector2s);
        }
        return map;
    }

    public Collection<Character> getCharacters() {
        return idCharacterMap.values();
    }
}
