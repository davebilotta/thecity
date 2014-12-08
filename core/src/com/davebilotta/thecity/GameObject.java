package com.davebilotta.thecity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {

	TheCity game;
	private int id;
	
	Vector2 position;
	Rectangle touchPoint;
	private boolean selected;
	private boolean alive;
	
	
	private int health;
	
	public GameObject(int id, TheCity game, Vector2 position) {
		this.game = game;
		this.id = id;
		
		this.position = position;
		this.selected = false;
		
		// TODO: Figure out height and width - for now be 32x32
		
		int width = 32;
		int height = 32;
		touchPoint = new Rectangle(position.x - (width/2), position.y - (height/2),width,height);
		
	}
	
	public int getId() {
		return id;
	}
	
	public Vector2 getPosition() { 
		return this.position;
	}
	
	public void setPosition(Vector2 position) { 
		this.position = position;
	}
	
	public boolean isSelected() { 
		return this.selected;
	}
	
	public void toggleSelected() {
		if (this.selected) this.selected = false;
		else this.selected = true;
		
	}
	
	public void select() {
		this.selected = true;
	}
	
	public void unselect() {
		this.selected = false;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	// Just for testing - remove later
	public void hit(int amount) {
		
		this.health -= amount;
				
		if (this.health <= 0) {
			this.health = 0;
			this.die();
		}
		
	}
	
	public boolean isAlive() {
		return this.alive;
	}

	public void die() {
		this.alive = false;
		
		// todo: make this more generic - check type?
		this.game.city.removeCitizen((Person) this);
		
	}
}
