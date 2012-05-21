package com.chalmers.speedtype.model;

import android.hardware.SensorEvent;
import android.view.KeyEvent;

import com.chalmers.speedtype.R;

public class TimeAttackModel extends GameModel {

	private static final int LAYOUT_ID = R.layout.time_attack_layout;
	private static final int VIEW_ID = R.id.time_attack_view;

	private static final int LEADERBOARD_ID = 826;

	private static final String manual = "Type the words as fast as possible!";

	private static final int UPDATE_FREQUENCY = 50;

	private int timeLeft = 10000;
	private boolean correctInput;

	private int gameSpeed = 600;

	private long lastUpdateMillis;

	public TimeAttackModel() {
		super();
		correctInput = false;
		speedRewardPowerUp = new SpeedRewardPowerUp(this);
		multiplierPowerUp = new MultiplierPowerUp(this);
	}

	protected void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	private void setCorrectInputReport(boolean b) {
		correctInput = b;
	}

	public boolean correctInputReport() {
		if (correctInput) {
			return true;
		} else {
			setCorrectInputReport(true);
			return false;
		}
	}

	protected void onCorrectChar() {
		super.onCorrectChar();
		incScore(1);
	}

	protected void onCorrectWord() {
		super.onCorrectWord();
		if (isFastEnough()) {
			speedRewardPowerUp.usePowerUp();
		}
		setTimeLeft(timeLeft + getGameSpeed() * activeWord.length());
		if (correctWordsInRow % 2 == 0) {
			multiplierPowerUp.usePowerUp();
		}
		updateWord();
	}

	private int getGameSpeed() {
		gameSpeed -= 2;
		return gameSpeed;
	}

	protected void onIncorrectChar() {
		super.onIncorrectChar();
		setCorrectInputReport(false);
		multiplierPowerUp.endPowerUp();
	}

	public boolean isFastEnough() {
		return System.currentTimeMillis() - speedRewardTimeStart < activeWord
				.length() * 1000 ? true : false;
	}

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

	@Override
	public void update() {
		if (System.currentTimeMillis() - lastUpdateMillis > UPDATE_FREQUENCY) {
			if (lastUpdateMillis != 0) {
				setTimeLeft((int) (timeLeft - (System.currentTimeMillis() - lastUpdateMillis)));
				listener.propertyChange(null);
			}
			lastUpdateMillis = System.currentTimeMillis();
		}

		if (timeLeft < 0)
			isGameOver = true;
	}

	@Override
	public String getManual() {
		return manual;
	}

	@Override
	public int getSwarmLeaderBoardID() {
		return LEADERBOARD_ID;
	}

	@Override
	protected void onPause() {
		// No need to do anything here
	}

	@Override
	protected void onResume() {
		lastUpdateMillis = System.currentTimeMillis();
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
		return true;
	}

	@Override
	public boolean isSensorDependent() {
		return false;
	}

	@Override
	public void onSensorChanged(SensorEvent event, int displayRotation) {
	}
}