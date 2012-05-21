package com.chalmers.speedtype.model;

public class MultiplierPowerUp extends PowerUp {

	public MultiplierPowerUp(GameModel model) {
		super(model);
	}

	@Override
	public void usePowerUp() {
		model.multiplier *= model.multiplier < 8 ? 2 : 1;
	}

	@Override
	public void endPowerUp() {
		model.multiplier = 1;
	}
}
