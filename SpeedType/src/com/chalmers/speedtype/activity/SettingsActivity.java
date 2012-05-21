package com.chalmers.speedtype.activity;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.application.SpeedTypeApplication;
import com.chalmers.speedtype.util.BackgroundSoundService;
import com.chalmers.speedtype.util.BackgroundSoundService.BackgroundSoundBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;

public class SettingsActivity extends Activity {

	private final String MUSIC_VOLUME = "musicVolume";
	private final String SAVE_SCORES = "saveScores";

	private SeekBar musicVolume;
	private CheckBox saveScores;
	private Button backButton;

	SharedPreferences preferences;
	Editor prefsEditor;

	private Intent backgroundSoundServiceIntent;
	BackgroundSoundService backgroundMusicService;
	private SpeedTypeApplication app;
	boolean bound = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		preferences = getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
		prefsEditor = preferences.edit();
		setContentView(R.layout.settings_layout);
		app = (SpeedTypeApplication) getApplication();
		backgroundSoundServiceIntent = app.getbackgroundSoundServiceIntent();
		bindService(backgroundSoundServiceIntent, backgroundSoundConnection,
				Context.BIND_AUTO_CREATE);
		setUpViews();
		setUpListeners();
		usePreferences();
	}

	private void setUpViews() {
		musicVolume = (SeekBar) findViewById(R.id.seekBar1);
		saveScores = (CheckBox) findViewById(R.id.checkBox1);
		backButton = (Button) findViewById(R.id.done_button);
	}

	private void setUpListeners() {
		saveScores.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				prefsEditor.putBoolean(SAVE_SCORES, saveScores.isChecked());
				prefsEditor.commit();
			}
		});
		musicVolume
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						prefsEditor.putInt(MUSIC_VOLUME,
								musicVolume.getProgress());
						prefsEditor.commit();
						if (backgroundMusicService != null) {
							backgroundMusicService.setVolume(musicVolume
									.getProgress());
						}
					}

					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					public void onStopTrackingTouch(SeekBar seekBar) {
					}
				});
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	public void usePreferences() {
		musicVolume.setProgress(preferences.getInt(MUSIC_VOLUME, 70));
		saveScores.setChecked(preferences.getBoolean(SAVE_SCORES, true));
	}

	private ServiceConnection backgroundSoundConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder service) {
			BackgroundSoundBinder binder = (BackgroundSoundBinder) service;
			backgroundMusicService = binder.getService();
			bound = true;
		}

		public void onServiceDisconnected(ComponentName name) {
			bound = false;
		}
	};

	public void onPause() {
		super.onPause();
		if (bound) {
			unbindService(backgroundSoundConnection);
			bound = false;
		}
	}

	public void onResume() {
		super.onResume();
	}
}
