package com.davebilotta.thecity;

import java.util.Random;

public class EventManager {

	Random random;
	private enum EventTypes {
		BIRTH,DEATH,MARRIAGE
	}
	
	public EventManager () {
		this.random = new Random();
		
		for (int i = 0; i < 20; i ++) {
			Person person = new Person(this);
		}
	}
		
	public int randomInt(int max) {
		return this.random.nextInt(max+1);
	}
	
	public boolean randomBoolean() {
		return this.random.nextBoolean();
	}
}
