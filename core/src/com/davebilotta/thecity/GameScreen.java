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
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen, InputProcessor {

	TheCity game;
	SpriteBatch batch;
	Texture red_dot, orange_dot, yellow_dot, green_dot, blue_dot, white_dot;

	OrthographicCamera camera;
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;

	int tileSize, mapWidth, mapHeight, itemSize;
	int worldX, worldY;
	int renderWidth, renderHeight; // # of tiles in either direction

	float screenWidth, screenHeight;

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
		camera.setToOrtho(false, screenWidth, screenHeight);

		boolean largeMap = false;
		if (largeMap) {
			tiledMap = new TmxMapLoader().load("tilemaps/level1.tmx");
			mapWidth = mapHeight = 250;
		} else {
			tiledMap = new TmxMapLoader().load("tilemaps/level3.tmx");
			mapWidth = mapHeight = 20;
		}

		renderWidth = 16; // 1024px
		renderHeight = 12; // 768px
		tileSize = 64;
		itemSize = 32;

		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		Gdx.input.setInputProcessor(this);

		worldX = (mapWidth - renderWidth) * tileSize;
		worldY = (mapHeight - renderHeight) * tileSize;

		camera.position.x = worldX + (tileSize * renderWidth) / 2;
		camera.position.y = worldY + (tileSize * renderHeight) / 2;
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
		
		
		renderUIComponents();

	}

	public void renderCitizens(float delta) {
		Iterator<Person> i = this.game.city.citizens.iterator();
		Person p;
		Texture texture;

		batch.begin();

		// TODO: Only render those whose tileX and tileY are within the viewing
		// window
		while (i.hasNext()) {
			p = i.next();

			// figure out if in view
			if (inView(p)) {
				if (p.getGender() == Gender.MALE)
					texture = blue_dot;
				else {
					if (p.fertile)
						texture = green_dot;
					else
						texture = red_dot;
				}

				Vector2 screenPos = worldToScreen(p.getPosition());
				// TODO: need to factor item's size when rendering
				batch.draw(texture,screenPos.x,screenPos.y);
			}
		} // end while

		batch.end();
	}

	public boolean inView(GameObject item) {
		// position = position in world
		Vector2 position = item.getPosition();

		// right now this is only checking the x axis
		if (((position.x + itemSize/2)>= worldX) && (position.x <= (screenWidth + worldX))
		&& ((position.y + itemSize/2)>= worldY) && (position.y <= (screenHeight + worldY)) ) {
			
			return true;
		} else {

			return false;
		}
	}
	
	public Vector2 worldToScreen(Vector2 position) {
		return new Vector2(((position.x - worldX)),
				(( (position.y - worldY))) );
	}
	
	public Vector2 screenToWorld(float screenX, float screenY) {
		return new Vector2((screenX + worldX ),((screenHeight - screenY + worldY)));
	}

	
	public void renderUIComponents() {
		// render city information
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
			if (worldX > 0) {
				worldX -= tileSize;
			}
			break;

		case Input.Keys.RIGHT:
			if (worldX < ((mapWidth - renderWidth) * tileSize)) {
				worldX += tileSize;
			}
			break;

		case Input.Keys.DOWN:
			if (worldY > 0) {
				worldY -= tileSize;
			}
			break;

		case Input.Keys.UP:
			if (worldY < ((mapHeight - renderHeight) * tileSize)) {
				worldY += tileSize;
			}
			break;

		}

		float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
		float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

		camera.position.x = worldX + (tileSize * renderWidth) / 2;
		camera.position.y = worldY + (tileSize * renderHeight) / 2;

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
		Utils.log("touchDown at " + screenX + ", " + screenY);
		Utils.log("worldX: " + worldX);
		Utils.log("worldY: " + worldY);

		Utils.log("Corresponds to world coordinates of " + (screenX + worldX)
				+ ", " + (screenY + worldY));

		Vector2 position = screenToWorld(screenX,screenY);

		this.game.city.addCitizen(this.game.city.numCitizens() + 1, this.game,
				position);
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
