package com.davebilotta.thecity;

import java.util.ArrayList;
import java.util.Iterator;

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
	final float EVENT_THRESHOLD = 10f; // Number of seconds in between events
	float gameMonthSeconds; // How many seconds in real life equals one month in
							// the game;
	int gameSpeed = 1;
	float delta = 0f;

	public static final int WORLD_WIDTH = 5000; // Width (in tiles) of the
												// entire world
	public static final int WORLD_HEIGHT = 5000; // Height (in tiles) of the
													// entire world

	// TODO: This needs to depend on device height/width
	public static final int VIEW_WIDTH = 100; // Width (in tiles) we can see of
												// the world at any moment
	public static final int VIEW_HEIGHT = 100; // Height (in tiles) we can see
												// of the world at any moment
	
	@Override
	public void create() {
	
		Assets.load();

		this.eventManager = new EventManager(this);
		this.city = new City(this);
		this.level = new Level(this);
		this.screen = new GameScreen(this);
		this.setScreen(screen);
		
		// TODO: Start this at the starting speed based on preferences
		this.setSpeed(5);

		eventManager.logMessage("City created!");
		
	}

	private void setSpeed(int gameSpeed) {
		// speed 1 = 5 real life minute per month
		// speed 5 = 1 real life minute per month
		
		if (gameSpeed > 5 || gameSpeed < 0) {
			gameMonthSeconds = 500;
		}
		else {
			gameMonthSeconds = 300 - ((gameSpeed -1)* 60);
		}
	}

	public void update(float delta) {
		this.delta += delta;

		updateCity(delta);
		updateCitizens(delta);

		if (this.delta > EVENT_THRESHOLD) {
			this.delta = 0;
		}
	}
	
	public void updateCity(float delta) {
		this.city.age(delta);
	}
	
	public void updateCitizens(float delta) {
		Iterator<Person> i = this.city.citizens.iterator();
		ArrayList<Person> removedCitizens = new ArrayList<Person>();
		Person p;
			
		// This will age citizens
		// And continue their business (eg, what they were doing previously)
		while (i.hasNext()) {
			p = i.next();
			p.increaseAge(delta);
			p.doActivity(delta);
			}
		}

}
