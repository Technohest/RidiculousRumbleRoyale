package com.technohest.core.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.technohest.constants.Constants;
import com.technohest.core.interfaces.Level;
import com.technohest.core.handlers.LevelHandler;

/**
 * Created by Oskar on 2015-03-24.
 */
public class RRRGameModel {
    private LevelHandler    levelHandler;
    private World           world;


    public RRRGameModel(){
        this.levelHandler = new LevelHandler();
        world = new World(new Vector2(0, Constants.GRAVITY), true);
    }
    public Level getLevel() {
        return levelHandler.getLevel();
    }

    public World getWorld() {
        return world;
    }

    /**
     * Initiatlizes all tiles with their corresponding box2d bodies
     */
    public void generateWorld() {
        getLevel().generate(world);
    }

    public void step(float v) {
        world.step(v, 6, 2);
    }
}
