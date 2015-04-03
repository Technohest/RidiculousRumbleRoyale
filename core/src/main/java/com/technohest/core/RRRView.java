package com.technohest.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class RRRView implements Screen {
	private final RRRController controller;
	private final RRRMain		game;

	private Stage 				stage;

	/**
	 * Initialize the Game, calling controller.getStage which calls model.getStage
	 * @param controller
	 * @param game
	 */
	public RRRView (RRRController controller, RRRMain game) {
		this.game = game;
		this.controller = controller;
		this.stage = controller.getStage();
	}

	@Override
	public void render(float v) {
		float r = (float) (9 / 255.0);
		float g = (float) (205 / 255.0);
		float b = (float) (218 / 255.0);

		Gdx.gl.glClearColor(r, g, b, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
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
}
