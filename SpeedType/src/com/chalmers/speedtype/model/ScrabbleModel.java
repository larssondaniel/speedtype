package com.chalmers.speedtype.model;

import android.hardware.SensorEvent;
import android.view.KeyEvent;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.util.Dictionary;

public class ScrabbleModel extends GameModel {
	private static final int LAYOUT_ID = R.layout.scrabble_layout;
	private static final int VIEW_ID = R.id.scrabble_view;

	private static final int LEADERBOARD_ID = 899;

	private static final String manual = "Figure out the hidden word as fast as possible!";

	private int timeLeft = 15000;
	private long speedRewardTimeStart;
	private long lastUpdateMillis;

	private static final int UPDATE_FREQUENCY = 50;

	private boolean getNewWord = true;
	private String activeScrabbledWord;

	/**
	 * Creates a new scrabbleModel. Also starts the timer associated with the
	 * Model.
	 */
	public ScrabbleModel() {
		super();
		speedRewardPowerUp = new SpeedRewardPowerUp(this);
	}

	/**
	 * Makes the current ActiveWord into a new scrabbled (aka randomized) word.
	 * Saves the scrabbled Word into activeScrabbledWord, if called after the
	 * user entered a correct word it fetches the next one. Otherwise it returns
	 * the saved value.
	 * 
	 * @return Word
	 */
	public String getActiveScrabbledWord() {
		if (getNewWord == true) {
			activeScrabbledWord = Dictionary.scrabble(getActiveWord());
			getNewWord = false;
			return activeScrabbledWord;
		} else {
			return activeScrabbledWord;
		}
	}

	protected void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	/**
	 * Returns the current timeLeft.
	 * 
	 * @return int
	 */
	public int getTimeLeft() {
		return timeLeft;
	}

	/**
	 * Handles input from the user. If correct character is written the score
	 * and timeLeft will increase, as well as the currentCharPos.
	 */
	@Override
	public void onInput(KeyEvent event) {
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

	@Override
	protected void onCorrectChar() {
		super.onCorrectChar();
		incScore(1);
	}

	@Override
	protected void onCorrectWord() {
		super.onCorrectWord();
		if (isFastEnough()) {
			speedRewardPowerUp.usePowerUp();
		}
		speedRewardTimeStart = System.currentTimeMillis();
		getNewWord = true;
		setTimeLeft(timeLeft + 1500 * activeWord.length());
		updateWord();
	}

	@Override
	protected void onIncorrectChar() {
		super.onIncorrectChar();
	}

	public boolean getNewWord() {
		return getNewWord;
	}

	/**
	 * Returns the Layout_ID for this model.
	 * 
	 * @return int
	 */
	@Override
	public int getLayoutId() {
		return LAYOUT_ID;
	}

	/**
	 * Returns the View_ID for this model.
	 * 
	 * @return int
	 */
	@Override
	public int getViewId() {
		return VIEW_ID;
	}

	@Override
	public boolean isContinuous() {
		return true;
	}

	@Override
	public String getManual() {
		return manual;
	}

	@Override
	protected void onPause() {
		// Not used
	}

	public void update() {
		if (System.currentTimeMillis() - lastUpdateMillis > UPDATE_FREQUENCY) {
			if (lastUpdateMillis != 0) {
				setTimeLeft((int) (timeLeft - (System.currentTimeMillis() - lastUpdateMillis)));
				listener.propertyChange(null);
			}
			lastUpdateMillis = System.currentTimeMillis();
		}
		if (timeLeft < 0) {
			isGameOver = true;
		}
	}

	@Override
	protected void onResume() {
		lastUpdateMillis = System.currentTimeMillis();
	}

	@Override
	public int getSwarmLeaderBoardID() {
		return LEADERBOARD_ID;
	}

	public boolean isSensorDependent() {
		return false;
	}

	@Override
	public void onSensorChanged(SensorEvent event, int displayRotation) {
		// Do nothing
	}

	@Override
	public boolean isFastEnough() {
		return System.currentTimeMillis() - speedRewardTimeStart < activeWord
				.length() * 1000 ? true : false;
	}
}
