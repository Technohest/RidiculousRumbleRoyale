package com.technohest.core;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.technohest.characters.Character;
import com.technohest.characters.CharacterView;
import com.technohest.core.Constants.*;

/**
 * A controller for the player.
 * @author Tobias Alldén
 * @version 1.0
 */
public class PlayerController implements ContactListener {
    private Character player;
    private CharacterView view;

    public PlayerController(Character player) {
        this.player = player;
        this.view =  new CharacterView(player);
    }
    public void drawPlayer(World world) {
        view.render(world);
    }
    public void handleInput(InputHandler handler) {
        if (handler.isPressed(InputHandler.RIGHT)) {
            view.getBody().setLinearVelocity(new Vector2(10,Constants.GRAVITY));
        } if(handler.isPressed(InputHandler.LEFT)) {
            view.getBody().setLinearVelocity(new Vector2(-10, Constants.GRAVITY));
        }
        if(handler.isPressed(InputHandler.JUMP)) {
            if(player.isGrounded()) {
                view.getBody().setLinearVelocity(new Vector2(0,100));
            } else {

            }
        }
    }

    public void update(float delta) {
        player.update(delta);
    }


    @Override
    public void beginContact(Contact contact) {
        if(contact.getFixtureA().getUserData() == "Player" ||contact.getFixtureB().getUserData() == "Player") {
            player.setGrounded(true);
        }


    }

    @Override
    public void endContact(Contact contact) {
        if(contact.getFixtureA().getUserData() == "Player" ||contact.getFixtureB().getUserData() == "Player") {
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
