package com.chalmers.speedtype.model;

import android.view.KeyEvent;

import com.chalmers.speedtype.R;

public class TimeAttackModel extends GameModel {

	private static final int LAYOUT_ID = R.layout.time_attack_layout;
	private static final int VIEW_ID = R.id.time_attack_view;

	private int timeLeft = 10000;
	private boolean correctInput;
	private long speedRewardTimeStart;

	private PowerUp speedRewardPowerUp;

	public TimeAttackModel() {
		super();
		initTimer();
		correctInput = false;
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

	protected void incTimeLeft(int timeLeft) {
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	@Override
	public int getLayoutId() {
		return LAYOUT_ID;
	}

	@Override
	public int getViewId() {
		return VIEW_ID;
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

	@Override
	public void onInput(KeyEvent event) {
		correctInput = true;
		char inputChar = Character.toLowerCase((char) event.getUnicodeChar());
		if (activeWord.charAt(currentCharPos) == inputChar) {
			incScore(1);
			if (isWordComplete()) {
				if (isFastEnough()) {
					speedRewardPowerUp = new SpeedRewardPowerUp(this);
					speedRewardPowerUp.usePowerUp();
				}
				speedRewardTimeStart = System.currentTimeMillis();
				setTimeLeft(timeLeft + 1000 * activeWord.length());
				updateWord();
			} else {
				incCurrentCharPos();
			}
		} else {
			setCorrectInputReport(false);
		}
	}

	@Override
	public boolean isContinuous() {
		return false;
	}

	@Override
	public boolean isSensorDependent() {
		return false;
	}

	public boolean isFastEnough() {
		return System.currentTimeMillis() - speedRewardTimeStart < activeWord
				.length() * 1000 ? true : false;
	}

	@Override
	public void update() {
	}
}