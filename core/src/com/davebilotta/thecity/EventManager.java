package com.davebilotta.thecity;

import java.util.Random;

public class EventManager {
	
	TheCity game;

	Random random;
	private enum EventTypes {
		BIRTH,DEATH,MARRIAGE
	}
	
	public EventManager (TheCity game) {
		this.game = game;
		this.random = new Random();
	
	}
		
	public int randomInt(int max) {
		return this.random.nextInt(max+1);
	}
	
	public int randomInt(int min, int max) {
		return this.random.nextInt((max - min)+1) + min;
	}
	
	public boolean randomBoolean() {
		return this.random.nextBoolean();
	}
	
	public void newEvent() {
		Utils.log("NEW EVENT");
		this.game.city.addCitizen(game);
		
	}
}
