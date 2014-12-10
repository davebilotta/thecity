package com.davebilotta.thecity;

import java.util.ArrayList;
import java.util.Random;

public class EventManager {
	
	TheCity game;
	ArrayList<String> messages;
	
	Random random;
	private enum EventTypes {
		BIRTH,DEATH,MARRIAGE
	}
	
	public EventManager (TheCity game) {
		this.game = game;
		this.random = new Random();
		this.messages = new ArrayList<String>();
	
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
		// This adds a single one
		//this.game.city.addCitizen(game);
		
	}
	
	public void logMessage(String msg) {
		messages.add(msg);
	}
	
	public String lastMessage() {
		
		return messages.get(messages.size()-1);
	}
}
