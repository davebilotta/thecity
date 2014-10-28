package com.davebilotta.thecity;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TheCity extends Game {
	public static final boolean DEBUG_MODE = true;
	EventManager eventManager;
	City city;
	Level level;
	GameScreen screen;
	
	// Used for timing/generating events
	final float EVENT_THRESHOLD = 10f;  // Number of seconds in between events
	float delta = 0f;
	
	public static final int WORLD_WIDTH = 5000;    // Width (in tiles) of the entire world
	public static final int WORLD_HEIGHT = 5000;   // Height (in tiles) of the entire world
	
	// TODO: This needs to depend on device height/width
	public static final int VIEW_WIDTH = 100;      // Width (in tiles) we can see of the world at any moment
	public static final int VIEW_HEIGHT = 100;     // Height (in tiles) we can see of the world at any moment
	
	public static final int TILE_WIDTH = 32;       // Width (In pixels) of tile
	public static final int TILE_HEIGHT = 32;      // Height (in pixels) of tile
	
	@Override
	public void create () {
		//batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		
		this.eventManager = new EventManager(this);
		this.city = new City(this);
		this.level = new Level(this);
		this.screen = new GameScreen(this);
		this.setScreen(screen);
		
	}

	public void update (float delta) {
		this.delta +=delta;
		
		this.city.ageCitizens(delta);
		
		if (this.delta > EVENT_THRESHOLD) {
			this.delta = 0;
			this.eventManager.newEvent();
			this.city.reportStatus();
		}
	}
}
