package com.technohest.core.model;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.technohest.constants.Constants;

/**
 *  A abstract class for dealing damage.
 *  @author Tobias Alldén
 *  @version 1.0
 */
public abstract class Attack  {
    private int damage;
    private Vector2 postition;
    private Shape shape;



    public Attack() {

    }

    public Attack(int damage, Shape shape, Vector2 position) {
        this.damage = damage;
        this.shape = shape;
        position = (new Vector2(position.x + 10/Constants.PPM,position.y));
        }

    public int getDamage() {
        return damage;
    }

    public Vector2 getPostition() {
        return postition;
    }

    public Shape getShape() {
        return shape;
    }
}