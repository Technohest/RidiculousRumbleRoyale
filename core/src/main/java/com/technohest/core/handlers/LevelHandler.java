package com.technohest.core.handlers;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by vilddjur on 2015-04-02.
 */
public class LevelHandler{
    private TiledMap    tiledMap;
    private TmxMapLoader loader;
    public LevelHandler(){
        loader = new TmxMapLoader();
        tiledMap = loader.load("testlevel.tmx");
    }

    /**
     * Gets the current set level
     * @return
     * The current level
     */
    public TiledMap getLevel(){
        return tiledMap;
    }

    /**
     * Sets the current level
     * @param name
     * the name of the level which, name is truncated "As df" = "asdf.tmx"
     */
    public void setLevel(String name){
        name = name.toLowerCase().trim();
        tiledMap = loader.load(name + ".tmx");
    }
}
