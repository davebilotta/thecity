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
	GameScreen screen;
	
	// Used for timing/generating events
	final float EVENT_THRESHOLD = 5f;  // Number of seconds in between events
	float delta = 0f;
	
	@Override
	public void create () {
		//batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		
		this.eventManager = new EventManager(this);
		this.city = new City(this);
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
