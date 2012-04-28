package com.chalmers.speedtype.model;

public class PowerUp {

	public PowerUp(int powerUpMultiplier) {
		powerUpMultiplier = powerUpMultiplier * 2;
	}

	public int incrementMultiplier(int powerUpMultiplier) {
		powerUpMultiplier = powerUpMultiplier * 2;
		return powerUpMultiplier;
	}

}
