package com.davebilotta.thecity;

import java.util.ArrayList;
import java.util.Iterator;

import com.davebilotta.thecity.Person.Gender;

public class City {

	private ArrayList<Person> citizens;
	TheCity game;
	
	public City(TheCity game) {
		this.game = game;
		
		this.citizens = new ArrayList<Person>();
		
		for (int i = 0; i < 5; i ++) {
			addCitizen(i,game);
		}
		
		this.reportStatus();
	}
	
	public void addCitizen(int i,TheCity game) {
		citizens.add(new Person(i,game));
	}
	
	public void addCitizen(TheCity game) {
		citizens.add(new Person(citizens.size()+1,game));
	}
	
	public void addCitizens(TheCity game,int size) {
		for (int i = 0; i < size; i ++) {
			citizens.add(new Person(citizens.size()+1,game));
		}
	}

	
	private int numCitizens() {
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
	
	public void ageCitizens(float delta) {
		Iterator<Person> i = citizens.iterator();
		
		Person p;
		
		while (i.hasNext()) {
			p = i.next();
			p.increaseAge(delta);
		}
	}
	
	public void reportStatus() {
		Utils.log("***** Total population: " + citizens.size() + " citizens *****");
		int m = getCitizens(Gender.MALE).size();
		int f = getCitizens(Gender.FEMALE).size();
		
		Utils.log("        Number males: " + m + " (" + (double)(((double)m/(m+f))*100) + "%)");
		Utils.log("      Number females: " + f + " (" + (double)(((double)f/(m+f))*100) + "%)");
		Utils.log("Average intelligence: " + averageIntelligence());
		Utils.log("    Average strength: " + averageStrength());
		Utils.log("      Average wealth: " + averageWealth());
		
		float[] stats = ageStats();
		
		Utils.log("Youngest: " + stats[0] + ", oldest: "+stats[1] + ", average: " + stats[2]);
	}
}
