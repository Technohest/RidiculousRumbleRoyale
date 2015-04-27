package com.technohest.core.controller;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.technohest.assets.characters.Character;
import com.technohest.assets.characters.CharacterView;
import com.technohest.constants.Constants;
import com.technohest.core.handlers.InputHandler;

/**
 * A controller for the player.
 * @author Tobias Alldén
 * @version 1.1
 */
public class PlayerController implements ContactListener {
    private Character player;
    private CharacterView view;

    public PlayerController(Character player) {
        this.player = player;
        this.view =  new CharacterView(player);
    }
    public void drawPlayer(World world, SpriteBatch batch) {
        view.render(world, batch);
    }
    public void handleInput(InputHandler handler) {
        if (handler.isPressed(InputHandler.RIGHT)) {
            view.getBody().setLinearVelocity(Constants.MOVEMENT_SPEED,view.getBody().getLinearVelocity().y);
            player.setIsFacingRight(true);
        } if(handler.isPressed(InputHandler.LEFT)) {
            view.getBody().setLinearVelocity(-Constants.MOVEMENT_SPEED,view.getBody().getLinearVelocity().y);
            player.setIsFacingRight(false);
        }
        if(handler.isPressed(InputHandler.JUMP)) {
            if(player.isGrounded()) {
                   view.getBody().applyForceToCenter(0,view.getBody().getMass()*Constants.JUMP_FORCE_MULTIPLIER,true);
                }
        }
    }

    public void update(float delta) {
        player.update(delta);
    }


    @Override
    public void beginContact(Contact contact) {
        if(contact.getFixtureA().getUserData() == "foot"
                || contact.getFixtureB().getUserData() == "foot") {
            player.setGrounded(true);
        }


    }

    @Override
    public void endContact(Contact contact) {
        if(contact.getFixtureA().getUserData() == "foot"
                || contact.getFixtureB().getUserData() == "foot") {
            player.setGrounded(false);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
