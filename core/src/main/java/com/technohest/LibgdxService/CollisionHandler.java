package com.technohest.LibgdxService;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
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
        //Feet detection
        if(contact.getFixtureA().getUserData() instanceof Body && contact.getFixtureB().getUserData().equals("Level"))  {
            Body b = ((Body)contact.getFixtureB().getUserData());
            gameLogic.getCharacterfromBody(b).setGrounded(true);

        } else if(contact.getFixtureB().getUserData() instanceof Body && contact.getFixtureA().getUserData().equals("Level")) {
           Body b = ((Body)contact.getFixtureB().getUserData());
            gameLogic.getCharacterfromBody(b).setGrounded(true);

        }

        //Attack detection
        if(contact.getFixtureA().getUserData() instanceof Attack && contact.getFixtureB().getUserData() instanceof Character)  {
            Character c = ((Character)contact.getFixtureB().getUserData());
            Attack attack = ((Attack)contact.getFixtureA().getUserData());
            if(gameLogic.model.getPlayerFromID(attack.getSourcePlayerId()) != c) {
                c.takeDamage(((Attack) contact.getFixtureA().getUserData()).getDamage());
                ((Attack)contact.getFixtureA().getUserData()).setInpacted(true);
                Gdx.app.log("Player: " + c.getId(), " took damage;" );
            }



        } else if(contact.getFixtureB().getUserData() instanceof Attack && contact.getFixtureA().getUserData() instanceof Character) {
            Character c = ((Character)contact.getFixtureA().getUserData());
            Attack attack = ((Attack)contact.getFixtureB().getUserData());
            if(gameLogic.model.getPlayerFromID(attack.getSourcePlayerId()) != c) {
                c.takeDamage(((Attack) contact.getFixtureB().getUserData()).getDamage());
                Gdx.app.log("Player: " + c.getId(), " took damage;");
                ((Attack)contact.getFixtureB().getUserData()).setInpacted(true);


            }

        }



        if(contact.getFixtureA().getUserData() instanceof Attack && contact.getFixtureB().getUserData().equals("Level"))  {
            ((Attack)contact.getFixtureA().getUserData()).setInpacted(true);



        } else if(contact.getFixtureB().getUserData() instanceof Attack && contact.getFixtureA().getUserData().equals("Level")) {
            ((Attack)contact.getFixtureB().getUserData()).setInpacted(true);



        }







    }

    @Override
    public void endContact(Contact contact) {
        //Feet detection
        if(contact.getFixtureA().getUserData() instanceof Body && contact.getFixtureB().getUserData().equals("Level"))  {
            Body b = ((Body)contact.getFixtureB().getUserData());
            gameLogic.getCharacterfromBody(b).setGrounded(false);

        } else if(contact.getFixtureB().getUserData() instanceof Body && contact.getFixtureA().getUserData().equals("Level")) {
            Body b = ((Body)contact.getFixtureB().getUserData());
            gameLogic.getCharacterfromBody(b).setGrounded(false);

        }

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
