package com.technohest.core.menu;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by time on 2015-05-30.
 */
public class CharacterSelectListenerTest {

    @Test
    public void testTouchDown() throws Exception {
        CharacterSelectListener c = new CharacterSelectListener(null, "Alldén");
        assertTrue(c.touchDown(null, 0, 0, 0, 0));
    }

    @Test
    public void testTouchUp() throws Exception {
        LobbyScreenStub stub = new LobbyScreenStub();
        CharacterSelectListener c = new CharacterSelectListener(stub, "Alldén");
        c.touchUp(null, 0, 0, 0, 0);
        assertTrue(stub.isSet());
    }
}
class LobbyScreenStub extends LobbyScreen {
    boolean set = false;

    @Override
    public void selectCharacter(String character) {
        set = true;
    }

    public boolean isSet() {
        return set;
    }
}