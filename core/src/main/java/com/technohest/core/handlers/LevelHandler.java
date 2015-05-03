package com.technohest.core.handlers;

import com.technohest.core.interfaces.ILevel;
import com.technohest.levels.GrassLevel;
import com.technohest.levels.MainLevel;

/**
 * Created by vilddjur on 2015-04-02.
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
