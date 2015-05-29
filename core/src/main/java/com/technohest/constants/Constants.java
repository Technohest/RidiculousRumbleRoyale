package com.technohest.constants;

/**
 * A class for all the constants used in the game.
 * @author Oskar Jedvert
 * @author Tobias Alldén
 * @author David Ström
 */
public class Constants {

    //Scale, Pixels per Meter
    public static final float PPM = 32;
    public static final float GRAVITY = -9.82f;
    public static final float RESPAWN_TIME = 3;


    //Character specific variables
    public static final int JUMP_FORCE_MULTIPLIER = 2500;
    public static final float PLAYER_HEIGHT = 16;
    public static final float PLAYER_WIDTH = 10;
    public static final int INITIAL_MOVEMENT_SPEED =5;
    public static final int BASE_ATTACK_DMG = 20;
    public static final int SPECIAL_ATTACK_DMG = 15;

    //Network constants
    public static final int NUMBER_OF_PLAYERS = 1;
}
