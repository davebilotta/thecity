package com.davebilotta.thecity;

public class MathUtils {

	public static double round(double input) {
		input = input*100;
		input = Math.round(input);
		return input/100;
	}
}
