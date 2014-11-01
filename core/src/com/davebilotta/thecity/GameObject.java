package com.davebilotta.thecity;

import com.badlogic.gdx.math.Vector2;

public class GameObject {

	private Vector2 position;
	
	public GameObject(Vector2 position) {
		this.position = position;
	}
	
	public Vector2 getPosition() { 
		return this.position;
	}
	
	public void setPosition(Vector2 position) { 
		this.position = position;
	}
	
}
