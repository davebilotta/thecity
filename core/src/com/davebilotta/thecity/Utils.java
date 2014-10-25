package com.davebilotta.thecity;

public class Utils {

	public static void log (String message) {
		if (TheCity.DEBUG_MODE) {
			System.out.println(message);
		}
	}
	
	public static void log (int message) {
		if (TheCity.DEBUG_MODE) {
			System.out.println(message);
		}
	}
}
