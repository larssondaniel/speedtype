package com.chalmers.speedtype.model;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.view.KeyEvent;
import android.view.Surface;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.util.Util;

public class BalanceModel extends Model {

	private static final int LAYOUT_ID = R.layout.balance_layout;
	private static final int VIEW_ID = R.id.balance_view;

	private static final float ballDiameter = 0.006f;
	private static final float ballFriction = 0.1f;

	private float sensorX;
	private long sensorTimeStamp;
	private long cpuTimeStamp;
	private float horizontalBound;
	private float verticalBound;
	private int timeLeft = 10000;
	private boolean correctInput;
	Particle particle;
	
	public BalanceModel() {
		super();
		initTimer();
		particle = new Particle(ballFriction,
				horizontalBound, verticalBound);
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
		//listener.propertyChange(null);
	}
	protected void incTimeLeft(int timeLeft) {
		
	}
	
	public int getTimeLeft(){
		return timeLeft;
	}

	public long getSensorTimeStamp(){
		return sensorTimeStamp;
	}
	public long getCpuTimeStamp(){
		return cpuTimeStamp;
	}
	
	public Particle getParticle() {
		return particle;
	}
	
	public float getSensorX(){
		return sensorX;
	}

	public void updateParticle(float sx, long now) {
		particle.update(sx, now);
	}

	public float getVerticalBound() {
		return verticalBound;
	}

	public void setVerticalBound(float verticalBound) {
		this.verticalBound = verticalBound;
		particle.setVerticalBound(verticalBound);
	}

	public void setHorizontalBound(float horizontalBound) {
		this.horizontalBound = horizontalBound;
		particle.setHorizontalBound(horizontalBound);
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
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
			return;
		switch (Util.getDisplay().getRotation()) {
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
		return false;
	}
	
	@Override
	public boolean isSensorDependent() {
		return true;	
	}
}