package com.technohest.core.interfaces;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.World;


/**
 * Created by vilddjur on 2015-04-27.
 */
public interface ILevel {
    TiledMap getMap();
    void generate(World world);
    String getName();
}
