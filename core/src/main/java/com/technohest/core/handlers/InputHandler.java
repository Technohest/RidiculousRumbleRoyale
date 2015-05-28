package com.technohest.core.handlers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.technohest.constants.Controls;

/**
 * A class for handling input.
 * @author Tobias Alldén
 */
public class InputHandler implements InputProcessor {

    //Bits (integers for the different actions)
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    public static final int JUMP = 4;
    public static final int BASE_ATTACK = 5;
    public static final int SPECIAL_ATTACK = 6;

    public static final int ESCAPE = 10;



    //Status for key, for example, buttons[3] = true, would mean that left(A) is being pressed
    public boolean[] buttons = new boolean[64];
    //old status of keys
    public boolean[] oldButtons = new boolean[64];




    /**
     * Constructor, creates a new InputHandler
     */
    public InputHandler() {

    }

    /**
     * Sets key-pressed to true or false.
     * @param key
     * @param down
     */
    public void set(int key, boolean down) {
        int button = -1;


        if(key == Controls.UP) button = JUMP;
        if(key == Controls.DOWN) button = DOWN;
        if(key == Controls.LEFT) button = LEFT;
        if(key == Controls.RIGHT) button = RIGHT;

        if(key == Keys.SPACE) button = JUMP;
        if(key == Controls.BASE_ATTACK) button = BASE_ATTACK;
        if(key == Controls.SPECIAL_ATTACK) button = SPECIAL_ATTACK;

        if(key == Keys.ESCAPE ||key == Keys.MENU) button = ESCAPE;

        if(button >=0) buttons[button] = down;
    }

    /**
     * Moves the current buttons to the old buttons,
     */
    public void tick() {
        for(int i =0; i<buttons.length;i++) {
            oldButtons[i] = buttons[i];
        }
    }

    /**
     * Checks if a key is pressed.
     * @param key
     */
    public boolean isPressed(int key) {
        return buttons[key];
    }

    /**
     * Returns true if input has ben recieved.
     * @return
     */
    public boolean hasInput(){
        for(int i = 0;i<buttons.length;i++) {
            if(buttons[i]) return true;
        }
        return false;
    }

    /**
     * Releases all keys
     */
    public void releaseAllKeys() {
        for (int i = 0; i < buttons.length; i++) {
            if (i == Controls.UP || i == Controls.DOWN) {
                continue;
            }
        buttons[i] = false;
    }
    }




    @Override
    public boolean keyDown(int i) {
        set(i,true);
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        set(i,false);
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        //will remain unimplemented, since this game is not for touch devices.
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        //will remain unimplemented, since this game is not for touch devices.
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        //will remain unimplemented, since this game is not for touch devices.
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        //will remain unimplemented, since this game is not for touch devices.
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
}
