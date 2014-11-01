package com.davebilotta.thecity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Person extends GameObject {

	TheCity game;
	
	private int id;
	private boolean alive;
	
	// Character traits
	private int strength;          // 0 - 100
	private int intelligence;      // 0 - 100	
	private int hunger;            // 0 - 100
	private int thirst;            // 0 - 100
	private int money;             // unlimited
	private Person spouse;
	boolean fertile;
	
	private static final float FERTILITY_BEGIN = 5f;
	private static final float FERTILITY_END = 20f;
	
	private Gender gender;
	private float ageSeconds;
	
	//Vector2 position;
	
	int worldX, worldY;
	
	public enum Gender {
		MALE,FEMALE
	}
	
	/*public Person(int id, TheCity game) {
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
		this.ageSeconds = 0;
		this.fertile = false;
		
		this.position = new Vector2((float) manager.randomInt(Gdx.graphics.getWidth()),(float) manager.randomInt(Gdx.graphics.getHeight()));

		Utils.log("Creating " + this.gender + " with intelligence: " + this.intelligence + ", strength: " + this.strength + " starting at " + position.x + "," + position.y);
	}
	*/
	
	public Person(int id, TheCity game, Vector2 position) {
		// position = world position = translate this when rendering
		super(position);
		
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
		this.ageSeconds = 0;
		this.fertile = false;
		
	//this.position = position;

		Utils.log("Creating " + this.gender + " with intelligence: " + this.intelligence + ", strength: " + this.strength + " starting at " + position.x + "," + position.y);
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

	public float getAge() {
		return ageSeconds;
	}
	
	public boolean isMarried() {
		if (this.spouse == null) return false;
		else return true;
	}
	
	public boolean isAlive() {
		return this.alive;
	}
	
	public void die() {
		Utils.log("Person " + this.id + " has died.");
		this.alive = false;
	}
	
	public void increaseAge(float delta) {
		this.ageSeconds+=delta;
		
		if (this.gender == Gender.FEMALE) {
			if (ageSeconds > FERTILITY_END) {
				//Utils.log(this.id + " is no longer fertile");
				this.fertile = false;
			}
			else if (ageSeconds > FERTILITY_BEGIN) {
				//Utils.log(this.id + " is now fertile");
				this.fertile = true;
			}
		}
	}
	
}
