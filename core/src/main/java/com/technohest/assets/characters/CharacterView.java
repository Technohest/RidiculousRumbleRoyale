package com.technohest.assets.characters;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.technohest.constants.Constants.PPM;

/**
 * The view for the character.
 * @author Tobias Alldén
 * @version 1.0
 */
public class CharacterView {
    BodyDef bdef;
    FixtureDef fdef;
    Character player;
    Body body;
    PolygonShape shape;
    MassData playerMass;
    public CharacterView(Character player) {
        bdef = new BodyDef();
        fdef = new FixtureDef();
        this.player = player;
    }

    public void render(World world, SpriteBatch batch) {
        if(body == null) {
            bdef.position.set(player.getPosition());
            playerMass = new MassData();
            playerMass.mass = 50;
            bdef.type = BodyDef.BodyType.DynamicBody;
            body = world.createBody(bdef);
            body.setMassData(playerMass);
            body.setLinearVelocity(0,0);
            body.setLinearDamping(10);
            body.setGravityScale(10);





        } else {
            player.setPosition(body.getPosition());
            player.getSprite(body.getLinearVelocity().x > 0).draw(batch);
        }

        if(shape == null) {
            shape = new PolygonShape();
            shape.setAsBox(20 / PPM, 30 / PPM);
            fdef.shape = shape;
            body.createFixture(fdef).setUserData("Player");

            //Foot sensors
            //right
            shape.setAsBox(5 / PPM, 5/ PPM, new Vector2( 10/PPM, -30/PPM),0);
            fdef.shape = shape;
            fdef.isSensor = true;
            body.createFixture(fdef).setUserData("foot");

            //left
            shape.setAsBox(5 / PPM, 5/ PPM, new Vector2( -10/PPM, -30/PPM),0);
            fdef.shape = shape;
            fdef.isSensor = true;
            body.createFixture(fdef).setUserData("foot");
        }

    }
    public Body getBody() {
        return this.body;
    }



}
