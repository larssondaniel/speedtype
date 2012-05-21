package com.chalmers.speedtype.model;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.view.KeyEvent;
import android.view.Surface;
import com.chalmers.speedtype.R;

public class BalanceModel extends GameModel {

	private static final int LAYOUT_ID = R.layout.balance_layout;
	private static final int VIEW_ID = R.id.balance_view;
	private static final int LEADERBOARD_ID = 897;
	private static final long UPDATE_FREQUENCY = 50;
	private static final String manual = "Do not let the ball hit the edges of the phone!";

	private float sensorX;
	private long sensorTimeStamp;
	private long cpuTimeStamp;
	private boolean correctInput;
	private Ball ball;

	private long lastUpdateMillis;

	public BalanceModel() {
		super();
		ball = new Ball();
		correctInput = false;
	}

	public Ball getBall() {
		return ball;
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
	public void update() {
		if (System.currentTimeMillis() - lastUpdateMillis > UPDATE_FREQUENCY) {
			long now = sensorTimeStamp + (System.nanoTime() - cpuTimeStamp);
			ball.updatePositions(sensorX, now);
			ball.resolveCollisionWithBounds();

			if (ball.getPosX() <= -ball.horizontalBound
					|| ball.getPosX() >= ball.horizontalBound)
				isGameOver = true;

			lastUpdateMillis = System.currentTimeMillis();

			listener.propertyChange(null);
		}
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
	protected void onCorrectChar() {
		super.onCorrectChar();
		incScore(1);
	}

	@Override
	protected void onCorrectWord() {
		super.onCorrectWord();
		updateWord();
	}

	@Override
	protected void onIncorrectChar() {
		super.onIncorrectChar();
		setCorrectInputReport(false);
	}

	@Override
	public boolean isContinuous() {
		return true;
	}

	@Override
	public boolean isSensorDependent() {
		return true;
	}

	@Override
	public void onSensorChanged(SensorEvent event, int displayRotation) {
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
			return;
		switch (displayRotation) {
		case Surface.ROTATION_0:
			sensorX = event.values[0];
			break;
		case Surface.ROTATION_90:
			sensorX = -event.values[1];
			break;
		case Surface.ROTATION_180:
			sensorX = -event.values[0];
			break;
		case Surface.ROTATION_270:
			sensorX = event.values[1];

			break;
		}

		sensorTimeStamp = event.timestamp;
		cpuTimeStamp = System.nanoTime();
	}

	@Override
	public int getSwarmLeaderBoardID() {
		return LEADERBOARD_ID;
	}

	public String getManual() {
		return manual;
	}

	public int getLayoutId() {
		return LAYOUT_ID;
	}

	@Override
	public int getViewId() {
		return VIEW_ID;
	}

	@Override
	protected void onPause() {
		// Not used
	}

	@Override
	protected void onResume() {
		// Not used
	}
}