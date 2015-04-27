package com.technohest.core.model;
import static com.technohest.constants.Constants.PPM;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.technohest.core.model.Attack;
import com.technohest.constants.Constants;

/**
 * A template for characters.
 * @author Tobias Alldén
 * @version 1.1
 */
public  class Character {


    //State of character,may be reimplemented in future versions.
    public enum State {
        Standing,Running,Jumping,Falling
    }

    //Variables
    private Vector2 position,velocity,acceleration;
    private State state;
    private Attack baseAttack,specialAttack;
    private boolean isFacingRight;
    private boolean grounded;
    private float height;
    private float width;
    private float stateTime;
    private Body body;
    private PolygonShape shape;
    private MassData playerMass;
    private World world;

    //Game specific variables;
    private int healthPoints;
    private int kills;
    private int deaths;

    /**
     * Creates a character.
     * @param world - The world in wich the character will exist.
     * @param position - Starting position for character.
     * @param height - height of the character.
     * @param width - Width of the character.
     * @param mass - Character mass
     */
    public Character(World world, Vector2 position, float width,float height,int mass) {
        this.world= world;
        this.position = position;
        this.healthPoints = 100;
        this.kills = 0;
        this.deaths = 0;
        this.width = width;
        this.height = height;
        this.playerMass = new MassData();
        acceleration = new Vector2(460,0);
        playerMass.mass = mass;
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.gravityScale = Constants.GRAVITY_SCALE;
        bdef.position.set(position);
        bdef.linearDamping = 10;
        body = world.createBody(bdef);
        body.setMassData(playerMass);
        body.setLinearVelocity(0,0);


        shape = new PolygonShape();
        shape.setAsBox(width / Constants.PPM, height/ Constants.PPM);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        body.createFixture(fdef).setUserData("Player");

        //Foot sensors
        shape.setAsBox((width/4)/Constants.PPM,(width/4)/Constants.PPM, new Vector2(((width/2)-(width/8))/PPM,-(height-width/4)/PPM),0);
        fdef.shape = shape;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("PlayerFoot");
    }

    public void move(Vector2 direction) {
        this.body.setLinearVelocity(direction);
    }
    public void jump() {
        if(isGrounded()) {
            body.applyForceToCenter(0, getPlayerMass() * Constants.JUMP_FORCE_MULTIPLIER, true);
            this.state = State.Jumping;
        }
    }

    /**
     * Updates the character.
     */
    public void update(float delta) {
        stateTime += delta;
        if(!isGrounded()) {
            this.state = State.Falling;
        } else if(velocity.x > 0) {
            if(velocity.x < Constants.MAX_SPEED) {
                velocity.add(acceleration.cpy().scl(delta));
            }
             this.state = State.Running;
        } else {
            state = State.Standing;
        }
    }

    /**
     * When character dies, redraws and increments deaths
     */
    public void die() {
        //To be implemented further in future versions,
        this.deaths++;
    }

    /**
     * When chararcter kills another character, increments kills.
     */
    public void incrementKill() {
        kills++;
    }

    //***************************************GETTERS/SETTERS***********************************************//


    public Vector2 getPosition() {
        return position;
    }
    public float getX() {return position.x;}
    public float getY() {return position.y;}
    public void setX(float x) {position.x = x;}
    public void setY(float y) {position.y = y;}
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Attack getBaseAttack() {
        return baseAttack;
    }

    public void setBaseAttack(Attack baseAttack) {
        this.baseAttack = baseAttack;
    }

    public Attack getSpecialAttack() {
        return specialAttack;
    }

    public void setSpecialAttack(Attack specialAttack) {
        this.specialAttack = specialAttack;
    }

    public boolean isFacingRight() {
        return isFacingRight;
    }

    public void setIsFacingRight(boolean isFacingRight) {
        this.isFacingRight = isFacingRight;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public Body getBody() {
        return body;
    }

    public PolygonShape getShape() {
        return shape;
    }

    public float getPlayerMass() {
        return playerMass.mass;
    }

}
