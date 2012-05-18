package com.chalmers.speedtype.model;

import java.beans.PropertyChangeListener;
import com.chalmers.speedtype.util.Dictionary;
import android.view.KeyEvent;
import android.hardware.SensorEvent;

public abstract class GameModel {

	protected PropertyChangeListener listener;

	protected Word activeWord;
	protected Word nextWord;
	protected int currentCharPos;
	protected int score = 0;
	protected int multiplier = 1;

	protected int displayWidth;
	protected int displayHeight;

	protected boolean isGameOver = false;

	public GameModel() {
		activeWord = new Word(Dictionary.getNextWord());
		nextWord = new Word(Dictionary.getNextWord());
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

	protected void incCurrentCharPos() {
		currentCharPos++;
	}

	protected void incScore(int i) {
		score += i;
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
}