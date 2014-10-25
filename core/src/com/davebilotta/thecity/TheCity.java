package com.davebilotta.thecity;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TheCity extends ApplicationAdapter {
	public static final boolean DEBUG_MODE = true;
	private EventManager eventmanager;
	
	@Override
	public void create () {
		//batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		
		this.eventmanager = new EventManager();
	}

	@Override
	public void render () {
	
	}
}
