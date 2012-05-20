package com.chalmers.speedtype.model;

import java.beans.PropertyChangeListener;
import java.util.Map;

import com.chalmers.speedtype.util.Dictionary;
import com.swarmconnect.SwarmAchievement;
import com.swarmconnect.SwarmAchievement.GotAchievementsMapCB;

import android.view.KeyEvent;
import android.hardware.SensorEvent;

public abstract class GameModel {

	protected PropertyChangeListener listener;

	protected Word activeWord;
	protected Word nextWord;
	protected int currentCharPos;
	protected int score = 0;
	protected int multiplier = 1;

protected int correctCharsInRow;
protected int correctWordsInRow;

protected Map<Integer, SwarmAchievement> achievements;

	protected int displayWidth;
	protected int displayHeight;

	protected boolean isGameOver = false;

	public GameModel() {
		activeWord = new Word(Dictionary.getNextWord());
		nextWord = new Word(Dictionary.getNextWord());
		GotAchievementsMapCB callback = new GotAchievementsMapCB() {

			public void gotMap(Map<Integer, SwarmAchievement> achievements1) {

				// Store the map of achievements somewhere to be used later.
				achievements = achievements1;
			}
		};
		SwarmAchievement.getAchievementsMap(callback);
	}

	public void addChangeListener(PropertyChangeListener newListener) {
		listener = newListener;
	}

	public Word getActiveWord() {
		return activeWord;
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

	protected void incCorrectCharsInRow(){
		correctCharsInRow++;
	}
	protected void resetCorrectCharsInRow(){
		correctCharsInRow = 0;
	}
	
	protected void incCorrectWordsInRow(){
		correctWordsInRow++;
	}
	
	protected void resetCorrectWordsInRow(){
		correctWordsInRow = 0;
	}
	
	protected void onCorrectChar(){
		incCorrectCharsInRow();
	}
	
	protected void onCorrectWord(){
		incCorrectWordsInRow();
	}
	
	protected void onIncorrectChar(){
		resetCorrectCharsInRow();
		resetCorrectWordsInRow();
	}

	protected void incCurrentCharPos() {
		currentCharPos++;
	}

	protected void giveAchievement(int id) {
		if (achievements != null) {
			SwarmAchievement achievement = achievements.get(id);
			if (achievement != null && achievement.unlocked == false) {
				achievement.unlock();
			}
		}
	}

	protected void incScore(int i) {
		score += i;
		checkAchievementsPrerequisites();
	}

	protected void checkAchievementsPrerequisites(){
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
	
	protected boolean isWordComplete() {
		return currentCharPos == activeWord.length() - 1;
	}

	protected void updateWord() {
		activeWord = nextWord;
		nextWord = new Word(Dictionary.getNextWord());
		currentCharPos = 0;
		listener.propertyChange(null);
	}

	public void onSensorChanged(SensorEvent event) {
		return;
	}

	public abstract void update();


	public abstract void onInput(KeyEvent event);

	public abstract int getLayoutId();

	public abstract int getViewId();

	public abstract boolean isContinuous();

	public abstract boolean isSensorDependent();

	public void setDisplaySize(int w, int h) {
		displayWidth = w;
		displayHeight = h;
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public void activatePowerUp(PowerUp powerUp) {
		powerUp.usePowerUp();
	}
	
	public abstract String getManual();

}