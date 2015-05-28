package com.technohest.core.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.technohest.LibgdxService.GameLogicGDX;
import com.technohest.constants.Settings;
import com.technohest.core.controller.RRRGameController;
import com.technohest.core.model.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The class managing the drawing of the application.
 * @author Oskar Jedvert
 * @author Tobias Alldén */

public class RRRGameView implements Screen {

	private RRRGameController controller = null;
	private final RRRGameModel		model;
	private TiledMapRenderer 	mapRenderer;
	private SpriteBatch batch;
	private OrthographicCamera camera;
    private Box2DDebugRenderer drenderer;
    private Matrix4 dmatrix;
    //Activate for debug
    public boolean isDebugging = false;
    ShapeRenderer sRenderer;
	Settings settings;

	/**
	 * Initialize the Game, calling controller.getLevel which calls model.getLevel
	 * @param controller
	 * The controller which will be used to check the validity of requests and relaying the requests to the model
	 */
	public RRRGameView(RRRGameController controller, RRRGameModel model) {
		settings = new Settings();
		this.model = model;
		this.controller = controller;
		controller.setView(this);
		mapRenderer = new OrthogonalTiledMapRenderer(controller.getLevel().getMap());
		batch = new SpriteBatch();
		settings = new Settings();
		/**
		 * camera is used to draw the tiles
		 * box2dcam is used to "draw" all bodies
		 */
		camera = new OrthographicCamera();
        sRenderer  = new ShapeRenderer();
		camera.setToOrtho(false, settings.getWidth(), settings.getHeight());
		mapRenderer.setView(camera);
        if(isDebugging) {
            drenderer = new Box2DDebugRenderer();
            dmatrix = new Matrix4(camera.combined);
            dmatrix.scale(32, 32, 1f);
        }

	}

	public RRRGameView(RRRGameModel model) {
		settings = new Settings();
		this.model = model;
		mapRenderer = new OrthogonalTiledMapRenderer(model.getGameLogic().getLevel().getMap());
		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		sRenderer = new ShapeRenderer();

		camera.setToOrtho(false, settings.getWidth(), settings.getHeight());
		mapRenderer.setView(camera);
	}

	@Override
	public void render(float v) {
		if (controller != null) {
			controller.update(v);
		}
        sRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //Draw players
        Collection<Integer> players = model.getAlivePlayersId();
		for (Integer i: players) {
			Body b = ((GameLogicGDX) model.getGameLogic()).getBodyFromplayerId(i);
			sRenderer.rect(((b.getPosition().x - (0.35f)) * 32), ((b.getPosition().y - (0.5f)) * 32), 20, 32);
		}

        //Draws projectiles
        ArrayList<Attack> activeAttacks = model.getActiveAttacks();
        for(Attack attack: activeAttacks) {
                if (attack instanceof Projectile) {
                    Body b = ((GameLogicGDX)model.getGameLogic()).getBodyFromAttackId(attack.getSourcePlayerId());
                    sRenderer.circle(((b.getPosition().x) * 32), ((b.getPosition().y) * 32), 5);
                }
        }

        sRenderer.end();
        if(isDebugging) {
            drenderer.render(((GameLogicGDX) model.getGameLogic()).getWorld(), dmatrix);

        }
	}
	public void update(float v){
		float r = 9 / 255.0f;
		float g = 205 / 255.0f;
		float b = 218 / 255.0f;
		Gdx.gl.glClearColor(r, g, b, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		mapRenderer.setView(camera);
		mapRenderer.render();
		batch.begin();
		batch.end();
	}
	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void show() {
		if (controller != null)
        	Gdx.input.setInputProcessor(controller);
    }

	@Override
	public void hide() {
        Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
	}
}
