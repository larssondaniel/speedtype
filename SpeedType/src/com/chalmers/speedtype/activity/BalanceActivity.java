package com.chalmers.speedtype.activity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.controller.Controller;
import com.chalmers.speedtype.model.BalanceModel;

public class BalanceActivity extends GameMode {
	TextView xCoor;
	TextView yCoor;
	TextView zCoor;

	private Controller controller;
	private BalanceModel model;
	private TextView wordView;
	private TextView nextWordView;
	private TextView timeView;
	private TextView scoreView;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		controller = new Controller();
		model = new BalanceModel(this);
		controller.setModel(model);

		setContentView(R.layout.balance);
		setUpViews();
		setUpInput();
		model.setViews(wordView, nextWordView, timeView, scoreView);

		OrientationListener ol = new OrientationListener(10, xCoor, yCoor,
				zCoor);
		ol.start();
	}

	private void setUpViews() {
		wordView = (TextView) findViewById(R.id.word);
		nextWordView = (TextView) findViewById(R.id.next_word);
		timeView = (TextView) findViewById(R.id.time);
		scoreView = (TextView) findViewById(R.id.score);

		xCoor = (TextView) findViewById(R.id.xcoor);
		yCoor = (TextView) findViewById(R.id.ycoor);
		zCoor = (TextView) findViewById(R.id.zcoor);

		wordView.setText(model.getCurrentWord());
		nextWordView.setText(model.getNextWord());
		scoreView.setText("0");
		timeView.setText("10.0");
	}

	protected void setUpInput() {
		EditText input = (EditText) findViewById(R.id.input_edit_text);
		input.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				controller.onTextChanged(s);
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
			}
		});
	}

	public class OrientationListener implements SensorEventListener {

		int rate;
		SensorManager sensorManager;
		TextView xView;
		TextView yView;
		TextView zView;

		public OrientationListener(int rate, TextView xView, TextView yView,
				TextView zView) {
			this.rate = rate;
			this.xView = xView;
			this.yView = yView;
			this.zView = zView;
			sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		}

		public void start() {
			sensorManager.registerListener(this,
					sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					rate);
			sensorManager.registerListener(this,
					sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
					rate);
		}

		float[] R = new float[16]; // Identity matrix
		float[] I = new float[16]; // Rotation matrix
		float[] magField = null; // Magnetic field values
		float[] gravity = null; // Accelerometer values

		float[] orientationValues = { 0f, 0f, 0f }; // Result of orientation
													// calculation

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

		}

		public void onSensorChanged(SensorEvent event) {

			switch (event.sensor.getType()) {
			case Sensor.TYPE_MAGNETIC_FIELD:
				magField = event.values.clone();
				break;
			case Sensor.TYPE_ACCELEROMETER:
				gravity = event.values.clone();
				break;
			}

			if (magField != null && gravity != null) {
				SensorManager.getRotationMatrix(R, I, gravity, magField);
				SensorManager.getOrientation(R, orientationValues);
			}

			xView.setText(SensorManager.getOrientation(R, orientationValues)[0]
					+ "");
			yView.setText(SensorManager.getOrientation(R, orientationValues)[1]
					+ "");
			zView.setText(SensorManager.getOrientation(R, orientationValues)[2]
					+ "");
		}
	}
}