package com.chalmers.speedtype.model;

import android.os.CountDownTimer;
import android.view.KeyEvent;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.util.Dictionary;

public class ScrabbleModel extends GameModel {
	private static final int LAYOUT_ID = R.layout.scrabble_layout; 
	private static final int VIEW_ID = R.id.scrabble_view;
		
	CountDownTimer timer;
	public int timeLeft = 10000;
	
	private boolean correctInput;
	private boolean getNewWord = true;
	private Word activeScrabbledWord;
	
	
	/**
	 * Creates a new scrabbleModel. 
	 * Also starts the timer associated with the Model.
	 */
	public ScrabbleModel(){
		super();
		initTimer();
		correctInput = false;
	}
	
	/**
	 * Makes the current ActiveWord into a new scrabbled (aka randomized) word.
	 * Saves the scrabbled Word into activeScrabbledWord, if called after the user
	 * entered a correct word it fetches the next one. Otherwise it returns the saved value.
	 * @return Word
	 */
	public Word getActiveScrabbledWord(){
		if(getNewWord == true){
			activeScrabbledWord = Dictionary.scrabble(this.getActiveWord());
			getNewWord = false;
			return activeScrabbledWord;
		} else {
			return activeScrabbledWord;
		}
	}
	

	private void initTimer() {
		Runnable runnable = new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(100);
						setTimeLeft(timeLeft - 100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		new Thread(runnable).start();
	}
	
	protected void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
		listener.propertyChange(null);
	}
	
	/**
	 * Returns the current timeLeft. 
	 * @return int
	 */
	public int getTimeLeft(){
		return timeLeft;
	}
	    
	private void setCorrectInputReport(boolean b){
		correctInput = b;
	}
	
	/**
	 * If correctInput is true it also returns true. 
	 * If false, the function sets the inputReport to true and returns false.
	 * @return boolean
	 */
	public boolean correctInputReport(){
		if(correctInput){
			return true;
		} else {
			setCorrectInputReport(true);
			return false;
		}
	}	

	/**
	 * Handles input from the user. 
	 * If correct character is written the score and timeLeft will increase,
	 * as well as the currentCharPos.
	 */
	@Override
	public void onInput(KeyEvent event) {
		correctInput = true;
		char inputChar = Character.toLowerCase((char) event.getUnicodeChar());
		if (activeWord.charAt(currentCharPos) == inputChar) {
			onCorrectChar();
			if (isWordComplete()) {
				onCorrectWord();
			} else {
				incCurrentCharPos();
			}
		} else {
			onIncorrectChar();
		}
	}
	protected void onCorrectChar(){
		super.onCorrectChar();
		incScore(1);
	}
	
	protected void onCorrectWord(){
		super.onCorrectWord();
		getNewWord = true;
		setTimeLeft(timeLeft + 1000*activeWord.length());
		updateWord();
	}
	
	protected void onIncorrectChar(){
		super.onIncorrectChar();
		setCorrectInputReport(false);
	}
	
	/**
	 * Returns the Layout_ID for this model.
	 * @return int
	 */
	@Override
	public int getLayoutId() {
		return LAYOUT_ID;
	}
	
	/**
	 * Returns the View_ID for this model.
	 * @return int
	 */
	@Override
	public int getViewId() {
		return VIEW_ID;
	}
	
	/**
	 * TODO Write javadoc.
	 */
	@Override
	public boolean isContinuous() {
		return false;
	}

	/**
	 * TODO Write javadoc.
	 */
	@Override
	public boolean isSensorDependent() {
		return false;
	}

	/**
	 * TODO Write javadoc.
	 */
	@Override
	public void update() {
	}
}
