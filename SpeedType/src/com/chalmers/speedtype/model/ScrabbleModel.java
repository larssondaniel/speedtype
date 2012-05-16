package com.chalmers.speedtype.model;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import android.os.CountDownTimer;
import android.view.KeyEvent;

import com.chalmers.speedtype.R;


public class ScrabbleModel extends Model {
	private static final int LAYOUT_ID = R.layout.scrabble_layout; 
	private static final int VIEW_ID = R.id.scrabble_view;
		
	CountDownTimer timer;
	public int timeLeft = 10000;
	
	private boolean correctInput;
	
	public ScrabbleModel(){
		super();
		initTimer();
		correctInput = false;
	}
	

	public Word getActiveScrabbledWord(){
		return scrabble(activeWord);
	}
	
	public Word getNextScrabbledWord(){
		return scrabble(nextWord);
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
	
	public int getTimeLeft(){
		return timeLeft;
	}
	
    private Word scrabble(CharSequence input){
    	String s = input.toString();
        s = s.toLowerCase();
        List<Character> string = new ArrayList<Character>();
        Random random = new Random();
        int randomNumber;
        String scrabble = "";
        while(s.length() > 0){
        	string.add(s.charAt(s.length() - 1));
        	s = s.substring(0, s.length() -1);
        }
        while(string.size() > 0){
            randomNumber = random.nextInt(string.size());
            scrabble = scrabble + (string.remove(randomNumber));
        }
        return (new Word(scrabble));
    }
    
	private void setCorrectInputReport(boolean b){
		correctInput = b;
	}
	
	public boolean correctInputReport(){
		if(correctInput){
			return true;
		} else {
			setCorrectInputReport(true);
			return false;
		}
	}

	@Override
	public void onInput(KeyEvent event) {
		//TODO Copy paste so far, look it over and adjust.
		correctInput = true;
		char inputChar = Character.toLowerCase((char) event.getUnicodeChar());
		if (activeWord.charAt(currentCharPos) == inputChar) {
			incScore(1);
			if (isWordComplete()) {
				setTimeLeft(timeLeft + 1000*activeWord.length());
				updateWord();
			} else {
				incCurrentCharPos();
			}
		} else {
			setCorrectInputReport(false);
		}
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
	public boolean isContinuous() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSensorDependent() {
		// TODO Auto-generated method stub
		return false;
	}
}
