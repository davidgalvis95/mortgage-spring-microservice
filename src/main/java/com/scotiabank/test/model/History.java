package com.scotiabank.test.model;

import java.util.ArrayList;
import java.util.List;

public class History {

	private static List<Mortgage> calculations = new ArrayList<>();

	public static List<Mortgage> getCalculations() {
		return calculations;
	}

	public static void addCalculations(Mortgage mortgage) {
		calculations.add(mortgage);
	}
	
}
