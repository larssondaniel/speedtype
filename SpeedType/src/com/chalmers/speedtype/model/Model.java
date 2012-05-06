package com.chalmers.speedtype.model;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;
import android.widget.TextView;
import com.chalmers.speedtype.util.Dictionary;
import com.swarmconnect.SwarmAchievement;
import com.swarmconnect.SwarmLeaderboard;

public abstract class Model {

	protected Word currentWord;
	protected Word nextWord;

	protected int score = 0;
	protected int currentChar = 0;
	protected int powerUpMultiplier = 1;
	protected int correctLettersInRow;
	protected SwarmLeaderboard timeAttackLeaderboard;
	protected Map<Integer, SwarmAchievement> timeAttackAchievements;

	protected PowerUp multiplier;
	protected PowerUp speedReward;

	private Activity activity;
	protected TextView speedBonusView;
	protected TextView speedBonusScoreView;

	protected TextView powerUpView;

	private Point displaySize;

	public Model(Activity activity) {
		this.activity = activity;
	}

	protected Dictionary dictionary;

	public abstract void onTextChanged(CharSequence s);

	public abstract boolean isFinished();

	public Context getContext() {
		return activity.getApplicationContext();
	}

	public int getDisplayWidth() {
		if (displaySize != null)
			return displaySize.x;

		displaySize = new Point();

		WindowManager mWinMgr = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		mWinMgr.getDefaultDisplay().getSize(displaySize);

		return displaySize.x;
	}

	public int getDisplayHeight() {
		if (displaySize != null)
			return displaySize.y;

		displaySize = new Point();

		WindowManager mWinMgr = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		mWinMgr.getDefaultDisplay().getSize(displaySize);

		return displaySize.y;
	}

	public void setViews(Activity activity) {
		this.activity = activity;
	}

	public Activity getActivity() {
		return activity;
	}

	public int getScore() {
		return score;
	}

	protected void setAchievement(){
		if (score > 1000) {
			giveAchievement(1340);
			if (score > 5000){
				giveAchievement(1350);
				if (score > 10000)
					giveAchievement(1352);
			}
		}
		if (correctLettersInRow > 20) {
			giveAchievement(1348);
			if (correctLettersInRow > 80){
				giveAchievement(1356);
				if (correctLettersInRow > 500)
					giveAchievement(1354);
			}
		}
			
	}
	protected void giveAchievement(int id) {
		if (timeAttackAchievements != null) {
			SwarmAchievement achievement = timeAttackAchievements.get(id);
			if (achievement != null && achievement.unlocked == false) {
				achievement.unlock();
			}
		}
	}

	public void startSpeedRewardTimer(long timeLeft, int lenghtOfWord, int score) {
		speedReward = new PowerUp(timeLeft, currentWord.length(), score);
	}

	public void tryMultiplierPowerUp() {
		if (correctLettersInRow == 5 || correctLettersInRow == 10
				|| correctLettersInRow == 20) {
			multiplier = new PowerUp(powerUpMultiplier);
			powerUpMultiplier = multiplier
					.incrementMultiplier(powerUpMultiplier);
			multiplier.useMultiplier(correctLettersInRow, powerUpMultiplier,
					powerUpView, activity);
		}
	}
}
