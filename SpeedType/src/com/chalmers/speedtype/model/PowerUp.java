package com.chalmers.speedtype.model;

import com.chalmers.speedtype.R;

import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

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

	public void addSpeedReward(PowerUp speedReward, long timeLeft, int score,
			TextView speedBonusView, TextView speedBonusScoreView,
			Activity activity) {
		if (speedReward != null) {
			if (speedReward.checkSpeedReward(timeLeft)) {
				speedBonusView.setVisibility(0);
				Animation speedBonusAnimation = AnimationUtils.loadAnimation(
						activity.getApplicationContext(),
						R.anim.speed_bonus_animation);
				Animation speedBonusAnimation2 = AnimationUtils.loadAnimation(
						activity.getApplicationContext(),
						R.anim.speed_bonus_animation_2);
				speedBonusView.startAnimation(speedBonusAnimation2);
				speedBonusScoreView.startAnimation(speedBonusAnimation);
				score = score + 5;
				System.out.println("Speed reward given");
			}
		}
	}
}
