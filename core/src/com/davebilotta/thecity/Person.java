package com.davebilotta.thecity;

import java.util.Random;

public class Person {

	private int id;
	// Character traits
	private int strength;          // 0 - 100
	private int intelligence;      // 0 - 100
	
	//  
	private int hunger;            // 0 - 100
	private int thirst;            // 0 - 100
	private int money;             // unlimited
	private Person spouse;
	
	private Gender gender;
	
	public enum Gender {
		MALE,FEMALE
	}
	
	public Person(EventManager eventManager) {
		
		// Randomly assigned - should some of these be based on parent?
		// ie, more intelligent people more likely to have intelligent children?
		this.strength = eventManager.randomInt(100);
		this.intelligence = eventManager.randomInt(100);
		
		boolean g = eventManager.randomBoolean();
		if (g == true) this.gender = Gender.MALE;
		else this.gender = Gender.FEMALE;
		
		this.hunger = 0;
		this.thirst = 0;
		this.money = 0;

		Utils.log("Creating " + this.gender + " with intelligence: " + this.intelligence + ", strength: " + this.strength);
	}
}
