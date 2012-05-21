package com.chalmers.speedtype.model;

import java.beans.PropertyChangeListener;
import java.util.Map;

import com.chalmers.speedtype.util.Dictionary;
import com.swarmconnect.SwarmAchievement;
import com.swarmconnect.SwarmAchievement.GotAchievementsMapCB;
import android.view.KeyEvent;
import android.hardware.SensorEvent;

public abstract class GameModel {

	public final int STATE_RUNNING = 1;
	public final int STATE_PAUSED = 2;

	protected int gameState;

	protected PropertyChangeListener listener;

	protected Word activeWord;
	protected Word nextWord;
	protected int currentCharPos;
	protected int score = 0;
	protected int multiplier = 1;

	protected long lastUpdateMillis;
	protected PowerUp speedRewardPowerUp;
	protected PowerUp multiplierPowerUp;

	protected long speedRewardTimeStart;

	protected int correctCharsInRow;
	protected int correctWordsInRow;

	protected Map<Integer, SwarmAchievement> achievements;

	protected int displayWidth;
	protected int displayHeight;

	protected boolean isGameOver = false;

	public GameModel() {
		activeWord = new Word(Dictionary.getNextWord());
		nextWord = new Word(Dictionary.getNextWord());
		gameState = STATE_RUNNING;

		GotAchievementsMapCB callback = new GotAchievementsMapCB() {
			public void gotMap(Map<Integer, SwarmAchievement> achievements1) {
				// Store the map of achievements to be used later.
				achievements = achievements1;
			}
		};

		SwarmAchievement.getAchievementsMap(callback);
	}

	public void addChangeListener(PropertyChangeListener newListener) {
		listener = newListener;
	}

	public abstract int getSwarmLeaderBoardID();

	public Word getActiveWord() {
		return activeWord;
	}

	public PowerUp getSpeedRewardPowerUp() {
		return speedRewardPowerUp;
	}

	public PowerUp multiplierPowerUp() {
		return multiplierPowerUp;
	}

	public Word getNextWord() {
		return nextWord;
	}

	public int getScore() {
		return score;
	}

	public int getCurrentCharPos() {
		return currentCharPos;
	}

	protected void incCorrectCharsInRow() {
		correctCharsInRow++;
	}

	protected void resetCorrectCharsInRow() {
		correctCharsInRow = 0;
	}

	protected void incCorrectWordsInRow() {
		correctWordsInRow++;
	}

	protected void resetCorrectWordsInRow() {
		correctWordsInRow = 0;
	}

	protected void onCorrectChar() {
		incCorrectCharsInRow();
	}

	protected void onCorrectWord() {
		incCorrectWordsInRow();
	}

	protected void onIncorrectChar() {
		resetCorrectCharsInRow();
		resetCorrectWordsInRow();
	}

	protected void incCurrentCharPos() {
		currentCharPos++;
	}

	protected void incScore(int i) {
		score += i;
		checkAchievementsPrerequisites();
	}

	protected boolean isWordComplete() {
		return currentCharPos == activeWord.length() - 1;
	}

	protected void updateWord() {
		activeWord = nextWord;
		nextWord = new Word(Dictionary.getNextWord());
		currentCharPos = 0;
		speedRewardTimeStart = System.currentTimeMillis();
		listener.propertyChange(null);
	}

	protected void giveAchievement(int id) {
		if (achievements != null) {
			SwarmAchievement achievement = achievements.get(id);
			if (achievement != null && achievement.unlocked == false) {
				achievement.unlock();
			}
		}
	}

	protected void checkAchievementsPrerequisites() {
		if (score > 1000) {
			giveAchievement(1340);
			if (score > 5000) {
				giveAchievement(1350);
				if (score > 10000) {
					giveAchievement(1352);
				}
			}
		}
	}

	public abstract void onInput(KeyEvent event);

	public abstract int getLayoutId();

	public abstract int getViewId();

	// if this returns true, update() will be called continuously
	public abstract boolean isContinuous();

	public abstract void update();

	protected abstract void onPause();

	protected abstract void onResume();

	public abstract boolean isSensorDependent();

	public abstract void onSensorChanged(SensorEvent event, int displayRotation);

	public void setDisplaySize(int w, int h) {
		displayWidth = w;
		displayHeight = h;
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameState(int state) {
		if (gameState == STATE_PAUSED && state == STATE_RUNNING) {
			gameState = state;
			onResume();
		} else if (gameState == STATE_RUNNING && state == STATE_PAUSED) {
			gameState = state;
			onPause();
		} else {
			// Do nothing
		}
	}

	public int getGameState() {
		return gameState;
	}

	public void activatePowerUp(PowerUp powerUp) {
		powerUp.usePowerUp();
	}

	public int getMultiplier() {
		return multiplier;
	}

	public abstract String getManual();

	public SpeedRewardPowerUp getSpeedReward() {
		return (SpeedRewardPowerUp) speedRewardPowerUp;
	}

	public boolean isFastEnough() {
		return System.currentTimeMillis() - speedRewardTimeStart < activeWord
				.length() * 370 ? true : false;
	}
}