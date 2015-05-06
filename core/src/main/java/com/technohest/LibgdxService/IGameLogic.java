package com.technohest.LibgdxService;

import com.technohest.core.model.*;
import com.technohest.core.model.Character;

import java.util.ArrayList;

/**
 * Created by oskar on 2015-05-01.
 */
public interface IGameLogic {
    /**
     * Updates the game logic
     * @param v
     * Delta time since last call
     */
    void update(float v);

    /**
     * Generate bodies
     * @param level
     * Target level
     * @param players
     */
    void generate(ILevel level, ArrayList<Character> players);
}
