package com.technohest.core.handlers;

import com.badlogic.gdx.physics.box2d.World;
import com.technohest.core.interfaces.Level;
import com.technohest.levels.MainLevel;

/**
 * Created by vilddjur on 2015-04-02.
 */
public class LevelHandler{
    private Level           level;
    public LevelHandler(){
        setLevel("mainlevel");
    }

    /**
     * Gets the current set level
     * @return
     * The current level
     */
    public Level getLevel(){
        return level;
    }

    /**
     * Sets the current level
     * @param name
     * the name of the level which, name is truncated "As df" = "asdf"
     */
    public void setLevel(String name){
        name = name.toLowerCase().trim();
        if (name.equals("mainlevel")) {
            level = new MainLevel();
        }
    }
}
