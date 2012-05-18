package com.chalmers.speedtype.model;

public class SpeedRewardPowerUp extends PowerUp {

	// private long timeLeftAtStart;
	// private int lengthOfWord;

	public SpeedRewardPowerUp(GameModel model) {
		super(model);
	}

	/*
	 * If the current word was typed in less than, the length of word divided by
	 * 5, seconds, a bonus will be added to the score. For example: If the word
	 * has 5 letters, the player will only get the bonus if it is typed within 1
	 * second. The actual divider will be decided later on.
	 */
	// public boolean checkSpeedReward(long timeLeftAtFinish) {
	// if (timeLeftAtStart - timeLeftAtFinish < lengthOfWord * 1000 / 1) {
	// return true;
	// } else {
	// System.out.println("Too slow!");
	// return false;
	// }
	// }

	// public void addSpeedReward(PowerUp speedReward, long timeLeft, int score,
	// TextView speedBonusView, TextView speedBonusScoreView,
	// TextView scoreView, Activity activity) {
	// if (speedReward != null) {
	// if (speedReward.checkSpeedReward(timeLeft)) {
	// speedBonusScoreView.setVisibility(0);
	// speedBonusView.setVisibility(0);
	// Animation speedBonusAnimation = AnimationUtils.loadAnimation(
	// activity.getApplicationContext(),
	// R.anim.speed_bonus_animation);
	// Animation speedBonusAnimation2 = AnimationUtils.loadAnimation(
	// activity.getApplicationContext(),
	// R.anim.speed_bonus_animation_2);
	// Animation speedBonusAnimation3 = AnimationUtils.loadAnimation(
	// activity.getApplicationContext(),
	// R.anim.speed_bonus_animation_3);
	// speedBonusView.startAnimation(speedBonusAnimation2);
	// speedBonusScoreView.startAnimation(speedBonusAnimation);
	// scoreView.startAnimation(speedBonusAnimation3);
	//
	// score = score + 5;
	// System.out.println("Speed reward given");
	// }
	// }
	// }

	@Override
	public void usePowerUp() {
		model.incScore(10);
	}

	@Override
	public void endPowerUp() {
	}
}
