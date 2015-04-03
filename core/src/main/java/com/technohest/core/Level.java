package com.technohest.core;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.uwsoft.editor.renderer.SceneLoader;

/**
 * Created by vilddjur on 2015-04-02.
 */
public class Level extends Stage{
    public Level(){
        SceneLoader sl = new SceneLoader();

        sl.loadScene("MainScene");

        this.addActor(sl.getRoot());
    }
}
