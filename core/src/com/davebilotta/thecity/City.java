package com.davebilotta.thecity;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;
import com.davebilotta.thecity.Person.Gender;

public class City {

	ArrayList<Person> citizens;
	TheCity game;
	private float ageSeconds;
	private int ageMonths;
	private int ageYears;
	
	public City(TheCity game) {
		this.game = game;
		
		this.ageSeconds = 0;
		this.ageMonths = 0;
		this.ageYears = 0;
		
		this.citizens = new ArrayList<Person>();
		
	}
		
	public void addCitizen(int i,TheCity game,Vector2 position) {
		citizens.add(new Person(i,game,position));
	}
	
	public int numCitizens() {
		return this.citizens.size();
	}
	
	private double averageIntelligence() {
		Iterator<Person> i = citizens.iterator();
		Person p;
		double intelligence = 0;
		
		while (i.hasNext()) {
			p = i.next();
			intelligence+= (double)p.getIntelligence();
		}
		
		return (double)(intelligence/citizens.size());
	}
	
	private double averageStrength() {
		Iterator<Person> i = citizens.iterator();
		Person p;
		double strength = 0;
		
		while (i.hasNext()) {
			p = i.next();
			strength+= (double)p.getStrength();
		}
		
		return (double) (strength/citizens.size());
	}
	
	private double averageWealth() {
		Iterator<Person> i = citizens.iterator();
		Person p;
		double money = 0;
		
		while (i.hasNext()) {
			p = i.next();
			money+= (double)p.getMoney();
		}
		
		return (double) (money/citizens.size());
	}
	
	private double averageAge() {
		Iterator<Person> i = citizens.iterator();
		Person p;
		double age = 0;
		
		while (i.hasNext()) {
			p = i.next();
			age+= (double)p.getAge();
		}
		
		return (double) (age/citizens.size());
	}
	
	private float[] ageStats() {
		// returns min, max, average ages
		
		Iterator<Person> i = citizens.iterator();
		Person p;
		float[] stats = new float[3];
		
		float max = 0f;
		float min = 9999999999f;
		float age = 0f;
		float a;
		
		while (i.hasNext()) {
			p = i.next();
			a = (float) p.getAge();
			if (a > max) { 
				max = a;
			}
			if (a < min) {
				min = a;
			}
			
			age += a;
		}
		
		float avg = (float) (age/citizens.size());
		
		return new float[]{min,max,avg};
		
	}
	
	private ArrayList<Person> getCitizens(Gender gender) {
		Iterator<Person> i = citizens.iterator();
		ArrayList<Person> l = new ArrayList<Person>();
		Person p;
		
		while (i.hasNext()) {
			p = i.next();
			if (p.getGender() == gender) {
				l.add(p);
			}
		}
		return l;
	}
	
	public void age (float delta) {
		// Ages city, citizens
		ageCity(delta);
	}
	
	private void ageCity(float delta) {
		// Age city
		// if we've hit the number of real life seconds for one game month, 
		// increase the city age by 1 month (and possibly 1 year)
	
		this.ageSeconds+=delta;
		
		if (this.ageSeconds >= this.game.gameMonthSeconds) { 
			this.ageSeconds = 0;
			this.ageMonths++;
			
			if (this.ageMonths > 11) {
				this.ageYears++;
				this.ageMonths = 0;
			}
			
			this.reportStatus();
		}
		
	}	
	
	public void removeCitizens(ArrayList<Person> temp) {
		// 
		Iterator<Person> i = citizens.iterator();
		ArrayList<Person> tempCitizens = new ArrayList<Person>();
		Person p;
				
		while (i.hasNext()) {
			p = i.next();
			if (!temp.contains(p)) tempCitizens.add(p);
		}
		citizens = tempCitizens;
	}
	
	public void reportStatus() {
		Utils.log("***** Total population: " + citizens.size() + " citizens *****");
		int m = getCitizens(Gender.MALE).size();
		int f = getCitizens(Gender.FEMALE).size();
		
		Utils.log("        Number males: " + m + " (" + MathUtils.round((double)(((double)m/(m+f))*100)) + "%)");
		Utils.log("      Number females: " + f + " (" + MathUtils.round((double)(((double)f/(m+f))*100)) + "%)");
		
		Utils.log("Average intelligence: " + MathUtils.round(averageIntelligence()));
		Utils.log("    Average strength: " + MathUtils.round(averageStrength()));
		Utils.log("      Average wealth: " + MathUtils.round(averageWealth()));
		
		float[] stats = ageStats();
		
		Utils.log("Youngest: " + stats[0] + ", oldest: "+stats[1] + ", average: " + stats[2]);
		
		Utils.log("City is " + this.ageYears + " years, " + this.ageMonths + " months");
	}
	
	// Used to render population, age for UI
	public CharSequence getPopulationText() {
		return "Population: " + this.citizens.size();
	}

	public CharSequence getAgeText() {

		return "Year: " + this.ageYears + " Month: " + this.ageMonths;
	}
}
