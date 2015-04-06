package com.technohest.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class RRRGameView implements Screen {
	private final RRRGameController controller;
	private final RRRMain		game;

	private Stage 				stage;

	/**
	 * Initialize the Game, calling controller.getStage which calls model.getStage
	 * @param controller
	 * The controller which will be used to check the validity of requests and relaying the requests to the model
	 * @param game
	 * game is used to call switchTo when we want to go to menu
	 */
	public RRRGameView(RRRGameController controller, RRRMain game) {
		this.game = game;
		this.controller = controller;
		this.stage = controller.getStage();
	}

	@Override
	public void render(float v) {
		getInput();

		float r = 9 / 255.0f;
		float g = 205 / 255.0f;
		float b = 218 / 255.0f;
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
	private void getInput(){
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
			game.switchTo("menu");
		}
	}
}
