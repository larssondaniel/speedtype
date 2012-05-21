package com.chalmers.speedtype.model;

public class SpeedRewardPowerUp extends PowerUp {

	private static final long POWERUP_DELAY_MILLIS = 4000;
	private long startMillis;

	public SpeedRewardPowerUp(GameModel model) {
		super(model);
	}

	@Override
	public void usePowerUp() {
		model.incScore(10);
		startMillis = System.currentTimeMillis();
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
