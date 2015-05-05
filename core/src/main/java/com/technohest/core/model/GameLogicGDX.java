package com.technohest.core.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.technohest.constants.Constants;
import com.technohest.core.interfaces.IGameLogic;
import com.technohest.core.interfaces.ILevel;

import java.util.HashMap;

/**
 * Created by oskar on 2015-05-01.
 */
public class GameLogicGDX implements IGameLogic{
    private final World world;
    //A map for bundling bodies with player objects
    private HashMap<Body,Character> bodyCharacterMap;
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
    public void generate(ILevel level,Character[] players) {
        level.generate(world);
        for (int i = 0;i<players.length;i++) {
            BodyDef bdef = new BodyDef();
            bdef.gravityScale = 4;
            bdef.position.set(145/Constants.PPM,80/Constants.PPM);
            bdef.linearDamping = 10;
            Body b = world.createBody(bdef);
            bodyCharacterMap.put(b,players[i]);
        }

    }

}
