package com.technohest.core.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import com.technohest.constants.Constants;
import com.technohest.core.controller.RRRGameController;
import com.technohest.core.model.RRRGameModel;

public class RRRGameView implements Screen {

	private final RRRGameController controller;
	private final RRRGameModel		model;

	private TiledMapRenderer 	mapRenderer;

	private SpriteBatch			batch;

	private OrthographicCamera 	camera;


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
		camera.setToOrtho(false, Constants.DEF_WIDTH, Constants.DEF_HEIGHT);


	}

	@Override
	public void render(float v) {
		controller.update(v);
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
