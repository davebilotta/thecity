package com.davebilotta.thecity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {

	Vector2 position;
	Rectangle touchPoint;
	
	public GameObject(Vector2 position) {
		this.position = position;
	
		// TODO: Figure out height and width - for now be 32x32
		
		int width = 32;
		int height = 32;
		touchPoint = new Rectangle(position.x - (width/2), position.y - (height/2),width,height);
	}
	
	public Vector2 getPosition() { 
		return this.position;
	}
	
	public void setPosition(Vector2 position) { 
		this.position = position;
	}
	
}
