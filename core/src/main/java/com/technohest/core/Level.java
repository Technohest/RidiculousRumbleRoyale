package com.technohest.core;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.uwsoft.editor.renderer.SceneLoader;

/**
 * Created by vilddjur on 2015-04-02.
 */
public class Level extends Stage{
    /**
     * SceneLoader, as I understand it, simply loads all the relevant resources in assets
     * when we call loadScene we can pass different strings as a argument so if you want to
     * load a new scene we simply need to name it like: "IceLevel", "ForestLevel", "Fuck Boking" and so on.
     *
     * Since this is a stage we can simply fetch level later and use it as a screen, sl.getRoot() gives us all the
     * actors and their state (background, foreground, particles, animations, etc)
     *
     * Later we should probably refactor this class as a LevelHandler which has a private Stage stage
     * through this we can easily call levelHandler.setStage("LevelAlldensMamma") and that method simply
     * reloads the scene, clears the Stage and loads the Actors.
     */
    public Level(){
        SceneLoader sl = new SceneLoader();

        sl.loadScene("MainScene");

        this.addActor(sl.getRoot());
    }
}
