package com.chalmers.speedtype.activity;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.controller.Controller;
import com.chalmers.speedtype.model.GameModel;
import com.chalmers.speedtype.util.GameFactory;
import com.chalmers.speedtype.util.Util;
import com.chalmers.speedtype.view.GameView;
import com.swarmconnect.SwarmActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.widget.TextView;

public class GameActivity extends SwarmActivity implements SensorEventListener {

	private GameModel model;
	private GameView view;
	private Controller controller;

	private SensorManager sensorManager;
	private Sensor sensor;

	private TextView statusText;
	//private RelativeLayout overlayLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		String gameMode = getIntent().getExtras().getString("gameMode");
		
		setUpUtil();
		initGameMode(gameMode);
		setUpListeners();
	}
	
	private void setUpUtil() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Util.setConstants(metrics);
		
		Util.setResources(getResources());
		WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		Util.setDisplay(windowManager.getDefaultDisplay());
	}

	private void initGameMode(String gameMode) {
		model = GameFactory.createGameMode(this, gameMode);
		setUpViews();
		view.setModel(model);

		controller = new Controller(model, new Handler() {
			@Override
			public void handleMessage(Message m) {
				//overlayLayout.setVisibility(m.getData().getInt("visibility"));
				//statusText.setVisibility(m.getData().getInt("visibility"));
				//statusText.setText(m.getData().getString("text"));
			}
		});
		controller.startGame();
	}

	private void setUpViews() {
		setContentView(model.getLayoutId());
		view = (GameView) findViewById(model.getViewId());
		//statusText = (TextView) findViewById(R.id.status_text);
		//overlayLayout = (RelativeLayout) findViewById(R.id.overlay_layout);
	}

	private void setUpListeners() {
		// Set keyListener
		view.setOnKeyListener(new OnKeyListener() {
			
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() != KeyEvent.ACTION_UP)
					return true;
				return controller.onKey(event);
			}
		});
		
		// Set sensor listener if needed
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		if (model.isSensorDependent())
			sensorManager.registerListener(this, sensor,
					SensorManager.SENSOR_DELAY_FASTEST);
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		if (!hasWindowFocus)
			controller.pause();
	}

	protected void onResume() {
		super.onResume();
		if(model.isSensorDependent())
			sensorManager.registerListener(this, sensor,
					SensorManager.SENSOR_DELAY_FASTEST);
	}

	protected void onPause() {
		super.onPause();
		if(model.isSensorDependent())
			sensorManager.unregisterListener(this);
		controller.pause();
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// send to controller
	}

	public void onSensorChanged(SensorEvent event) {
		controller.onSensorChanged(event);
	}
}