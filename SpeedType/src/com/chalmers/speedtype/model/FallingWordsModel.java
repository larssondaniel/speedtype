package com.chalmers.speedtype.model;

import java.util.ArrayList;
import java.util.LinkedList;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.util.Dictionary;

import android.view.KeyEvent;

public class FallingWordsModel extends Model {

	private static final int LAYOUT_ID = R.layout.falling_words_layout;
	private static final int VIEW_ID = R.id.falling_words_view;
	
	private static final int WORD_FREQUENCY = 3000;
	
	private LinkedList<Word> visibleWords = new LinkedList<Word>();
	private long lastWordTimeMillis;
	
	public FallingWordsModel(){
		super();
		visibleWords.addLast(Dictionary.getNextWord());
		
		lastWordTimeMillis = System.currentTimeMillis();
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
		char inputChar = (char) event.getUnicodeChar();
		if (visibleWords.getFirst().charAt(currentCharPos) == inputChar) {
			incScore(1);
			if (isWordComplete()) {
				updateWord();
			} else {
				incCurrentCharPos();
			}
		}
	}

	@Override
	public boolean isRealTime() {
		return true;
	}
	
	@Override
	protected void updateWord(){
		visibleWords.removeFirst();
		currentCharPos = 0;
	}
	
	@Override
	public void update(){
		if(System.currentTimeMillis() - lastWordTimeMillis > WORD_FREQUENCY){
			Word newWord = Dictionary.getNextWord();
			newWord.setX((int)(Math.random()*400));
			newWord.setY(0);
			visibleWords.addLast(newWord);
			
			lastWordTimeMillis = System.currentTimeMillis();
		}

		updateWordPhysics();
		listener.propertyChange(null);
	}

	@Override
	public Word getActiveWord() {
		return visibleWords.getFirst();
	}
	
	@Override
	protected boolean isWordComplete(){
		return currentCharPos == visibleWords.getFirst().length() -1;
	}
	

	private void updateWordPhysics() {
		for(Word w : visibleWords)
			w.setY(w.getY()+1);
	}
	
	public LinkedList<Word> getVisibleWords(){
		return visibleWords;
	}
}