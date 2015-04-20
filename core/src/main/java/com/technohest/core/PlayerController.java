package com.technohest.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.SpriteAnimation;
import com.uwsoft.editor.renderer.physics.PhysicsBodyLoader;
import com.uwsoft.editor.renderer.script.IScript;

/**
 * A controller for the Player.
 * @author Tobias Alldén
 * @version 1.0
 */
public class PlayerController implements IScript {
    private Character character;

    // Keep GameStage to access world later
    private Overlap2DStage stage;

    //Player
    private CompositeItem item;

    // Player Animation
    private SpriteAnimation animation;

    private boolean isWalking = false;
    private boolean isGrounded = false;

    private float moveSpeed = 170f;
    private float gravity = -800f;
    private float verticalSpeed = 0;

    public PlayerController(Overlap2DStage stage) {
        this.stage = stage;
    }
    @Override
    public void init(CompositeItem compositeItem) {
        this.item = item;
        animation = item.getSpriteAnimationById("animation");

        animation.pause();

        // Setting item origin at the center
        item.setOrigin(item.getWidth()/2, 0);

    }

    @Override
    public void dispose() {

    }

    @Override
    public void act(float delta) {
        // Check for control events

        boolean wasWalking = isWalking;

        isWalking = false;
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            // Go right
            item.setX(item.getX() + delta*moveSpeed);
            item.setScaleX(1f);
            isWalking = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            // Go left
            item.setX(item.getX() - delta*moveSpeed);
            item.setScaleX(-1f);
            isWalking = true;
        }

        if(wasWalking == true && isWalking == false) {
            // Not walking anymore stop the animation
            animation.pause();
        }
        if(wasWalking == false && isWalking == true) {
            // just started to walk, start animation
            animation.start();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            // Jump
            if(isGrounded) {
                verticalSpeed = 460f;
                isGrounded = false;
            }
        }

        // Gravity
        verticalSpeed += gravity*delta;

        // ray-casting for collision detection
        checkForCollisions();

        // set the position
        item.setY(item.getY() + verticalSpeed*delta);


    }
    /*
Ray cast down, and if collision is happening stop player and reposition to closest point of collision
 */
    private void checkForCollisions() {
        // Ray size is the exact size of the deltaY change we plan for this frame
        float raySize = -(verticalSpeed+Gdx.graphics.getDeltaTime())*Gdx.graphics.getDeltaTime();

        // only check for collisions when moving down
        if(verticalSpeed >= 0) return;

        // Vectors of ray from middle bottom
        Vector2 rayFrom = new Vector2((item.getX()+item.getWidth()/2)* PhysicsBodyLoader.SCALE, item.getY()*PhysicsBodyLoader.SCALE);
        Vector2 rayTo = new Vector2((item.getX()+item.getWidth()/2)*PhysicsBodyLoader.SCALE, (item.getY() - raySize)*PhysicsBodyLoader.SCALE);

        // Cast the ray
        stage.getWorld().rayCast(new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                // Stop the player
                verticalSpeed = 0;

                // reposition player slightly upper the collision point
                item.setY(point.y / PhysicsBodyLoader.SCALE + 0.1f);

                // make sure it is grounded, to allow jumping again
                isGrounded = true;

                return 0;
            }
        }, rayFrom, rayTo);
    }
}
