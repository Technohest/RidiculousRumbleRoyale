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

import static com.technohest.constants.Constants.PPM;

/**
 * Created by oskar on 2015-05-01.
 */
public class GrassLevel implements ILevel{
    private final TiledMap map;

    public GrassLevel(){
        TmxMapLoader l = new TmxMapLoader();
        map = l.load("grasslevel.tmx");
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
                    /**
                     * Create a "Chain Linked" polygon of corners which will check for collision later. Using this chainlink will allow our players to jump up into the box
                     * this can be changed easily by adding more items to the vector, e.g a bottom.
                     */
                    ChainShape cs = new ChainShape();
                    Vector2[] v = boxVector;

                    cs.createChain(v);
                    fdef.shape = cs;
                    world.createBody(bdef).createFixture(fdef);
                }
            }
        }
        layer = (TiledMapTileLayer) map.getLayers().get("Platforms");

        Vector2[] lineVector = new Vector2[1];
        lineVector[0] = new Vector2(-tileSize/2/PPM, tileSize/2/PPM);
        lineVector[1] = new Vector2(tileSize/2/PPM, tileSize/2/PPM);

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
                    /**
                     * Create a "Chain Linked" polygon of corners which will check for collision later. Using this chainlink will allow our players to jump up into the box
                     * this can be changed easily by adding more items to the vector, e.g a bottom.
                     */
                    ChainShape cs = new ChainShape();
                    Vector2[] v = boxVector;

                    cs.createChain(v);
                    fdef.shape = cs;
                    world.createBody(bdef).createFixture(fdef);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "grasslevel";
    }
}
