package com.chalmers.speedtype.model;

import java.beans.PropertyChangeListener;

import com.chalmers.speedtype.util.Dictionary;

import android.view.KeyEvent;

public abstract class Model {

	protected PropertyChangeListener listener;
	
	protected Word activeWord;
	protected Word nextWord;
	protected int currentCharPos;
	
	protected int score = 0;
	
	public Model(){
		activeWord = Dictionary.getNextWord();
		nextWord = Dictionary.getNextWord();
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
	
	public int getScore(){
		return score;
	}
	
	public int getCurrentCharPos(){
		return currentCharPos;
	}
	
	protected void incCurrentCharPos(){
		currentCharPos++;
		listener.propertyChange(null);
	}
	
	protected void incScore(int i){
		score+= i;
	}
	
	protected boolean isWordComplete(){
		return currentCharPos == activeWord.length() -1;
	}
	
	protected void updateWord(){
		activeWord = nextWord;
		nextWord = Dictionary.getNextWord();
		currentCharPos = 0;
		listener.propertyChange(null);
	}
	

	public abstract void onInput(KeyEvent event);	
	public abstract int getLayoutId();
	public abstract int getViewId();
}
