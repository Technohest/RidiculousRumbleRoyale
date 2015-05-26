package com.technohest.LibgdxService;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.technohest.constants.Constants;
import com.technohest.core.model.Attack;
import com.technohest.core.model.Character;

/**
 * A class for handling collition.
 * @author Tobias Alldén
 * @version 0.1
 */
public class CollisionHandler implements ContactListener {
    private GameLogicGDX gameLogic;



    public CollisionHandler(GameLogicGDX gameLogic) {
        this.gameLogic = gameLogic;
    }


    @Override
    public void beginContact(Contact contact) {

        //Attack collision
        if(contact.getFixtureA().getBody().getUserData().equals("baseAttack")) {
            if(contact.getFixtureB().getUserData().equals("Level")) {
                gameLogic.setAttackInpacted(gameLogic.getBodyFromAttackId((Integer)contact.getFixtureA().getUserData()));
            } else if(contact.getFixtureB().getBody().getUserData().equals("Player")) {
                if(contact.getFixtureA().getUserData() != contact.getFixtureB().getUserData()) {
                    Integer attackId = ((Integer) contact.getFixtureA().getUserData());
                    gameLogic.setDamageDealtToPlayer(attackId, Constants.BASE_ATTACK_DMG);
                }

            }
        }

        if(contact.getFixtureB().getBody().getUserData().equals("baseAttack")) {
            if(contact.getFixtureA().getUserData().equals("Level")) {
                gameLogic.setAttackInpacted(gameLogic.getBodyFromAttackId((Integer)contact.getFixtureB().getUserData()));
            }else if(contact.getFixtureA().getBody().getUserData().equals("Player")) {
                if(contact.getFixtureA().getUserData() != contact.getFixtureB().getUserData()) {
                    Integer attackId = ((Integer) contact.getFixtureB().getUserData());
                    gameLogic.setDamageDealtToPlayer(attackId, Constants.BASE_ATTACK_DMG);
                }
            }

        }

        if(contact.getFixtureA().getBody().getUserData().equals("specialAttack")) {
            if(contact.getFixtureB().getUserData().equals("Level")) {
                Integer attackId = ((Integer)contact.getFixtureA().getUserData());
                gameLogic.setAttackInpacted(gameLogic.getBodyFromAttackId((Integer)contact.getFixtureA().getUserData()));
            } else if(contact.getFixtureB().getBody().getUserData().equals("Player")) {
                if(contact.getFixtureA().getUserData() != contact.getFixtureB().getUserData()) {
                    Integer attackId = ((Integer) contact.getFixtureA().getUserData());
                    gameLogic.setDamageDealtToPlayer(attackId, Constants.SPECIAL_ATTACK_DMG);
                }

            }

        }

        if(contact.getFixtureB().getBody().getUserData().equals("specialAttack")) {
            if(contact.getFixtureA().getUserData().equals("Level")) {
                gameLogic.setAttackInpacted(gameLogic.getBodyFromAttackId((Integer)contact.getFixtureB().getUserData()));
            }else if(contact.getFixtureA().getBody().getUserData().equals("Player")) {
                if(contact.getFixtureA().getUserData() != contact.getFixtureB().getUserData()) {
                    Integer attackId = ((Integer) contact.getFixtureB().getUserData());
                    gameLogic.setDamageDealtToPlayer(attackId, Constants.SPECIAL_ATTACK_DMG);
                }

            }

        }




    }

    @Override
    public void endContact(Contact contact) {
        //Feet detection
        if(contact.getFixtureA().getUserData() instanceof Body && contact.getFixtureB().getUserData().equals("Level"))  {
            Body b = ((Body)contact.getFixtureB().getUserData());

        } else if(contact.getFixtureB().getUserData() instanceof Body && contact.getFixtureA().getUserData().equals("Level")) {
            Body b = ((Body)contact.getFixtureB().getUserData());

        }

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
