package com.chalmers.speedtype.model;

public abstract class PowerUp {

	protected GameModel model;

	public PowerUp(GameModel model) {
		this.model = model;
	}

	public abstract void usePowerUp();

	public abstract void endPowerUp();

}
