package com.davebilotta.thecity;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen, InputProcessor {

	TheCity game;
	SpriteBatch batch;

	private Stage stage;
	OrthographicCamera camera;
	ShapeRenderer sr;
	
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;

	int tileSize, mapWidth, mapHeight, itemSize;
	int worldX, worldY;
	int renderWidth, renderHeight; // # of tiles in either direction

	float screenWidth, screenHeight;
	
	// UI Components for header
	Label populationText;
	Label ageText;
	boolean bottomBarVisible = false;
	
	private ScreenRenderer renderer;
	
	private GameObject currentObject;
	private ArrayList<GameObject> currentObjects = new ArrayList<GameObject>();
	private boolean objectSelected = false;
	
	private boolean touchDragged = false;
	private Vector2 touchStartPos;
	private Vector2 touchEndDiff;
	private Vector2 touchEndPos;
	private boolean controlKey = false;
	
	private FPSLogger logger;
	boolean log = false;
	
	ImageTextButton messageButton;
	
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
		
		this.renderer = new ScreenRenderer(game,this,batch);
		
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
				
		
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(inputMultiplexer);
		
//		Gdx.input.setInputProcessor(this);
		

		worldX = (mapWidth - renderWidth) * tileSize;
		worldY = (mapHeight - renderHeight) * tileSize;

		camera.position.x = worldX + (tileSize * renderWidth) / 2;
		camera.position.y = worldY + (tileSize * renderHeight) / 2;
		
		camera.update();
		
		logger = new FPSLogger();
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
		tiledMapRenderer.render();
		
		renderer.render(delta);
		
		stage.draw();
			
		if (log) logger.log();
		
	}

	public void createUI() {
		populationText = new Label("Population: 0",Assets.menuLabelStyle);
		populationText.setPosition(0,screenHeight - populationText.getHeight());
		
		ageText = new Label("age",Assets.menuLabelStyle);
		ageText.setPosition(screenWidth - ageText.getWidth(), screenHeight - ageText.getHeight());
		
		stage.addActor(populationText);
		stage.addActor(ageText);
		
		addButton("Build",Assets.ui[6],Assets.ui[7], 10,10, 45,45, true).addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				
				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				buildButton();
			}
		}); 
		
		
		messageButton = addButton("",Assets.ui[8],Assets.ui[8], ((int) screenWidth/2 - 250),((int)screenHeight - 56), 500,56, false);
		messageButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Utils.log("Log Button DOWN");
				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Utils.log("Log Button UP");
			}
		});		
		
	}
	
	public ImageTextButton addButton(String text, TextureRegion textureUp,TextureRegion textureDown, int x, int y, int w, int h, boolean buttonStyle) {

		ImageTextButtonStyle imageTextButtonStyle = new ImageTextButtonStyle();
		if (buttonStyle) { 
			imageTextButtonStyle.font  = Assets.buttonFont;
			imageTextButtonStyle.fontColor = Color.BLACK;
		}
		else {
			imageTextButtonStyle.font  = Assets.messageFont;
			imageTextButtonStyle.fontColor = Color.WHITE;
		}
		imageTextButtonStyle.imageUp =  new TextureRegionDrawable(textureUp);
		imageTextButtonStyle.imageDown = new TextureRegionDrawable(textureDown);
		
		ImageTextButton button = new ImageTextButton(text,imageTextButtonStyle);
		
		Stack s = new Stack();
		s.add(button.getImage());
		s.add(button.getLabel());
		button.add(s);
		
		button.setPosition(x, y);
		button.setBounds(x, y, w,h);
		button.setVisible(true);
							
		stage.addActor(button);
		return button;
	}	

	@Override
	public void resize(int width, int height) {
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
 
		camera.viewportWidth = screenWidth;
		camera.viewportHeight = screenHeight;

	}

	// InputProcessor methods - eventually move this to own class
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
		case Input.Keys.CONTROL_LEFT:
		case Input.Keys.CONTROL_RIGHT:
			 controlKey = true;
			 break;

		case Input.Keys.TAB:
			if (log) log = false;
			else log = true;
			break;
			
		case Input.Keys.B:
			//this.game.city.addBuilding(this.game.city.numBuildings()+1, this.game, 
			//		new Vector2(this.game.eventManager.randomInt(1024),this.game.eventManager.randomInt(800)));
			buildButton();
			break;
		}

	//	float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
	//	float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

		camera.position.x = worldX + (tileSize * renderWidth) / 2 ;
		camera.position.y = worldY + (tileSize * renderHeight) / 2;

		return true;
	}

	@Override
	public boolean keyUp(int keycode) {

		controlKey = false;
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
	
		touchStartPos = new Vector2(screenX,screenY);
		
		return false;
	}
	
	public void leftClick(Vector2 position) {
		
		if (gameObjectTouched(position)) {
			// Which one was touched?
		}
		else {
			//addMany(position);
			 addSingle(position);
		}
		
	}
	
	public void buildButton() {
		if (bottomBarVisible) bottomBarVisible = false;
		else bottomBarVisible = true;
	}
	
	public void addSingle(Vector2 position) {
		this.game.city.addCitizen(this.game.city.numCitizens() + 1, this.game, position);

	}
	
	public void addMany(Vector2 position) {
		int f = 50;
		float x = position.x;
		float y = position.y;
		
		// Create 100 for now at random places in relation to position
		for (int i = 0; i < 100; i ++) {
			boolean plus = this.game.eventManager.randomBoolean();
			int a = this.game.eventManager.randomInt(0, f);
			int b = this.game.eventManager.randomInt(0, f);
			
			Vector2 vector;
			
			if (plus)  vector = new Vector2(x + a, y + b); 
			else vector = new Vector2(x - a, y - b);
							
			this.game.city.addCitizen(this.game.city.numCitizens() + 1, this.game, vector);
		}

	}
	
	public void rightClick(Vector2 position) {
		
	}
	
	public void middleClick(Vector2 position) {
		
	}
	
	public boolean gameObjectTouched(Vector2 position) {
		boolean done = false;
				
		// Check people
		Iterator<Person> i = this.game.city.citizens.iterator();
		Person p;
		// how big should touch size be?
		Rectangle touchRect = new Rectangle(position.x,position.y,itemSize,itemSize);
		
		while (i.hasNext() && !done) {
			p = i.next();
			
			if (renderer.inView(p)) {
				Rectangle checkRect = new Rectangle(p.getPosition().x,p.getPosition().y,itemSize,itemSize);
				
				if (touchRect.overlaps(checkRect)) {
					if (!controlKey) {
						unselectAll(p);
						p.toggleSelected();
							
						if (p.isSelected()) {
							currentObjects.add(p);
							p.pause();
						}
						else {
							currentObjects.remove(p);
							p.unpause();
						}
						done = true;
					}
					else {
						p.toggleSelected();
						if (p.isSelected()) {
							currentObjects.add(p);
							p.pause();
						}
						else {
							currentObjects.remove(p);
							p.unpause();
						}
					}
				} // end overlap check
			}

		}
		
		if (currentObjects.size() == 0) objectSelected = false;
		else objectSelected = true;
				
		//bottomBarVisible = objectSelected;
		// TODO: Check buildings, tourists, etc.
		if (done) return done;
		else return objectSelected;
	}
	
	// unselects all but o
	public void unselectAll(GameObject p) {
		Iterator<GameObject> iter = currentObjects.iterator();
		while (iter.hasNext()) {
			GameObject o = iter.next();
			if (p != o) {
				o.unselect();
				
			}
		}
		// Clear everything, even P - it will be added later
		currentObjects.clear();
	}
	
	// unselects all
	public void unselectAll() {
		Iterator<GameObject> iter = currentObjects.iterator();
		while (iter.hasNext()) {
			GameObject o = iter.next();
			o.unselect();
		}
		
		currentObjects.clear();
	}
	
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector2 position = renderer.screenToWorld(screenX,screenY);
	
		if (button == 0) leftClick(position);
		if (button == 1) rightClick(position);
		else middleClick(position);
		
		touchDragged = false;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		touchDragged = true;
				
		touchEndPos = new Vector2(screenX,(screenHeight - screenY));
		touchEndDiff =	touchEndPos.sub(touchStartPos);
			
		return false;
		
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		//Utils.log("Mouse moved");
		
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		// -amount = up on scrollwheel, +amount = down
		
		this.camera.zoom += (amount * 0.25f);
		
		if (this.camera.zoom < 0.25f) this.camera.zoom = 0.25f;
		if (this.camera.zoom > 2.0f) this.camera.zoom = 2.0f;

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
		renderer.batch.dispose();
		stage.dispose();

		Assets.dispose();
	}
}
