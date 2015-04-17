package com.technohest.assets.characters;
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

    public void render(World world) {
        if(body == null) {
            bdef.position.set(player.getPosition());
            playerMass = new MassData();
            playerMass.mass = 50;
            bdef.type = BodyDef.BodyType.DynamicBody;
            body = world.createBody(bdef);
            body.setMassData(playerMass);

        } else {
            player.setPosition(body.getPosition());
            body.setLinearVelocity(player.getVelocity());
        }

        if(shape == null) {
            shape = new PolygonShape();
            shape.setAsBox(20 / PPM, 30 / PPM);
            fdef.shape = shape;
            body.createFixture(fdef).setUserData("Player");
        }

    }
    public Body getBody() {
        return this.body;
    }



}
