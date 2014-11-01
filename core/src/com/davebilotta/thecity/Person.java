package com.davebilotta.thecity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Person extends GameObject {

	TheCity game;

	private int id;
	private boolean alive;

	// Character traits
	private int strength; // 20 - 100
	private int intelligence; // 50 - 100
	private int hunger; // 0 - 100
	private int thirst; // 0 - 100
	private int speed;  // 50 - 100
	private int money; // unlimited
	private Person spouse;
	boolean fertile;
	boolean left; // is person moving left?

	private static final int FERTILITY_BEGIN = 12; // year at which fertility begins
	private static final int FERTILITY_END = 33; // year at which fertility ends

	private Gender gender;
	private Action action;
	private float ageSeconds;
	private int ageMonths, ageYears;

	int worldX, worldY;

	public enum Gender {
		MALE, FEMALE
	}

	public enum Action {
		WALKING, RUNNING, ATTACKING, ATTACKED, SLEEPING, EATING, DRINKING, HUNTING, BUILDING, WANDERING
	}

	public Person(int id, TheCity game, Vector2 position) {
		// position = world position => translate this when rendering
		super(position);

		this.game = game;
		this.id = id;
		// Randomly assigned - should some of these be based on parent?
		// ie, more intelligent people more likely to have intelligent children?
		EventManager manager = this.game.eventManager;

		this.strength = manager.randomInt(20, 100);
		this.intelligence = manager.randomInt(50, 100);
		this.speed = manager.randomInt(50, 100);

		boolean g = manager.randomBoolean();
		if (g == true)
			this.gender = Gender.MALE;
		else
			this.gender = Gender.FEMALE;

		this.hunger = 0;
		this.thirst = 0;
		this.money = 0;
		this.ageSeconds = 0;
		this.ageMonths = 0;
		this.ageYears = 0;
		this.fertile = false;

		this.action = action.WANDERING;
		this.left = this.game.eventManager.randomBoolean();

		Utils.log("Creating " + this.gender + " with intelligence: "
				+ this.intelligence + ", strength: " + this.strength
				+ " starting at " + position.x + "," + position.y);

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
		if (this.spouse == null)
			return false;
		else
			return true;
	}

	public boolean isAlive() {
		return this.alive;
	}

	public void die() {
		Utils.log("Person " + this.id + " has died.");
		this.alive = false;
	}

	public void doActivity(float delta) {
		// This just continues the persons previous activity
		switch (this.action) {
		case WANDERING:
			wander(delta);
			break;

		default:
			wander(delta);
			break;
		}
	}

	public void wander(float delta) {
		// this happens when person is created, bored, etc.
		// For now only move in the X direction
		
		if (this.left) {
			this.position.x -= (delta * (float) (this.speed * 0.20));
		
		if (this.position.x < 0) {
			this.position.x = 0;
			this.left = false;
		}
			
		} 
		// moving right
		else {
			this.position.x += (delta * (float) (this.speed * 0.20));
			
			if (this.position.x > this.game.screen.mapWidth * this.game.screen.tileSize) {
				this.position.x = (this.game.screen.mapWidth * this.game.screen.tileSize);
				this.left = true;
						
			}
		}
				
		
	}

	public void increaseAge(float delta) {
		this.ageSeconds += delta;

		if (this.ageSeconds >= this.game.gameMonthSeconds) {
			this.ageSeconds = 0;
			this.ageMonths++;

			if (this.ageMonths > 11) {
				this.ageYears++;
				this.ageMonths = 0;
			}

		}

		// FEMALES - check if still fertile
		if (this.gender == Gender.FEMALE) {
			if (ageYears > FERTILITY_END) {
				this.fertile = false;
			} else if (ageYears > FERTILITY_BEGIN) {
				// Utils.log(this.id + " is now fertile");
				this.fertile = true;
			}
		}
	}

}
