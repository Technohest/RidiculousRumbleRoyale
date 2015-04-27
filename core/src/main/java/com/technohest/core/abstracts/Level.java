package com.technohest.core.abstracts;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.World;


/**
 * Created by vilddjur on 2015-04-27.
 */
public abstract class Level {
    public final TiledMap map;
    public Level(String s) {
        TmxMapLoader l = new TmxMapLoader();
        map = l.load(s);
    }
    public TiledMap getMap(){
        return map;
    }
    public abstract void generate(World world);
    public abstract String getName();
}
