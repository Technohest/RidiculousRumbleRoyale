package com.technohest.LibgdxService;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.technohest.constants.Constants;
import com.technohest.core.model.Character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by oskar on 2015-05-01.
 */
public class GameLogicGDX implements IGameLogic{
    private final World world;
    //A map for bundling bodies with player objects
    private HashMap<Body, Character> bodyCharacterMap;
    private HashMap<Integer,Character> idCharacterMap;
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
            BodyDef bdef = new BodyDef();
            bdef.gravityScale = 10;
            bdef.position.set(140,100);
            bdef.linearVelocity.set(0,0);
            bdef.type = BodyDef.BodyType.DynamicBody;
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(20/Constants.PPM,30/Constants.PPM);
            FixtureDef fdef = new FixtureDef();
            fdef.shape = shape;
            Body b = world.createBody(bdef);
            b.createFixture(fdef);
            bodyCharacterMap.put(b,c);
        }
    }

    /**
     * Returns the associated character to the specified body.
     * @param body
     * @return
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
     * @param v
     */
    public void updatePlayers(float v) {
        for (Body b: bodyCharacterMap.keySet()) {
            if(b.getLinearVelocity().y != 0) {
                if (b.getLinearVelocity().y < 0) {
                    Character c = getCharacterfromBody(b);
                    c.setGrounded(false);
                    c.setState(Character.State.Falling);
                } else if(b.getLinearVelocity().y < 0 ) {
                    Character c = getCharacterfromBody(b);
                    c.setGrounded(false);
                    c.setState(Character.State.Jumping);
                }
            } else if(b.getLinearVelocity().x != 0) {
                if(b.getLinearVelocity().x < 0) {
                    Character c = getCharacterfromBody(b);
                    c.setGrounded(true);
                    c.setState(Character.State.Running);
                    c.setIsFacingRight(false);
                } else {
                    Character c = getCharacterfromBody(b);
                    c.setGrounded(true);
                    c.setState(Character.State.Running);
                    c.setIsFacingRight(true);
                }
            } else {
                Character c = getCharacterfromBody(b);
                c.setGrounded(true);
                c.setState(Character.State.Standing);
            }
        }

    }

    @Override
    public void moveLeft(Character player){
        Body playerBody = getBodyFromCharacter(player);
        playerBody.setLinearVelocity(new Vector2((playerBody.getLinearVelocity().x - Constants.INITIAL_MOVEMENT_SPEED),playerBody.getLinearVelocity().y));
    }

    @Override
    public void moveRight(Character player){
        Body playerBody = getBodyFromCharacter(player);
        playerBody.setLinearVelocity(new Vector2(playerBody.getLinearVelocity().x + Constants.INITIAL_MOVEMENT_SPEED,playerBody.getLinearVelocity().y));

    }

    @Override
    public void jump(Character player) {
        Body playerBody = getBodyFromCharacter(player);
        playerBody.applyForceToCenter(0,Constants.JUMP_FORCE_MULTIPLIER,true);

    }

    @Override
    public void attack_base(Character player) {
        Body playerBody = getBodyFromCharacter(player);

    }

    @Override
    public void attack_special(Character player) {
        Body playerBody = getBodyFromCharacter(player);

    }
}
