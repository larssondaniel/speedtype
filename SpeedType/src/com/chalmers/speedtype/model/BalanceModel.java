package com.chalmers.speedtype.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
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

	private Bitmap ballImage;
	private float xOrigin;
	private float yOrigin;
	private float sensorX;
	private long sensorTimeStamp;
	private long cpuTimeStamp;
	private float horizontalBound;
	private float verticalBound;
	private final Particle particle = new Particle(ballFriction,
			horizontalBound, verticalBound);

	public BalanceModel() {
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

	public Particle getParticle() {
		return particle;
	}

	public void updateParticle(float sx, long now) {
		particle.update(sx, now);
	}

	public float getVerticalBound() {
		return verticalBound;
	}

	public void setVerticalBound(float verticalBound) {
		this.verticalBound = verticalBound;
	}

	public void setHorizontalBound(float horizontalBound) {
		this.horizontalBound = horizontalBound;
	}

	@Override
	public void onInput(KeyEvent event) {
		char inputChar = (char) event.getUnicodeChar();
		if (activeWord.charAt(currentCharPos) == inputChar) {
			incScore(1);
			if (isWordComplete()) {
				updateWord();
			} else {
				incCurrentCharPos();
			}
		}
	}
}
