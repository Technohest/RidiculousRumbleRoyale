package com.technohest.LibgdxService;

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
    private HashMap<Body, com.technohest.core.model.Character> bodyCharacterMap;
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
    }

    @Override
    public void generate(ILevel level,ArrayList<Character> players) {
        level.generate(world);
        Random random = new Random();

        int i=0;

        for (Character c: players) {
            i++;
            BodyDef bdef = new BodyDef();
            bdef.gravityScale = 10;
            bdef.position.set(100 + (22*i),100);
            bdef.linearDamping = 10;
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(20/Constants.PPM,30/Constants.PPM);
            FixtureDef fdef = new FixtureDef();
            fdef.shape = shape;
            Body b = world.createBody(bdef);
            b.setType(BodyDef.BodyType.DynamicBody);
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
            if(e.getValue() == character) {
                return e.getKey();
            }
        }
        return null;
    }

}
