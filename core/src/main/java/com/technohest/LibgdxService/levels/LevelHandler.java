package com.technohest.LibgdxService.levels;

import com.technohest.LibgdxService.ILevel;

/**
 * The level handler, here the level which will be played chosen.
 * @author Oskar Jedvert
 */
public class LevelHandler{
    private ILevel level;
    public LevelHandler(){
        setLevel("grasslevel");
    }

    /**
     * Gets the current set level
     * @return
     * The current level
     */
    public ILevel getLevel(){
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
        }else if(name.equals("grasslevel")){
            level =  new GrassLevel();
        }
    }
}
