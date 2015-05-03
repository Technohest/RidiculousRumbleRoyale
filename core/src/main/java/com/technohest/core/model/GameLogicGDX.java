package com.technohest.core.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.technohest.constants.Constants;
import com.technohest.core.interfaces.IGameLogic;
import com.technohest.core.interfaces.ILevel;

/**
 * Created by oskar on 2015-05-01.
 */
public class GameLogicGDX implements IGameLogic{
    private final World world;
    public GameLogicGDX(){
        world = new World(new Vector2(0, Constants.GRAVITY), true);
    }

    public World getWorld(){
        return world;
    }

    @Override
    public void update(float v){
        world.step(v, 6, 2);
    }

    @Override
    public void generate(ILevel level) {
        level.generate(world);
    }
}
