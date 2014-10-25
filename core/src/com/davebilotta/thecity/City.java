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

	
	private int numCitizens() {
		return this.citizens.size();
	}
	
	private double averageIntelligence() {
		Iterator i = citizens.iterator();
		Person p;
		double intelligence = 0;
		
		while (i.hasNext()) {
			p = (Person)i.next();
			intelligence+= (double)p.getIntelligence();
		}
		
		return (double)(intelligence/citizens.size());
	}
	
	private double averageStrength() {
		Iterator i = citizens.iterator();
		Person p;
		double strength = 0;
		
		while (i.hasNext()) {
			p = (Person)i.next();
			strength+= (double)p.getStrength();
		}
		
		return (double) (strength/citizens.size());
	}
	
	private double averageWealth() {
		Iterator i = citizens.iterator();
		Person p;
		double money = 0;
		
		while (i.hasNext()) {
			p = (Person)i.next();
			money+= (double)p.getMoney();
		}
		
		return (double) (money/citizens.size());
	}
	
	private ArrayList<Person> getCitizens(Gender gender) {
		Iterator i = citizens.iterator();
		ArrayList<Person> l = new ArrayList<Person>();
		Person p;
		
		while (i.hasNext()) {
			p = (Person)i.next();
			if (p.getGender() == gender) {
				l.add(p);
			}
		}
		return l;
	}
	
	public void reportStatus() {
		Utils.log("***** Total population: " + citizens.size() + " citizens *****");
		Utils.log("Number males: " + getCitizens(Gender.MALE).size());
		Utils.log("Number females: " + getCitizens(Gender.FEMALE).size());
		Utils.log("Average intelligence: " + averageIntelligence());
		Utils.log("Average strength: " + averageStrength());
		Utils.log("Average wealth: " + averageWealth());
	}
}
