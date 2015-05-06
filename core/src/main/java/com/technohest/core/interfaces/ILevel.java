package com.technohest.core.interfaces;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;


/**
 * Created by vilddjur on 2015-04-27.
 */
public interface ILevel {

    /**
     * Gets the TiledMap of the level
     * @return
     * The levels TiledMap
     */
    TiledMap getMap();

    /**
     * Generates bodies for the level
     * @param world
     * The target world
     */
    void generate(World world);

    /**
     * Gets the name of the current world.
     * @return
     */
    String getName();

    Vector2 getSpawnPoint();

}
