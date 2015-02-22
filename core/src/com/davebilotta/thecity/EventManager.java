package com.davebilotta.thecity;

import java.util.ArrayList;
import java.util.Random;

public class EventManager {
	
	TheCity game;
	ArrayList<String> messages;
	boolean messageDisplayed;
	float messageDuration;
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
	
	public void update(float delta) {
		this.messageDuration -= delta;
		
		if (this.messageDuration <=0) messageDisplayed = false; 
	}
	
	public void logMessage(String msg) {
		messages.add(msg);
		messageDisplayed = true;
		messageDuration = 5;
	}
	
	public void clearMessage() {
		messageDisplayed = true;
	}
	
	public String lastMessage() {
		
		if (messageDisplayed) { 
			return messages.get(messages.size()-1);
		}
		else {
			return "";
		}
	}
}
