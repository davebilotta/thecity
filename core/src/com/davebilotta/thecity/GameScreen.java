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

public class GameScreen implements Screen, InputProcessor {

	TheCity game;
	SpriteBatch batch;
	Texture red_dot, orange_dot, yellow_dot, green_dot, blue_dot, white_dot;

	OrthographicCamera camera;
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;

	int tileSize, mapWidth, mapHeight;
	int xpos, ypos;

	public GameScreen(TheCity game) {
		this.game = game;
		this.batch = new SpriteBatch();

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();

		tiledMap = new TmxMapLoader().load("tilemaps/level1.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		Gdx.input.setInputProcessor(this);

		// TODO: Eventually move to assets class

		red_dot = new Texture("red_dot.png");
		orange_dot = new Texture("orange_dot.png");
		yellow_dot = new Texture("yellow_dot.png");
		green_dot = new Texture("green_dot.png");
		blue_dot = new Texture("blue_dot.png");
		white_dot = new Texture("white_dot.png");

		tileSize = 64;
		// map is 15 x 15;
		mapWidth = mapHeight = 250;
		xpos = 7;
		ypos = 7;

	}

	@Override
	public void render(float delta) {
		game.update(delta);

		Gdx.gl20.glClearColor(0, 0, 0, 1f);
		Gdx.gl20.glEnable(GL20.GL_BLEND);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT); // clear the buffer

		// Now do rendering here

		camera.position.set(xpos * (tileSize + 1), (ypos * tileSize)/2, 0);

		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		// renderMenu();
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
		// TODO Auto-generated method stub
		Utils.log("gamescreen-RESIZE");
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Utils.log("gamescreen-SHOW");

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		Utils.log("gamescreen-HIDE");
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		Utils.log("gamescreen-PAUSE");
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		Utils.log("gamescreen-RESUME");
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		Utils.log("gamescreen-DISPOSE");
	}

	// InputProcessor methods
	@Override
	public boolean keyDown(int keycode) {
		
		switch (keycode) {
		case Input.Keys.LEFT:
			if (xpos > 0) {
				xpos--;
				camera.translate(-tileSize, 0);}
			break;
			
		case Input.Keys.RIGHT:
			if (xpos < mapWidth) {
				xpos++;
				camera.translate(tileSize, 0);
			}
			break;

		case Input.Keys.DOWN:
			if (ypos > 0) {
				ypos--;
				camera.translate(0, -tileSize);}
			break;

		case Input.Keys.UP:
			if (ypos < mapHeight) {
				ypos++;
				camera.translate(0, tileSize);
			}
			break;
		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
