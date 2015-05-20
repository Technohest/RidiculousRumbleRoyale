package com.technohest.java

import com.badlogic.gdx.math.Vector2
import com.technohest.core.model.Character
import com.technohest.core.model.Projectile
import com.technohest.core.network.IState
import com.technohest.core.network.StateGDX

/**
 * Created by vilddjur on 2015-05-18.
 */
class StateGDXTest extends GroovyTestCase {
    void testGetInstance() {
        IState state = StateGDX.getInstance()
        assertNotNull(state);
    }

    void testEquals() {
        HashMap<Character, ArrayList<Vector2>> map =  new HashMap<Character, ArrayList<Vector2>>();
        for (int i = 0;i<10;i++) {
            //Create new character for every id. Make them all the same type "Allden".
            ArrayList<Vector2> list = new ArrayList<Vector2>();
            list.add(new Vector2(1,1));
            list.add(new Vector2(i,i));
            map.put(new Character(i, "Allden " + i.toString(),new Projectile("FireBall", 100, 10,10),new Projectile("FireBall", 100, 10,10)), list);
        }

        StateGDX.getInstance().setState(map);

        IState state = new IState() {
            private HashMap<Character, ArrayList<Vector2>> map;

            @Override
            public HashMap<Character, ArrayList<Vector2>> getState() {
                return this.map;
            }

            @Override
            public void setState(HashMap<Character, ArrayList<Vector2>> newMap) {
                this.map = newMap;
            }

            @Override
            public boolean equals(HashMap<Character, ArrayList<Vector2>> newMap){
                return this.map.equals(newMap);
            }
        }
        HashMap<Character, ArrayList<Vector2>> map2 =  new HashMap<Character, ArrayList<Vector2>>();
        for (int i = 0;i<10;i++) {
            //Create new character for every id. Make them all the same type "Allden".
            ArrayList<Vector2> list = new ArrayList<Vector2>();
            list.add(new Vector2(1,1));
            list.add(new Vector2(i,i));
            map2.put(new Character(i, "Allden " + i.toString(),new Projectile("FireBall", 100, 10,10),new Projectile("FireBall", 100, 10,10)), list);
        }
        Character c  = map2.keySet().toArray()[0];
        c.setHealthPoints(2);
        state.setState(map2)
        assertFalse(state.equals(StateGDX.getInstance()));
    }
}

