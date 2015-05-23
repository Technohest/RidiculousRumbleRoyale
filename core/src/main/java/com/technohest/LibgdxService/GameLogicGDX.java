package com.technohest.LibgdxService;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.esotericsoftware.minlog.Log;
import com.technohest.Tools.Debugg;
import com.technohest.constants.Constants;
import com.technohest.core.model.*;
import com.technohest.core.model.Character;
import com.technohest.core.network.IState;
import com.technohest.core.network.StateGDX;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * The service managing the game physics.
 * @author Oskar Jedvert
 * @author Tobias Alldén
 */
public class GameLogicGDX implements IGameLogic {
    private final World world;
    private HashMap<Attack,Body> attackBodyMap;
    CollisionHandler collisionHandler;
    RRRGameModel model;
    //A map for bundling bodies with player objects
    private HashMap<Character, Body> characterBodyMap;

    //THIS ONE SHOULD BE IN MODEL!
    private HashMap<Integer,Character> idCharacterMap;

    public GameLogicGDX(RRRGameModel model){
        world = new World(new Vector2(0, Constants.GRAVITY), true);
        characterBodyMap = new HashMap<Character,Body>();
        collisionHandler = new CollisionHandler(this);
        world.setContactListener(collisionHandler);
        this.attackBodyMap = new HashMap<Attack, Body>();
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
            //Move to model
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

//Perhaps move state setting to model
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

    public Body getBodyFromAttack(Attack attack) {
       return attackBodyMap.get(attack);
    }



    public void respawnPlayer(Character player) {
                createPlayer(player);
    }


    public void destroyAttack(Attack attack) {
        Body b = getBodyFromAttack(attack);
        if(b != null) {
            world.destroyBody(b);
        }
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
    public void attack_base(Character player) {
        Body playerBody = getBodyFromId(player.getId());
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
            b.setUserData(player.getBaseAttack());
            b.createFixture(fdef1).setUserData(player.getBaseAttack());
            attackBodyMap.put(player.getBaseAttack(),b);
    }


    @Override
    public void attack_special(Character player) {
        Body playerBody = getBodyFromId(player.getId());
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.DynamicBody;
            bdef.gravityScale = 0;
            if (player.isFacingRight()) {
                bdef.position.set((playerBody.getPosition().x + Constants.PLAYER_WIDTH/32.0f), (playerBody.getPosition().y));
                bdef.linearVelocity.set(((Projectile)player.getSpecialAttack()).getVelocityX(),0);

            } else {
                bdef.position.set((playerBody.getPosition().x - Constants.PLAYER_WIDTH / 32.0f), (playerBody.getPosition().y));
                bdef.linearVelocity.set(-((Projectile)player.getSpecialAttack()).getVelocityX(), 0);
            }


            FixtureDef fdef1 = new FixtureDef();
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(5.0f / 32.0f, 8.0f / 32.0f);
            fdef1.shape = shape;
            Body b = world.createBody(bdef);
            b.setLinearDamping(0);
            b.setUserData(player.getSpecialAttack());
            b.createFixture(fdef1).setUserData(player.getSpecialAttack());
            attackBodyMap.put(player.getSpecialAttack(),b);
    }

        public Character getCharacterFromAttack(Attack attack) {
            if(attack instanceof Projectile) {
                for(Character c:getCharacters()) {
                    if(c.getBaseAttack().equals(attack)) {
                        return c;
                    }
                }
            } else {
                for(Character c:getCharacters()) {
                    if(c.getBaseAttack().equals(attack)) {
                        return c;
                    }
                }
            }
                return null;

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

    @Override
    public void setAttackState(Attack attack, Vector2 position, Vector2 velocity) {
        Body attackBody = getBodyFromAttack(attack);

        if (attackBody != null) {
            attackBody.setLinearVelocity(velocity);
            attackBody.setTransform(position, attackBody.getAngle());
        }
        else {
            if (getCharacterFromAttack(attack) != null) {
                if (attack instanceof MeleeAttack) {
                    attack_base(getCharacterFromAttack(attack));
                } else {
                    attack_special(getCharacterFromAttack(attack));
                }
            }
        }
    }

    private Body getBodyFromId(Integer playerId) {
        return characterBodyMap.get(idCharacterMap.get(playerId));
    }

    @Override
    public void correct(IState newState) {
        HashMap<Character, ArrayList<Vector2>> map =  newState.getCharacterStates();
        for(Character c : map.keySet()){
            ArrayList<Vector2> temp = map.get(c);
            setCharacterState(c.getId(), temp.get(0), temp.get(1));
        }
        HashMap<Attack,ArrayList<Vector2>> attackVectorMap = newState.getAttackStates();
        if(!attackVectorMap.isEmpty()) {
            for (Attack a : attackVectorMap.keySet()) {
                ArrayList<Vector2> temp = attackVectorMap.get(a);
                setAttackState(a, temp.get(0), temp.get(1));
            }
        }
        model.setEnabledAttacks(attackBodyMap.keySet());
    }

    @Override
    public void generateState() {
        HashMap<Character, ArrayList<Vector2>> map = new HashMap<Character, ArrayList<Vector2>>();
        Collection<Character> chars = getCharacters();
        for(Character c : chars){
            ArrayList<Vector2> vector2s = new ArrayList<Vector2>();
            Body playerBody = getBodyFromCharacter(c);
            vector2s.add(playerBody.getPosition());
            vector2s.add(playerBody.getLinearVelocity());

            map.put(c, vector2s);
        }
        HashMap<Attack,ArrayList<Vector2>> attackVectorMap = new HashMap<Attack, ArrayList<Vector2>>();
        for(Attack a:attackBodyMap.keySet()) {
            if(a.isReady()) {
                ArrayList<Vector2> Vector2s = new ArrayList<Vector2>();
                Vector2s.add(attackBodyMap.get(a).getPosition());
                Vector2s.add(attackBodyMap.get(a).getLinearVelocity());
                attackVectorMap.put(a, Vector2s);
            }
        }
        StateGDX state = StateGDX.getInstance();
        state.setState(map,attackVectorMap);
    }

    public Collection<Character> getCharacters() {
        Collection<Character> aliveCharacters = new ArrayList<Character>();
        for(Character c:idCharacterMap.values()) {
            if(c.isAlive()) {
                aliveCharacters.add(c);
            }
        }
        return aliveCharacters;
    }
}
