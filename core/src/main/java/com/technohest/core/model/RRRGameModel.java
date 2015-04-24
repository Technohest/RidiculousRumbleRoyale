package com.technohest.core.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.technohest.constants.Constants;
import com.technohest.core.handlers.LevelHandler;

import static com.technohest.constants.Constants.PPM;

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
    public TiledMap getLevel() {
        return levelHandler.getLevel();
    }

    public World getWorld() {
        return world;
    }

    /**
     * Initiatlizes all tiles with their corresponding box2d bodies
     */
    public void generateWorld() {
        TiledMap levelMap = levelHandler.getLevel();
        TiledMapTileLayer layer = (TiledMapTileLayer) levelMap.getLayers().get("Foreground");
        /**
         * BodyDef and FixtureDef is used as temporary models which can later be added to a body
         */
        float tileSize = layer.getTileWidth();
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
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
                    bdef.type = BodyDef.BodyType.StaticBody;
                    bdef.position.set(
                            (c + 0.5f) * tileSize / PPM,
                            (r + 0.5f) * tileSize / PPM
                    );
                    /**
                     * Create a "Chain Linked" polygon of corners which will check for collision later. Using this chainlink will allow our players to jump up into the box
                     * this can be changed easily by adding more items to the vector, e.g a bottom.
                     */
                    ChainShape cs = new ChainShape();
                    Vector2[] v = new Vector2[5];
                    v[0] = new Vector2(-tileSize/2/PPM, -tileSize/2/PPM);
                    v[1] = new Vector2(-tileSize/2/PPM, tileSize/2/PPM);
                    v[2] = new Vector2(tileSize/2/PPM, tileSize/2/PPM);
                    v[3] = new Vector2(tileSize/2/PPM, -tileSize/2/PPM);
                    v[4] = new Vector2(-tileSize/2/PPM, -tileSize/2/PPM);


                    cs.createChain(v);
                    fdef.friction = 0;
                    fdef.shape = cs;
                    fdef.filter.categoryBits = 1;
                    fdef.filter.maskBits = -1;
                    fdef.isSensor = false;
                    world.createBody(bdef).createFixture(fdef);
                }
            }
        }
    }

    public void step(float v) {
        world.step(v, 6, 2);
    }
}
