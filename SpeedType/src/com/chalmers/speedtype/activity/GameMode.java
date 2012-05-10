package com.chalmers.speedtype.activity;

import com.chalmers.speedtype.controller.Controller;
import com.chalmers.speedtype.model.Model;
import com.chalmers.speedtype.view.GameView;
import com.swarmconnect.SwarmActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.WindowManager;

public class GameMode extends SwarmActivity implements SensorEventListener{
	
	private Model model;
	private GameView view;
	private Controller controller;
	
	private SensorManager sensorManager;
	private Sensor sensor;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        String gameMode = getIntent().getExtras().getString("gameMode");
        
        initGameMode(gameMode);
        setUpListeners();
	}
	
	private void initGameMode(String gameMode) {
		model = GameModeFactory.createGameMode(this, gameMode);
        controller = new Controller(model);

		setContentView(model.getLayoutId());
		view = (GameView) findViewById(model.getViewId());
		view.setModel(model);
		//controller.start();
	}

	private void setUpListeners() {
		view.setOnKeyListener(new OnKeyListener(){
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				return controller.onKey(event);
			}
		});
		
		SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
	}
	
	protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    	// send to controller
    }

    public void onSensorChanged(SensorEvent event) {
    	// send to controller
    }
}