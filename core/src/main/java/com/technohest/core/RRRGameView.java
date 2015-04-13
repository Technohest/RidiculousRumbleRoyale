package com.technohest.core;

import static com.technohest.core.Constants.PPM;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class RRRGameView implements Screen {
	private final RRRGameController controller;

	private TiledMap 			levelMap;
	private TiledMapRenderer 	mapRenderer;

	private World				world;
	private	float				tileSize;

	private OrthographicCamera 	camera;
	private OrthographicCamera 	box2dcam;

	private Box2DDebugRenderer b2dr;
	/**
	 * Initialize the Game, calling controller.getLevel which calls model.getLevel
	 * @param controller
	 * The controller which will be used to check the validity of requests and relaying the requests to the model
	 */
	public RRRGameView(RRRGameController controller) {
		b2dr  = new Box2DDebugRenderer();

		this.controller = controller;
		this.levelMap = controller.getLevel();
		mapRenderer = new OrthogonalTiledMapRenderer(levelMap);
		/**
		 * Initialize the box2d world with -9,82 gravity
		 */
		world = new World(new Vector2(0,-9.82f), true);

		createTiles(world, levelMap);
		/**
		 * camera is used to draw the tiles
		 * box2dcam is used to "draw" all bodies
		 */
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);

		box2dcam = new OrthographicCamera();
		box2dcam.setToOrtho(false, 1280 / PPM, 720 / PPM);
	}

	/**
	 * Initiatlizes all tiles with their corresponding box2d bodies
	 * @param world
	 * The world to put the bodies in
	 * @param levelMap
	 * The level where we get the tiles from
	 */
	private void createTiles(World world, TiledMap levelMap) {
		TiledMapTileLayer layer = (TiledMapTileLayer) levelMap.getLayers().get("Foreground");
		/**
		 * BodyDef and FixtureDef is used as temporary models which can later be added to a body
		 */
		tileSize = layer.getTileWidth();
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		/**
		 * Create a box2d body for each tile
		 */
		for(int r = 0; r < layer.getHeight(); r++){
			for(int c = 0; c < layer.getWidth(); c++){
				Cell cell = layer.getCell(c,r);
				/**
				 * If there is a tile on (r,w)
				 */
				if(cell != null && cell.getTile() != null){
					bdef.type = BodyType.StaticBody;
					bdef.position.set(
							(c + 0.5f) * tileSize / PPM,
							(r + 0.5f) * tileSize / PPM
					);
					/**
					 * Create a "Chain Linked" polygon of corners which will check for collision later. Using this chainlink will allow our players to jump up into the box
					 * this can be changed easily by adding more items to the vector, e.g a bottom.
					 */
					ChainShape cs = new ChainShape();
					Vector2[] v = new Vector2[3];
					v[0] = new Vector2(-tileSize/2/PPM, -tileSize/2/PPM);
					v[1] = new Vector2(-tileSize/2/PPM, tileSize/2/PPM);
					v[2] = new Vector2(tileSize/2/PPM, tileSize/2/PPM);

					cs.createChain(v);
					fdef.friction = 0;
					fdef.shape = cs;
					fdef.filter.categoryBits = 1;
					fdef.filter.maskBits = -1;
					fdef.isSensor = false;
					world.createBody(bdef).createFixture(fdef);
				}
			}
		}
		/**
		 * Create a small falling box just used for debugging
		 */
		bdef.position.set(160/PPM,250/PPM);
		bdef.type = BodyType.DynamicBody;
		Body body = world.createBody(bdef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(5/PPM,5/PPM);
		fdef.shape = shape;
		body.createFixture(fdef);
	}

	@Override
	public void render(float v) {
		update(v);

		float r = 9 / 255.0f;
		float g = 205 / 255.0f;
		float b = 218 / 255.0f;
		Gdx.gl.glClearColor(r, g, b, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		mapRenderer.setView(camera);
		mapRenderer.render();
		b2dr.render(world, box2dcam.combined);
	}

	private void update(float v) {
		getInput();
		world.step(v,6,2);
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
