package com.chalmers.speedtype.model;

public class SpeedRewardPowerUp extends PowerUp {

	/*
	 * If the current word was typed in less than, the length of word divided by
	 * 5, seconds, a bonus will be added to the score. For example: If the word
	 * has 5 letters, the player will only get the bonus if it is typed within 1
	 * second. The actual divider will be decided later on.
	 */

	private static final long POWERUP_DELAY_MILLIS = 1000;
	private final int incScoreAmount = 50;

	private long startMillis;

	public SpeedRewardPowerUp(GameModel model) {
		super(model);
	}

	@Override
	public void usePowerUp() {
		model.incScore(50);
		startMillis = System.currentTimeMillis();
	}

	@Override
	public String toString() {
		return incScoreAmount + "";
	}

	@Override
	public void endPowerUp() {
	}

	public boolean isFinished() {
		if (System.currentTimeMillis() - startMillis < POWERUP_DELAY_MILLIS) {
			return false;
		}
		return true;
	}
}
