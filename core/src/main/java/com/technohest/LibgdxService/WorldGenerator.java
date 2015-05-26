package com.technohest.LibgdxService;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.Collections;
import java.util.Vector;

import static com.technohest.constants.Constants.PPM;

/**
 * Created by vilddjur on 2015-05-07.
 */
public class WorldGenerator {
    /**
     * Generate box-shaped bodies in a world using a layer
     * @param world
     * Target world
     * @param layer
     * Input layer.
     */
    public static void generateBox(World world, TiledMapTileLayer layer){
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
                    Body b = world.createBody(bdef);
                    b.setUserData("Level");
                    b.createFixture(fdef).setUserData("Level");
                }
            }
        }

    }

    /**
     * Generate flat bodies in a world using a layer
     * @param world
     * Target world
     * @param layer
     * Input layer
     */
    public static void generatePlatform(World world, TiledMapTileLayer layer){
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
        Vector2[] lineVector = new Vector2[2];
        lineVector[0] = new Vector2(-tileSize/2/PPM, tileSize/2/PPM);
        lineVector[1] = new Vector2(tileSize/2/PPM, tileSize/2/PPM);
        /**
         * Create a "Chain Linked" polygon of corners which will check for collision later. Using this chainlink will allow our players to jump up into the box
         * this can be changed easily by adding more items to the vector, e.g a bottom.
         */
        ChainShape cs = new ChainShape();
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

                    Body b = world.createBody(bdef);
                    b.setUserData("Level");
                    b.createFixture(fdef).setUserData("Level");
                }
            }
        }
    }

    /**
     * Gets spawnpoints using each tile in a layer
     * @param layer
     * Input layer, each tile will be a spawnpoint
     * @return
     * Returns a vector of randomly sorted points
     */
    public static Vector<Vector2> getSpawnPoints(TiledMapTileLayer layer){
        float tileSize = layer.getTileWidth();
        Vector<Vector2> spawnPoints = new Vector<Vector2>();
        /**
         * Create spawnpoints
         */
        for(int r = 0; r < layer.getHeight(); r++){
            for(int c = 0; c < layer.getWidth(); c++){
                TiledMapTileLayer.Cell cell = layer.getCell(c,r);
                /**
                 * If there is a spawnpoint on (r,w) add spawnpoint
                 */
                if(cell != null && cell.getTile() != null){
                    Vector2 point = new Vector2((c + 0.5f) * tileSize / PPM, (r + 0.5f) * tileSize / PPM);
                    spawnPoints.add(point);
                }
            }
        }
        Collections.shuffle(spawnPoints);
        return spawnPoints;
    }
}
