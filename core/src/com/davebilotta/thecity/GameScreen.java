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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.davebilotta.thecity.Person.Gender;

public class GameScreen implements Screen, InputProcessor {

	TheCity game;
	
	SpriteBatch batch;

	private Stage stage;
	OrthographicCamera camera;
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;

	int tileSize, mapWidth, mapHeight, itemSize;
	int worldX, worldY;
	int renderWidth, renderHeight; // # of tiles in either direction

	float screenWidth, screenHeight;
	
	// UI Components for header
	Label populationText;
	Label ageText;
	
	private GameObject currentObject;
	private boolean objectSelected;
	
	public GameScreen(TheCity game) {
		this.game = game;
		this.batch = new SpriteBatch();

		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		renderWidth = 16; // 1024px / 64
		renderHeight = 12; // 768px / 64
		tileSize = 64;
		itemSize = 32;
		
		camera = new OrthographicCamera(screenWidth,screenHeight);
		stage = new Stage(new ScreenViewport());
		
		boolean largeMap = false;
		if (largeMap) {
			tiledMap = new TmxMapLoader().load("tilemaps/level1.tmx");
			mapWidth = mapHeight = 250;
		} else {
			tiledMap = new TmxMapLoader().load("tilemaps/level3.tmx");
			mapWidth = mapHeight = 20;
		}
		
		createUI();
		
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
		stage.act();
		
		Gdx.gl20.glClearColor(0, 0, 0, 1f);
		Gdx.gl20.glEnable(GL20.GL_BLEND);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT); // clear the buffer
	
		camera.update();
		tiledMapRenderer.setView(camera);
		
		//tiledMapRenderer.setView(camera.combined,0,0,screenWidth,screenHeight);
		
		tiledMapRenderer.render();

		renderCitizens(delta);
		renderUIComponents();
		
		stage.draw();
		

	}

	public void renderCitizens(float delta) {
		Person p;
		Texture texture;

		batch.begin();

		Iterator<Person> i = this.game.city.citizens.iterator();
		// TODO: Only render those whose tileX and tileY are within the viewing
		// window
		while (i.hasNext()) {
			p = i.next();

			// figure out if in view
			if (inView(p)) {
				
			if (p.isSelected()) {
				texture = Assets.white_dot;
							}
			else {
				if (p.getGender() == Gender.MALE)
					texture = Assets.blue_dot;
				else {
					if (p.fertile)
						texture = Assets.green_dot;
					else
						texture = Assets.red_dot;
				}
			}

				Vector2 screenPos = worldToScreen(p.getPosition());
				// TODO: need to factor item's size when rendering
				batch.draw(texture,screenPos.x,screenPos.y);
				
				if (p.isSelected()) {
					renderHealthBar(p,screenPos);
				}
				
			}
		} // end while

		batch.end();
	}
	
	public void renderHealthBar (GameObject p, Vector2 pos) {
		// display at
		int healthSize = 4;
		int health = p.getHealth();
		// render max of 8
		//int h = Math.floor((double) (((health/100) * itemSize) / healthSize));
		int h = (int) ((double) (health/100.0f) * itemSize) / healthSize;
		
		//Utils.log("drawing " + h + " bars");
		
		for (int i = 0; i < h; i++) {
		batch.draw(Assets.health,(pos.x + (healthSize * i)),pos.y + itemSize + 5);
		}
	}

	public boolean inView(GameObject item) {
		// position = position in world
		Vector2 position = item.getPosition();

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

	public void createUI() {
		populationText = new Label("Population: 0",Assets.menuLabelStyle);
		populationText.setPosition(0,screenHeight - populationText.getHeight());
		
		ageText = new Label("0m0y",Assets.menuLabelStyle);
		// TODO: Fix this position
		ageText.setPosition(screenWidth - 250, screenHeight - ageText.getHeight());
		
		stage.addActor(populationText);
		stage.addActor(ageText);
		

		
	}
	
	public void renderUIComponents() {
		// render city information
				
		populationText.setText(this.game.city.getPopulationText());
		ageText.setText(this.game.city.getAgeText());
		
	
	}
	
	@Override
	public void resize(int width, int height) {
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
 
		camera.viewportWidth = screenWidth;
		camera.viewportHeight = screenHeight;

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

	//	float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
	//	float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

		camera.position.x = worldX + (tileSize * renderWidth) / 2 ;
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

		Vector2 position = screenToWorld(screenX,screenY);

		// check for collisions
		
		if (gameObjectTouched(position)) {
			// Which one was touched?
		}
		else {
			// For now, just create a new person
			this.game.city.addCitizen(this.game.city.numCitizens() + 1, this.game, position);

		}
		
		return false;
	}
	
	public boolean gameObjectTouched(Vector2 position) {
		boolean touched = false;
		
		// Check people
		Iterator<Person> i = this.game.city.citizens.iterator();
		Person p;
		// how big should touch size be?
		Rectangle touchRect = new Rectangle(position.x,position.y,itemSize,itemSize);
		
		while (i.hasNext() && !touched) {
			p = i.next();
			
			if (inView(p)) {
				Rectangle checkRect = new Rectangle(p.getPosition().x,p.getPosition().y,itemSize,itemSize);
				
					if (touchRect.overlaps(checkRect)) {
					// Unselect current object, if any 
					if (objectSelected) {
						
						// Just for testing - remove later
						if (currentObject == p) {
							p.hit(this.game.eventManager.randomInt(1, 15));
						}
						
						currentObject.toggleSelected();
					}
					
					objectSelected = true;					
					currentObject = p;
					
					p.toggleSelected();
					
					Utils.log("Touched" + p.getId());
					
					touched = true;
				}
			}

		}
		
		// TODO: Check buildings, tourists, etc.
		return touched;
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
		// -amount = up on scrollwheel, +amount = down
		Utils.log("Scrolled " + amount);
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
		stage.dispose();

		Assets.dispose();
	}
}
