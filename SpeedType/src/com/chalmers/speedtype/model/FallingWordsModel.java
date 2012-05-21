package com.chalmers.speedtype.model;

//TODO Resuming a paused game always results in a new word, this may cause two words landing on top of each other.

import java.util.LinkedList;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.util.Dictionary;

import android.hardware.SensorEvent;
import android.view.KeyEvent;

public class FallingWordsModel extends GameModel {

	private static final int LAYOUT_ID = R.layout.falling_words_layout;
	private static final int VIEW_ID = R.id.falling_words_view;
	
	private static final int LEADERBOARD_ID = 830;

	private static final int WORD_FREQUENCY = 3000;
	private static final int UPDATE_FREQUENCY = 30;
	private static final String manual = "Type the words before they dissappear!";

	private long lastWordTimeMillis;
	private long lastUpdateMillis;

	private LinkedList<Word> visibleWords = new LinkedList<Word>();
	private double speed = 1;

	public FallingWordsModel() {
		super();
	}

	@Override
	public int getLayoutId() {
		return LAYOUT_ID;
	}

	@Override
	public int getViewId() {
		return VIEW_ID;
	}

	@Override
	public void onInput(KeyEvent event) {
		if (visibleWords.size() > 0) {
			char inputChar = (char) event.getUnicodeChar();
			if (visibleWords.getFirst().charAt(currentCharPos) == inputChar) {
				onCorrectChar();
				if (isWordComplete()) {
					onCorrectWord();
				} else {
					incCurrentCharPos();
				}
			}
			onIncorrectChar();
		}
	}

	protected void onCorrectChar() {
		super.onCorrectChar();
		incScore(1);
	}

	protected void onCorrectWord() {
		super.onCorrectWord();
		updateWord();
		incSpeed();
	}

	protected void onIncorrectChar() {
		super.onIncorrectChar();
	}

	@Override
	public boolean isContinuous() {
		return true;
	}

	@Override
	protected void updateWord() {
		visibleWords.removeFirst();
		currentCharPos = 0;
	}

	@Override
	public void update() {
		if (System.currentTimeMillis() - lastWordTimeMillis > WORD_FREQUENCY) {
			int wordSize = (int) (Math.random() * 50 + 30);
			Word newWord = new Word(Dictionary.getNextWord(), wordSize,
					(int) (Math.random() * 100), 0);
			visibleWords.addLast(newWord);

			lastWordTimeMillis = System.currentTimeMillis();
			listener.propertyChange(null);
		}

		if (System.currentTimeMillis() - lastUpdateMillis > UPDATE_FREQUENCY) {
			double speed = getSpeed();
			for (Word w : visibleWords) {
				w.setY(w.getY() + speed);
				if (w.getY() > displayHeight / 2)
					isGameOver = true;

			}

			lastUpdateMillis = System.currentTimeMillis();
			listener.propertyChange(null);
		}
	}

	private double getSpeed() {
		return speed;
	}

	private void incSpeed() {
		speed += 0.01;
	}

	@Override
	public Word getActiveWord() {
		if (!visibleWords.isEmpty())
			return visibleWords.getFirst();
		return null;
	}

	@Override
	protected boolean isWordComplete() {
		return currentCharPos == visibleWords.getFirst().length() - 1;
	}

	@SuppressWarnings("unchecked")
	public LinkedList<Word> getVisibleWords() {
		return (LinkedList<Word>) visibleWords.clone();
	}

	@Override
	public boolean isSensorDependent() {
		return false;
	}
	
	@Override
	public int getSwarmLeaderBoardID() {
		return LEADERBOARD_ID;
	}

	@Override
	public String getManual() {
		return manual;
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		// Do nothing
	}

	@Override
	protected void onPause() {
		// No need to do anything here
	}

	@Override
	protected void onResume() {
		lastWordTimeMillis = System.currentTimeMillis();
		lastUpdateMillis = System.currentTimeMillis();
	}
}