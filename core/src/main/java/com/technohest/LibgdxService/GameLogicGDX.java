package com.technohest.LibgdxService;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.technohest.constants.Constants;
import com.technohest.core.model.Character;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by oskar on 2015-05-01.
 */
public class GameLogicGDX implements IGameLogic{
    private final World world;
    private final CollisionHandler collisionHandler;
    //A map for bundling bodies with player objects
    private HashMap<Body, Character> bodyCharacterMap;
    private HashMap<Integer,Character> idCharacterMap;
    public GameLogicGDX(){
        world = new World(new Vector2(0, Constants.GRAVITY), true);
        bodyCharacterMap = new HashMap<Body, Character>();
        this.collisionHandler = new CollisionHandler(this);
        world.setContactListener(collisionHandler);
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
        int i = 10;
        for(Character c: idCharacterMap.values()) {
            i++;
            BodyDef bdef1 = new BodyDef();
            bdef1.type = BodyDef.BodyType.DynamicBody;
            bdef1.gravityScale = 5;
            bdef1.position.set((100f + 15*i) / 32f, 140f / 32f);
            FixtureDef fdef1 = new FixtureDef();
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(10.0f / 32.0f, 16.0f / 32.0f);
            fdef1.shape = shape;

            Body b = world.createBody(bdef1);
            b.setLinearDamping(10);
            b.createFixture(fdef1).setUserData(c);

            // Feet
            shape.setAsBox((4f / 32.f), (4f / 32f), new Vector2(0, -(16f/32f)),0);
            fdef1.shape = shape;
            fdef1.isSensor = true;
            b.createFixture(fdef1).setUserData(b);


            bodyCharacterMap.put(b, c);
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
                    c.setState(Character.State.Falling);
                } else if(b.getLinearVelocity().y < 0 ) {
                    Character c = getCharacterfromBody(b);
                    c.setState(Character.State.Jumping);
                }
            } else if(b.getLinearVelocity().x != 0) {
                if(b.getLinearVelocity().x < 0) {
                    Character c = getCharacterfromBody(b);
                    c.setState(Character.State.Running);
                    c.setIsFacingRight(false);
                } else {
                    Character c = getCharacterfromBody(b);
                    c.setState(Character.State.Running);
                    c.setIsFacingRight(true);
                }
            } else {
                Character c = getCharacterfromBody(b);
                c.setState(Character.State.Standing);
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
}
