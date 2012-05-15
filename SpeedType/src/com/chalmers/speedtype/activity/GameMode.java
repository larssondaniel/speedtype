package com.chalmers.speedtype.activity;

import com.chalmers.speedtype.controller.Controller;
import com.chalmers.speedtype.model.Model;
import com.chalmers.speedtype.util.Util;
import com.chalmers.speedtype.view.GameView;
import com.swarmconnect.SwarmActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.WindowManager;

public class GameMode extends SwarmActivity implements SensorEventListener {

	private Model model;
	private GameView view;
	private Controller controller;

	private SensorManager sensorManager;
	private WindowManager windowManager;
	private Sensor sensor;
	private Display display;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		String gameMode = getIntent().getExtras().getString("gameMode");
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Util.setConstants(metrics);
		Util.setResources(getResources());

		initGameMode(gameMode);
		setUpListeners();

	}

	private void initGameMode(String gameMode) {
		model = GameModeFactory.createGameMode(this, gameMode);
		controller = new Controller(model);

		setContentView(model.getLayoutId());
		view = (GameView) findViewById(model.getViewId());
		view.setModel(model);
		controller.startGame();
	}

	private void setUpListeners() {
		view.setOnKeyListener(new OnKeyListener() {
			
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(event.getAction() != KeyEvent.ACTION_UP)
					return true;
				return controller.onKey(event);
			}
		});

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(this, sensor,
				SensorManager.SENSOR_DELAY_FASTEST);
		
		
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
		Util.setDisplay(display);
	}
	
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// send to controller
	}

	public void onSensorChanged(SensorEvent event) {
		controller.onSensorChanged(event);
	}
}