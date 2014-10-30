package com.davebilotta.thecity;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.davebilotta.thecity.Person.Gender;
import com.badlogic.gdx.math.MathUtils;

public class GameScreen implements Screen, InputProcessor {

	TheCity game;
	SpriteBatch batch;
	Texture red_dot, orange_dot, yellow_dot, green_dot, blue_dot, white_dot;

	OrthographicCamera camera;
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;

	int tileSize, mapWidth, mapHeight;
	int tileX, tileY;
	int renderWidth, renderHeight; // # of tiles in either direction
	
	float screenWidth,screenHeight;

	public GameScreen(TheCity game) {
		this.game = game;
		this.batch = new SpriteBatch();

		// TODO: Eventually move to assets class
		red_dot = new Texture("red_dot.png");
		orange_dot = new Texture("orange_dot.png");
		yellow_dot = new Texture("yellow_dot.png");
		green_dot = new Texture("green_dot.png");
		blue_dot = new Texture("blue_dot.png");
		white_dot = new Texture("white_dot.png");
		
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, screenWidth,screenHeight);

		boolean largeMap = false;
		if (largeMap) { 
			tiledMap = new TmxMapLoader().load("tilemaps/level1.tmx");
			mapWidth = mapHeight = 250;
		} 
		else {
			tiledMap = new TmxMapLoader().load("tilemaps/level3.tmx");
			mapWidth = mapHeight = 20;
		}
		
		renderWidth = 16;      // 1024px
		renderHeight = 12;     // 768px
		tileSize = 64;
		
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		Gdx.input.setInputProcessor(this);
	
		tileX = (mapWidth - renderWidth) * tileSize;
		tileY = (mapHeight - renderHeight) * tileSize;
				
        camera.position.x = tileX + (tileSize * renderWidth) / 2;
        camera.position.y = tileY + (tileSize * renderHeight) / 2;
        camera.update();
	}

	@Override
	public void render(float delta) {
		game.update(delta);

		Gdx.gl20.glClearColor(0, 0, 0, 1f);
		Gdx.gl20.glEnable(GL20.GL_BLEND);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT); // clear the buffer
	
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		renderCitizens(delta);

	}

	public void renderCitizens(float delta) {
		Iterator<Person> i = this.game.city.citizens.iterator();
		Person p;
		Texture texture;

		batch.begin();
		while (i.hasNext()) {
			p = i.next();

			if (p.getGender() == Gender.MALE)
				texture = blue_dot;
			else {
				if (p.fertile)
					texture = green_dot;
				else
					texture = red_dot;
			}

			batch.draw(texture, p.position.x, p.position.y);

		}

		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		
	}

	// InputProcessor methods
	
	@Override
		public boolean keyDown(int keycode) {

			switch (keycode) {
			case Input.Keys.LEFT:
				if (tileX > 0) {
				tileX-=tileSize;
				}
				break;

			case Input.Keys.RIGHT:
				if (tileX < ((mapWidth - renderWidth) * tileSize )) {
				tileX+=tileSize;
				}
				break;

			case Input.Keys.DOWN:
				if (tileY > 0) { 
					tileY-=tileSize;
				}
				break;

			case Input.Keys.UP:
				if (tileY < ((mapHeight - renderHeight) * tileSize )) {
					tileY+=tileSize;
				}
				break;

		}

			float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
	        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

	        camera.position.x = tileX + (tileSize * renderWidth) / 2;
	        camera.position.y = tileY + (tileSize * renderHeight) / 2;
	        
			return false;
		}

	@Override
	public boolean keyUp(int keycode) {
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		batch.dispose();
		
		// TODO: Dispose of assets
		// Assets.dispose();
	}
}
