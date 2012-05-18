package com.chalmers.speedtype.model;

public class MultiplierPowerUp extends PowerUp {

	// public void useMultiplier(int correctLettersInRow, int powerUpMultiplier,
	// TextView powerUpView, Activity activity) {
	// // powerUpMultiplier = incrementMultiplier(powerUpMultiplier);
	// // powerUpMultiplier = powerUpMultiplier * 2;
	// powerUpView = (TextView) activity.findViewById(R.id.multiplier);
	// Animation multiplierAnimation = AnimationUtils.loadAnimation(
	// activity.getApplicationContext(), R.anim.multiplier_animation);
	// powerUpView.setVisibility(0);
	// powerUpView.setText("x" + powerUpMultiplier);
	// powerUpView.startAnimation(multiplierAnimation);
	// System.out.println("useMultiplier");
	// System.out.println("multiplier = " + powerUpMultiplier);
	// }
	//
	// public int incrementMultiplier(int powerUpMultiplier) {
	// return powerUpMultiplier * 2;
	// }

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
