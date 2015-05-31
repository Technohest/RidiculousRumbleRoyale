package com.technohest.LibgdxService.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Vector;

/**
 * The first level.
 * @author Oskar Jedvert
 */
public class MainLevel implements ILevel {
    private final TiledMap          map;

    private Vector<Vector2>         spawnPoints;

    private int                     spawnIndex;

    public MainLevel(){
        TmxMapLoader l = new TmxMapLoader();
        map = l.load("mainlevel.tmx");
        spawnIndex=-1;
    }
    @Override
    public TiledMap getMap() {
        return map;
    }

    @Override
    public void generate(World world) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Foreground");
        WorldGenerator.generateBox(world, layer);
        layer = (TiledMapTileLayer) map.getLayers().get("Spawnpoints");
        spawnPoints = WorldGenerator.getSpawnPoints(layer);
    }

    @Override
    public String getName() {
        return "Main Level";
    }

    @Override
    public Vector2 getSpawnPoint() {
        spawnIndex++;
        if(spawnIndex>=spawnPoints.size()){
            spawnIndex=0;
        }
        return spawnPoints.get(spawnIndex);
    }
}
