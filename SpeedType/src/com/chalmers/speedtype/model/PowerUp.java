package com.chalmers.speedtype.model;

public class PowerUp {

	private long timeLeftAtStart;
	private int lengthOfWord;

	public PowerUp(int powerUpMultiplier) {
		powerUpMultiplier = powerUpMultiplier * 2;
	}

	public PowerUp(long timeLeftAtStart, int lengthOfWord, int score) {
		this.timeLeftAtStart = timeLeftAtStart;
		this.lengthOfWord = lengthOfWord;
	}

	public int incrementMultiplier(int powerUpMultiplier) {
		powerUpMultiplier = powerUpMultiplier * 2;
		return powerUpMultiplier;
	}

	/*
	 * If the current word was typed in less than, the length of word divided by
	 * 5, seconds, a bonus will be added to the score. For example: If the word
	 * has 5 letters, the player will only get the bonus if it is typed within 1
	 * second. The actual divider will be decided later on.
	 */
	public boolean checkSpeedReward(long timeLeftAtFinish) {
		if (timeLeftAtStart - timeLeftAtFinish < lengthOfWord * 1000 / 1) {
			return true;
		} else {
			System.out.println("Too slow!");
			return false;
		}
	}
}
