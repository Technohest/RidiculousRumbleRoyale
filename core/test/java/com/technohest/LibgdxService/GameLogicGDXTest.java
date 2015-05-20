package com.technohest.LibgdxService;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.technohest.LibgdxService.levels.MainLevel;
import com.technohest.core.model.*;
import com.technohest.core.model.Character;
import com.technohest.core.network.IState;
import com.technohest.core.network.StateGDX;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by vilddjur on 2015-05-18.
 */
public class GameLogicGDXTest {

  /*  @Test
    public void testCorrect() throws Exception {
        GameLogicGDX gl = new GameLogicGDX(true){
            @Override
            public void generate(ILevel level,HashMap<Integer,Character> idCharacterMap) {
                this.idCharacterMap = idCharacterMap;
                level.generate(world);

                int i=0;
                for (Character c: idCharacterMap.values()) {
                    i++;
                    BodyDef bdef1 = new BodyDef();
                    bdef1.type = BodyDef.BodyType.DynamicBody;
                    bdef1.gravityScale = 5;
                    bdef1.position.set((100f+22f*i)/32f,140f/32f);
                    FixtureDef fdef1 = new FixtureDef();
                    PolygonShape shape = new PolygonShape();
                    shape.setAsBox(10.0f/32.0f,16.0f/32.0f);
                    fdef1.shape = shape;

                    Body b = world.createBody(bdef1);
                    b.setLinearDamping(10);
                    b.createFixture(fdef1);

                    bodyCharacterMap.put(b,c);
                }
            }

        };
        IState state = StateGDX.getInstance();
        HashMap<Character, ArrayList<Vector2>> map =  new HashMap<Character, ArrayList<Vector2>>();
        for (int i = 0;i<10;i++) {
            //Create new character for every id. Make them all the same type "Allden".
            ArrayList<Vector2> list = new ArrayList<Vector2>();
            list.add(new Vector2(1,1));
            list.add(new Vector2(i,i));
            map.put(new Character(i, "Allden " + i,new Projectile("FireBall", 100, 10,10),new Projectile("FireBall", 100, 10,10)), list);
        }
        StateGDX.getInstance().setState(map);

        HashMap<Integer, Character> idMap = new HashMap<Integer, Character>();

        for(int i = 0;i<10;i++){
            idMap.put(i, new Character(i, "Allden " + i, new Projectile("FireBall", 100, 10, 10), new Projectile("FireBall", 100, 10, 10)));
            idMap.get(i).setHealthPoints(2);
        }

        gl.generate(new ILevel(){

            @Override
            public TiledMap getMap() {
                return null;
            }

            @Override
            public void generate(World world) {

            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public Vector2 getSpawnPoint() {
                return null;
            }
        }
                , idMap);
        gl.correct(state);

        HashMap<Character, ArrayList<Vector2>> map2 = gl.generateState();
        assertTrue(map2.equals(map));
    }*/
}