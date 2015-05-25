package com.technohest.LibgdxService.levels;

import com.technohest.LibgdxService.ILevel;
import com.technohest.LibgdxService.levels.GrassLevel;
import com.technohest.LibgdxService.levels.MainLevel;

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
