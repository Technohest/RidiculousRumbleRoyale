package com.technohest.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.technohest.core.interfaces.ILevel;

import java.util.Vector;

import static com.technohest.constants.Constants.PPM;

/**
 * Created by oskar on 2015-05-01.
 */
public class GrassLevel implements ILevel{
    private final TiledMap  map;

    private Vector<Vector2>       spawnPoints;

    private int                     spawnIndex;

    public GrassLevel(){
        TmxMapLoader l = new TmxMapLoader();
        map = l.load("grasslevel.tmx");
        spawnPoints = new Vector<Vector2>();
        spawnIndex=-1;
    }
    @Override
    public TiledMap getMap() {
        return map;
    }

    @Override
    public void generate(World world) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Foreground");

        /**
         * BodyDef and FixtureDef is used as temporary models which can later be added to a body
         */
        float tileSize = layer.getTileWidth();

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;

        FixtureDef fdef = new FixtureDef();
        fdef.friction = 0;
        fdef.filter.categoryBits = 1;
        fdef.filter.maskBits = -1;
        fdef.isSensor = false;

        Vector2[] boxVector = new Vector2[5];
        boxVector[0] = new Vector2(-tileSize/2/PPM, -tileSize/2/PPM);
        boxVector[1] = new Vector2(-tileSize/2/PPM, tileSize/2/PPM);
        boxVector[2] = new Vector2(tileSize/2/PPM, tileSize/2/PPM);
        boxVector[3] = new Vector2(tileSize/2/PPM, -tileSize/2/PPM);
        boxVector[4] = new Vector2(-tileSize/2/PPM, -tileSize/2/PPM);
        /**
         * Create a "Chain Linked" polygon of corners which will check for collision later. Using this chainlink will allow our players to jump up into the box
         * this can be changed easily by adding more items to the vector, e.g a bottom.
         */
        ChainShape cs = new ChainShape();

        cs.createChain(boxVector);
        fdef.shape = cs;
        /**
         * Create a box2d body for each tile
         */
        for(int r = 0; r < layer.getHeight(); r++){
            for(int c = 0; c < layer.getWidth(); c++){
                TiledMapTileLayer.Cell cell = layer.getCell(c,r);
                /**
                 * If there is a tile on (r,w)
                 */
                if(cell != null && cell.getTile() != null){
                    bdef.position.set(
                            (c + 0.5f) * tileSize / PPM,
                            (r + 0.5f) * tileSize / PPM
                    );

                    world.createBody(bdef).createFixture(fdef);
                }
            }
        }
        layer = (TiledMapTileLayer) map.getLayers().get("Platforms");

        Vector2[] lineVector = new Vector2[2];
        lineVector[0] = new Vector2(-tileSize/2/PPM, tileSize/2/PPM);
        lineVector[1] = new Vector2(tileSize/2/PPM, tileSize/2/PPM);
        /**
         * Create a "Chain Linked" polygon of corners which will check for collision later. Using this chainlink will allow our players to jump up into the box
         * this can be changed easily by adding more items to the vector, e.g a bottom.
         */
        cs = new ChainShape();
        cs.createChain(lineVector);
        fdef.shape = cs;
        /**
         * Create a box2d body for each tile
         */
        for(int r = 0; r < layer.getHeight(); r++){
            for(int c = 0; c < layer.getWidth(); c++){
                TiledMapTileLayer.Cell cell = layer.getCell(c,r);
                /**
                 * If there is a tile on (r,w)
                 */
                if(cell != null && cell.getTile() != null){
                    bdef.position.set(
                            (c + 0.5f) * tileSize / PPM,
                            (r + 0.5f) * tileSize / PPM
                    );

                    world.createBody(bdef).createFixture(fdef);
                }
            }
        }
        layer = (TiledMapTileLayer) map.getLayers().get("Spawnpoints");
        /**
         * Create spawnpoints
         */
        int index = 0;
        for(int r = 0; r < layer.getHeight(); r++){
            for(int c = 0; c < layer.getWidth(); c++){
                TiledMapTileLayer.Cell cell = layer.getCell(c,r);
                /**
                 * If there is a spawnpoint on (r,w) add spawnpoint
                 */
                if(cell != null && cell.getTile() != null){
                    Vector2 point = new Vector2((c + 0.5f) * tileSize / PPM, (r + 0.5f) * tileSize / PPM);
                    spawnPoints.add(index, point);
                    index++;
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Grass Level";
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
