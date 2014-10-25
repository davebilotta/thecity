package com.davebilotta.thecity;

import java.util.Random;

public class Person {

	TheCity game;
	
	private int id;
	// Character traits
	private int strength;          // 0 - 100
	private int intelligence;      // 0 - 100	
	private int hunger;            // 0 - 100
	private int thirst;            // 0 - 100
	private int money;             // unlimited
	private Person spouse;
	
	private Gender gender;
	
	public enum Gender {
		MALE,FEMALE
	}
	
	public Person(int id, TheCity game) {
		this.game = game;
		this.id = id;
		// Randomly assigned - should some of these be based on parent?
		// ie, more intelligent people more likely to have intelligent children?
		EventManager manager = this.game.eventManager;
		
		this.strength = manager.randomInt(20,100);
		this.intelligence = manager.randomInt(50,100);
		
		boolean g = manager.randomBoolean();
		if (g == true) this.gender = Gender.MALE;
		else this.gender = Gender.FEMALE;
		
		this.hunger = 0;
		this.thirst = 0;
		this.money = 0;

		Utils.log("Creating " + this.gender + " with intelligence: " + this.intelligence + ", strength: " + this.strength);
	}
	
	// Getters
	public int getId() {
		return id;
	}

	public int getStrength() {
		return strength;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public int getHunger() {
		return hunger;
	}

	public int getThirst() {
		return thirst;
	}

	public int getMoney() {
		return money;
	}

	public Person getSpouse() {
		return spouse;
	}

	public Gender getGender() {
		return gender;
	}

}
