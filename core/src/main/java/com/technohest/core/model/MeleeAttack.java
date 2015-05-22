package com.technohest.core.model;

/**
 * A class representing a melee Attack.
 * @author Tobias Alldén
 */
public class MeleeAttack extends Attack {
    public MeleeAttack(String name,int sourceplayerId,int damage,float duration) {
        super(name,sourceplayerId,damage,duration);
    }
    public MeleeAttack() {

    }

}
