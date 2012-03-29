package com.chalmers.speedtype.activity;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.controller.Controller;
import com.chalmers.speedtype.model.TimeAttackModel;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class BalanceActivity extends GameMode implements SensorEventListener {
	private SensorManager sensorManager;

	TextView xCoor; // declare X axis object
	TextView yCoor; // declare Y axis object
	TextView zCoor; // declare Z axis object

	private Controller controller;
	private TimeAttackModel model;
	private TextView wordView;
	private TextView nextWordView;
	private TextView timeView;
	private TextView scoreView;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.balance);
		setUpViews();

		controller = new Controller();
		model = new TimeAttackModel(this);
		controller.setModel(model);

		setContentView(R.layout.time_attack);
		setUpViews();
		setUpInput();
		model.setViews(wordView, nextWordView, timeView, scoreView);

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		// add listener. The listener will be HelloAndroid (this) class
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);

		/*
		 * More sensor speeds (taken from api docs) SENSOR_DELAY_FASTEST get
		 * sensor data as fast as possible SENSOR_DELAY_GAME rate suitable for
		 * games SENSOR_DELAY_NORMAL rate (default) suitable for screen
		 * orientation changes
		 */
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public void onSensorChanged(SensorEvent event) {

		// check sensor type
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

			// assign directions
			float x = event.values[0];
			float y = event.values[1];
			float z = event.values[2];

			xCoor.setText("X: " + x);
			yCoor.setText("Y: " + y);
			zCoor.setText("Z: " + z);
		}
	}

	private void setUpViews() {
		wordView = (TextView) findViewById(R.id.word);
		nextWordView = (TextView) findViewById(R.id.next_word);
		timeView = (TextView) findViewById(R.id.time);
		scoreView = (TextView) findViewById(R.id.score);

		//wordView.setText(model.getCurrentWord());
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
}