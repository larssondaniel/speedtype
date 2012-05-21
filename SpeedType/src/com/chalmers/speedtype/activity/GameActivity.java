package com.chalmers.speedtype.activity;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.application.SpeedTypeApplication;
import com.chalmers.speedtype.controller.Controller;
import com.chalmers.speedtype.factory.GameFactory;
import com.chalmers.speedtype.model.GameModel;
import com.chalmers.speedtype.view.GameView;
import com.swarmconnect.SwarmActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameActivity extends SwarmActivity {

	private GameModel model;
	private GameView view;
	private Controller controller;

	private SensorEventListener sensorEventListener;
	private SensorManager sensorManager;
	private Sensor sensor;

	private TextView statusText;
	private TextView manualText;
	private TextView startText;
	private RelativeLayout overlayLayout;

	private SpeedTypeApplication app;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String gameMode = getIntent().getExtras().getString("gameMode");
		app = (SpeedTypeApplication) getApplication();

		startService(app.getbackgroundSoundServiceIntent());
		initGameMode(gameMode);
		setUpListeners();
	}

	private void initGameMode(String gameMode) {
		model = GameFactory.createGameMode(this, gameMode);

		setUpViews();
		view.setModel(model);
		controller = new Controller(model, this, new Handler() {
			@Override
			public void handleMessage(Message m) {

				overlayLayout.setVisibility(m.getData().getInt("visibility"));
				statusText.setText(m.getData().getString("text"));
				manualText.setText(m.getData().getString("manualText"));
				startText.setText(m.getData().getString("startText"));

			}
		});
		controller.startGame();
	}

	private void setUpViews() {
		setContentView(model.getLayoutId());
		view = (GameView) findViewById(model.getViewId());
		statusText = (TextView) findViewById(R.id.status_text);
		overlayLayout = (RelativeLayout) findViewById(R.id.overlay_layout);
		manualText = (TextView) findViewById(R.id.manual_text);
		startText = (TextView) findViewById(R.id.start_text);
	}

	private void setUpListeners() {
		view.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() != KeyEvent.ACTION_UP)
					return true;
				return controller.onKey(event);
			}
		});

		view.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (model.isGameOver() == true) {
					Intent intent = new Intent(getApplicationContext(),
							MenuActivity.class);
					startActivity(intent);
				}
				return controller.onTouch(event);
			}
		});

		sensorEventListener = new SensorEventListener() {
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
			}

			public void onSensorChanged(SensorEvent event) {
				WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
				controller.onSensorChanged(event, wm.getDefaultDisplay()
						.getRotation());
			}
		};

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		if (model.isSensorDependent())
			sensorManager.registerListener(sensorEventListener, sensor,
					SensorManager.SENSOR_DELAY_FASTEST);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
		startActivity(intent);
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		if (!hasWindowFocus)
			controller.pause();
	}

	protected void onResume() {
		super.onResume();
		if (model.isSensorDependent())
			sensorManager.registerListener(sensorEventListener, sensor,
					SensorManager.SENSOR_DELAY_FASTEST);

	}

	protected void onPause() {
		super.onPause();
		if (model.isSensorDependent())
			sensorManager.unregisterListener(sensorEventListener);
		controller.pause();
		stopService(app.getbackgroundSoundServiceIntent());
	}
}