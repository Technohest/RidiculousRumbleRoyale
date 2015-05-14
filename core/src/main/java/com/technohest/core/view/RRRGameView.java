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
import com.technohest.constants.Constants;
import com.technohest.core.controller.RRRGameController;
import com.technohest.core.model.*;
import com.technohest.core.model.Character;

import java.util.ArrayList;
import java.util.Collection;

public class RRRGameView implements Screen {

	private RRRGameController controller = null;
	private final RRRGameModel		model;

	private TiledMapRenderer 	mapRenderer;

	private SpriteBatch			batch;

	private OrthographicCamera 	camera;

    //Activate for debugrenderer
    /*
    private Box2DDebugRenderer drenderer;
    private Matrix4 dmatrix;
    */
    ShapeRenderer sRenderer;


	/**
	 * Initialize the Game, calling controller.getLevel which calls model.getLevel
	 * @param controller
	 * The controller which will be used to check the validity of requests and relaying the requests to the model
	 */
	public RRRGameView(RRRGameController controller, RRRGameModel model) {

		this.model = model;
		this.controller = controller;
		controller.setView(this);
		mapRenderer = new OrthogonalTiledMapRenderer(controller.getLevel().getMap());
		batch = new SpriteBatch();

		/**
		 * camera is used to draw the tiles
		 * box2dcam is used to "draw" all bodies
		 */
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
        sRenderer  = new ShapeRenderer();
        camera.setToOrtho(false, Constants.DEF_WIDTH, Constants.DEF_HEIGHT);

        //Activate for debugrenderer
        /*
        drenderer = new Box2DDebugRenderer();
        dmatrix = new Matrix4(camera.combined);
        dmatrix.scale(32,32,1f);
        */

	}

	public RRRGameView(RRRGameModel model) {
		this.model = model;
		mapRenderer = new OrthogonalTiledMapRenderer(model.getLevel().getMap());
		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		sRenderer = new ShapeRenderer();
		camera.setToOrtho(false, Constants.DEF_WIDTH, Constants.DEF_HEIGHT);
	}

	@Override
	public void render(float v) {

		if (controller != null)
			controller.update(v);
        sRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //Draws players ---  TEMP!
        Collection<Character> players = model.getPlayers();
		for (Character c: players) {
            Body b = ((GameLogicGDX)model.getGameLogic()).getBodyFromCharacter(c);
			if (b != null) {
				sRenderer.rect(((b.getPosition().x - (0.35f)) * 32), ((b.getPosition().y - (0.5f)) * 32), 20, 32);
			} else {
				System.out.println("DAYUM SON");
			}
        }
        //Activate for debugrenderer
       // drenderer.render(((GameLogicGDX)model.getGameLogic()).getWorld(),dmatrix);
        sRenderer.end();
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
