package com.technohest.assets.attacks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 *  A class for representing a particle.
 *  @author Tobias Alldén
 *  @version 1.0
 */
public class Projectile extends Attack {
    private Body body;
    private Shape shape;
    private float elapsedTime;
    private float duration;

    public Projectile(int damage, Shape shape, Vector2 position, World world,Vector2 velocity) {
        super(damage,shape,position);
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(position);
        body = world.createBody(bdef);
        body.setGravityScale(1);

        this.shape = shape;
        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        body.createFixture(fDef).setUserData("Projectile");
        body.setLinearVelocity(velocity);
    }

    public void render(float delta, World world) {

    }

    @Override
    public Shape getShape() {
        return shape;
    }

    public Body getBody() {
        return body;
    }
}
