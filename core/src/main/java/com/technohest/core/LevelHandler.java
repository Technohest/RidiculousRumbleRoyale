package com.technohest.core;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by vilddjur on 2015-04-02.
 */
public class LevelHandler{
    private TiledMap    tiledMap;

    public LevelHandler(){
        tiledMap = new TmxMapLoader().load("testlevel.tmx");
    }
    public TiledMap getLevel(){
        return tiledMap;
    }
}
