package com.technohest.core.model;
import com.technohest.constants.Constants;

/**
 *  A abstract class for dealing damage.
 *  @author Tobias Alldén
 *  @version 1.0
 */
public abstract class Attack  {
    private int damage;
    private String name;



    public Attack() {

    }

    public Attack(String name,int damage) {
        this.damage = damage;
        this.name = name;
        }

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }
}