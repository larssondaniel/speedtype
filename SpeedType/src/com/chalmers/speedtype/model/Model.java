package com.chalmers.speedtype.model;

import android.view.KeyEvent;

import com.chalmers.speedtype.util.Dictionary;

public abstract class Model {

	protected Word nextWord;
	protected Word activeWord;

	protected int score = 0;
	protected int currentChar = 0;

	public Model() {
		activeWord = Dictionary.getNextWord();
		nextWord = Dictionary.getNextWord();
	}

	public Word getNextWord() {
		return nextWord;
	}
	
	public Word getActiveWord() {
		return activeWord;
	}
	
	public int getScore() {
		return score;
	}

	public int getCurrentCharNr() {
		return currentChar;
	}
	
	public void incCurrentChar() {
		currentChar++;
		currentChar %= activeWord.length();
	}
	
	public void incScore() {
		score++;
	}

	public abstract void onKey(KeyEvent event);
}
