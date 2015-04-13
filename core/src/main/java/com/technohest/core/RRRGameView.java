package com.technohest.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class RRRGameView implements Screen {
	private final RRRGameController controller;

	private TiledMap 			levelMap;
	private TiledMapRenderer 	mapRenderer;

	private OrthographicCamera 	camera;
	/**
	 * Initialize the Game, calling controller.getLevel which calls model.getLevel
	 * @param controller
	 * The controller which will be used to check the validity of requests and relaying the requests to the model
	 */
	public RRRGameView(RRRGameController controller) {
		this.controller = controller;
		this.levelMap = controller.getLevel();
		mapRenderer = new OrthogonalTiledMapRenderer(levelMap);
		camera = new OrthographicCamera();
		camera.setToOrtho(false,1280,720);
		camera.update();
	}

	@Override
	public void render(float v) {
		getInput();

		float r = 9 / 255.0f;
		float g = 205 / 255.0f;
		float b = 218 / 255.0f;
		Gdx.gl.glClearColor(r, g, b, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		camera.update();
		mapRenderer.setView(camera);
		mapRenderer.render();
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

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
	private void getInput(){
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
			ScreenHandler.getInstance().setScreen("menu");
		}
	}
}
