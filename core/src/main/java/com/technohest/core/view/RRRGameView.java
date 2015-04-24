package com.technohest.core.view;

import static com.technohest.constants.Constants.PPM;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.technohest.constants.Constants;
import com.technohest.core.controller.RRRGameController;
import com.technohest.core.handlers.InputHandler;
import com.technohest.core.model.RRRGameModel;

public class RRRGameView implements Screen {

	private final RRRGameController controller;
	private final RRRGameModel		model;

	private TiledMapRenderer 	mapRenderer;

	private SpriteBatch			batch;

	private OrthographicCamera 	camera;
	private OrthographicCamera 	box2dcam;


	/**
	 * Initialize the Game, calling controller.getLevel which calls model.getLevel
	 * @param controller
	 * The controller which will be used to check the validity of requests and relaying the requests to the model
	 */
	public RRRGameView(RRRGameController controller, RRRGameModel model) {

		this.model = model;
		this.controller = controller;

		mapRenderer = new OrthogonalTiledMapRenderer(controller.getLevel());
		batch = new SpriteBatch();

		model.generateWorld();
		/**
		 * camera is used to draw the tiles
		 * box2dcam is used to "draw" all bodies
		 */
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);

	}

	@Override
	public void render(float v) {
		controller.updateGame(v);

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