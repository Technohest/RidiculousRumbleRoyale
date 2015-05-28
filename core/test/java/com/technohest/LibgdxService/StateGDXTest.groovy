package com.technohest.LibgdxService

import com.badlogic.gdx.math.Vector2
import com.technohest.core.model.Attack
import com.technohest.core.model.Character

/**
 * Created by vilddjur on 2015-05-18.
 * @author Oskar Jedvert
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
            map.put(new Character(i,"Allden"), list);
        }

        StateGDX.getInstance().setState(map, null);

        IState state = new IState() {
            private HashMap<Character, ArrayList<Vector2>> map;

            @Override
            HashMap<Integer, ArrayList<Vector2>> getCharacterIdStates() {
                return null
            }

            @Override
            void setState(HashMap<Character, ArrayList<Vector2>> map1, HashMap<Attack, ArrayList<Vector2>> attackVectorMap) {

            }

            @Override
            HashMap<Integer, ArrayList<Vector2>> getAttackIdStates() {
                return null
            }

            @Override
            ArrayList<Attack> getActiveAttacks() {
                return null
            }

            @Override
            void setActiveAttacks(ArrayList<Attack> attacks) {

            }

            @Override
            void setCharacterIdVectorMap(HashMap<Integer, ArrayList<Vector2>> idVectorMap) {

            }

            @Override
            void setAttackIdVectorMap(HashMap<Integer, ArrayList<Vector2>> idVectorMap) {

            }

            @Override
            void setAlivePlayers(ArrayList<Character> players) {

            }

            @Override
            boolean equals(HashMap<Character, ArrayList<Vector2>> map1, HashMap<Attack, ArrayList<Vector2>> attackBodyVectorMap) {
                return false
            }
        }
        HashMap<Character, ArrayList<Vector2>> map2 =  new HashMap<Character, ArrayList<Vector2>>();
        for (int i = 0;i<10;i++) {
            //Create new character for every id. Make them all the same type "Allden".
            ArrayList<Vector2> list = new ArrayList<Vector2>();
            list.add(new Vector2(1,1));
            list.add(new Vector2(i,i));
            map2.put(new Character(i, "Allden"), list);
        }
        Character c  = map2.keySet().toArray()[0];
        c.setHealthPoints(2);
        state.setState(map2, null)
        assertFalse(state.equals(StateGDX.getInstance()));
    }
}

