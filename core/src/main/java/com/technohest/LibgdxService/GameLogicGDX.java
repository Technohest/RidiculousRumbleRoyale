package com.technohest.LibgdxService;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.esotericsoftware.minlog.Log;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.technohest.Tools.Debugg;
import com.technohest.constants.Constants;
import com.technohest.core.model.Character;
import com.technohest.core.network.IState;
import com.technohest.core.network.StateGDX;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The service managing the game physics.
 * Created by oskar on 2015-05-01.
 */
public class GameLogicGDX implements IGameLogic{
    private final World world;
    //A map for bundling bodies with player objects
    private HashMap<Body, Character> bodyCharacterMap;
    private HashMap<Integer,Character> idCharacterMap;
    private Boolean isClient = null;

    public GameLogicGDX(){
        world = new World(new Vector2(0, Constants.GRAVITY), true);
        bodyCharacterMap = new HashMap<Body, Character>();
    }

    public World getWorld(){
        return world;
    }

    @Override
    public void update(float v){
        world.step(v, 6, 2);
        updatePlayers(v);
    }

    @Override
    public void generate(ILevel level,HashMap<Integer,Character> idCharacterMap) {
        this.idCharacterMap = idCharacterMap;
        level.generate(world);

        int i=0;
        for (Character c: idCharacterMap.values()) {
            i++;
            BodyDef bdef1 = new BodyDef();
            bdef1.type = BodyDef.BodyType.DynamicBody;
            bdef1.gravityScale = 5;
            bdef1.position.set((100f+22f*i)/32f,140f/32f);
            FixtureDef fdef1 = new FixtureDef();
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(10.0f/32.0f,16.0f/32.0f);
            fdef1.shape = shape;

            Body b = world.createBody(bdef1);
            b.setLinearDamping(10);
            b.createFixture(fdef1);

            bodyCharacterMap.put(b,c);
        }
    }

    /**
     * Returns the associated character to the specified body.
     * @param body the body which will be used to search for the character.
     * @return the corresponding character to a body or null if the character was not found.
     */
    public Character getCharacterfromBody(Body body) {
        return bodyCharacterMap.get(body);
    }
    public Body getBodyFromCharacter(Character character) {
        for(Map.Entry<Body,Character> e: bodyCharacterMap.entrySet()) {
            if(e.getValue().equals(character))  {
                return e.getKey();
            }
        }
        return null;
    }

    /**
     * Updates the player state and player variables.
     * @param v the timestep
     */
    public void updatePlayers(float v) {
        boolean changed = false;

        for (Body b: bodyCharacterMap.keySet()) {
            if(b.getLinearVelocity().y != 0) {
                if (b.getLinearVelocity().y < 0) {
                    if (Debugg.debugging)
                        System.out.print("1 - ");
                    Character c = getCharacterfromBody(b);
                    c.setGrounded(false);
                    c.setState(Character.State.Falling);
                    changed = true;
                } else if(b.getLinearVelocity().y < 0 ) {
                    if (Debugg.debugging)
                        System.out.print("2 - ");
                    Character c = getCharacterfromBody(b);
                    c.setGrounded(false);
                    c.setState(Character.State.Jumping);
                    changed = true;
                }
            } else if(b.getLinearVelocity().x != 0) {
                if(b.getLinearVelocity().x < 0) {
                    if (Debugg.debugging)
                        System.out.print("3 - ");
                    Character c = getCharacterfromBody(b);
                    c.setGrounded(true);
                    c.setState(Character.State.Running);
                    c.setIsFacingRight(false);
                    changed = true;
                } else {
                    if (Debugg.debugging)
                        System.out.print("4 - ");
                    Character c = getCharacterfromBody(b);
                    c.setGrounded(true);
                    c.setState(Character.State.Running);
                    c.setIsFacingRight(true);
                    changed = true;
                }
            } else {
                if (Debugg.debugging)
                    System.out.print("5 - ");
                Character c = getCharacterfromBody(b);
                c.setGrounded(true);
                c.setState(Character.State.Standing);
                changed = true;
            }

            if (changed && isClient != null && Debugg.debugging) {
                if (isClient) {
                    Log.info("[C] " + b.getPosition());
                } else {
                    Log.info("[S] " + b.getPosition());
                }
            }
        }
    }

    @Override
    public void moveLeft(Character player){
        Body playerBody = getBodyFromCharacter(player);
        playerBody.setLinearVelocity(new Vector2(-Constants.INITIAL_MOVEMENT_SPEED,playerBody.getLinearVelocity().y));
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
        Body playerBody = getBodyFromCharacter(player);

    }

    @Override
    public void attack_special(Character player) {
        Body playerBody = getBodyFromCharacter(player);

    }

    @Override
    public void setCharacterState(Character newState, Vector2 pos, Vector2 vel) {
        Character player = idCharacterMap.get(newState);
        Body playerBody = getBodyFromCharacter(player);

        if (playerBody != null) {
            playerBody.setLinearVelocity(vel);
            playerBody.setTransform(pos, playerBody.getAngle());
        }
        getCharacterFromID(newState.getId()).setAttributes(newState);
    }

    @Override
    public void correct(IState newState) {
        HashMap<Character, ArrayList<Vector2>> map =  newState.getState();
        for(Character i : map.keySet()){
            ArrayList<Vector2> temp = map.get(i);
            setCharacterState(i, temp.get(0), temp.get(1));
        }
        StateGDX.getInstance().setState(map);
    }

    @Override
    public HashMap<Character, ArrayList<Vector2>> generateState() {
        HashMap<Character, ArrayList<Vector2>> map = new HashMap<Character, ArrayList<Vector2>>();
        Collection<Character> ids = idCharacterMap.values();
        for(Character id : ids){
            ArrayList<Vector2> vector2s = new ArrayList<Vector2>();
            Body playerBody = getBodyFromCharacter(idCharacterMap.get(id));
            vector2s.add(playerBody.getPosition());
            vector2s.add(playerBody.getLinearVelocity());

            map.put(id, vector2s);
        }
        return map;
    }

    @Override
    public void setIsClient() {
        this.isClient = true;
    }

    @Override
    public void setIsServer() {
        this.isClient = false;
    }

    public Collection<Character> getCharacters() {
        return idCharacterMap.values();
    }

    public Character getCharacterFromID(int id) {
        for(Character c : idCharacterMap.values()){
            if(c.getId() == id){
                return c;
            }
        }
        return null;
    }
}

