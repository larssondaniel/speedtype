package com.chalmers.speedtype.model;

//TODO Complete the implementation of the game, possible to die while balancing etc.

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.view.KeyEvent;
import android.view.Surface;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.util.Util;

public class BalanceModel extends GameModel {

	private static final int LAYOUT_ID = R.layout.balance_layout;
	private static final int VIEW_ID = R.id.balance_view;
	
	private static final int LEADERBOARD_ID = 897;

	private static final float ballFriction = 0.1f;
	private static final String manual = "Do not let the ball hit the edges of the phone!";

	private static final long UPDATE_FREQUENCY = 50;

	private float sensorX;
	private long sensorTimeStamp;
	private long cpuTimeStamp;
	private float horizontalBound;
	private float verticalBound;
	private boolean correctInput;
	private Ball ball;

	private long lastUpdateMillis;

	public BalanceModel() {
		super();
		ball = new Ball(ballFriction, horizontalBound, verticalBound);
		correctInput = false;
	}

	public long getSensorTimeStamp() {
		return sensorTimeStamp;
	}

	public long getCpuTimeStamp() {
		return cpuTimeStamp;
	}

	public Ball getBall() {
		return ball;
	}

	public float getSensorX() {
		return sensorX;
	}

	public float getVerticalBound() {
		return verticalBound;
	}

	public void setVerticalBound(float verticalBound) {
		this.verticalBound = verticalBound;
		ball.setVerticalBound(verticalBound);
	}

	public void setHorizontalBound(float horizontalBound) {
		this.horizontalBound = horizontalBound;
		ball.setHorizontalBound(horizontalBound);
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

	protected void onCorrectChar() {
		super.onCorrectChar();
		incScore(1);
	}

	protected void onCorrectWord() {
		super.onCorrectWord();
		updateWord();
	}

	protected void onIncorrectChar() {
		super.onIncorrectChar();
		setCorrectInputReport(false);
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
		return true;
	}

	@Override
	public void update() {
		if (System.currentTimeMillis() - lastUpdateMillis > UPDATE_FREQUENCY) {
			if (ball.getPosX() <= -horizontalBound
					|| ball.getPosX() >= horizontalBound) {
				isGameOver = true;
			}
			lastUpdateMillis = System.currentTimeMillis();
			
			float sensorX = getSensorX();
			long now = getSensorTimeStamp() + (System.nanoTime() - getCpuTimeStamp());
			
			ball.update(sensorX, now);
			
			listener.propertyChange(null);
		}
	}

	@Override
	public int getSwarmLeaderBoardID() {
		return LEADERBOARD_ID;
	}

	public String getManual() {
		return manual;
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
	}
}