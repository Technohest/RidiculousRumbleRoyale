package com.technohest.LibgdxService.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;


/**
 * An interface for a level.
 * @author Oskar Jedvert.
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
