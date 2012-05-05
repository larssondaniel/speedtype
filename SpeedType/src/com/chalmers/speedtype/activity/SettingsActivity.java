package com.chalmers.speedtype.activity;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.util.Config;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;

public class SettingsActivity extends Activity {

	private final String MUSIC_VOLUME = "musicVolume";
	private final String FX_VOLUME = "fxVolume";
	private final String SAVE_SCORES = "saveScores";

	private SeekBar musicVolume;
	private SeekBar fxVolume;
	private CheckBox saveScores;
	private Button backButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.settings);
		setUpViews();
		setUpListeners();
		useConfig();
	}

	private void setUpViews() {
		musicVolume = (SeekBar) findViewById(R.id.seekBar1);
		fxVolume = (SeekBar) findViewById(R.id.seekBar2);
		saveScores = (CheckBox) findViewById(R.id.checkBox1);
		backButton = (Button) findViewById(R.id.button1);
	}

	private void setUpListeners() {
		saveScores.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Config.setSaveScores(saveScores.isChecked());
			}
		});
		fxVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				Config.setFxVolume(progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});
		musicVolume
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						Config.setMusicVolume(progress);
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
					}
				});
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	public void useConfig() {

		SharedPreferences myPrefs = this.getSharedPreferences("myPrefs",
				MODE_WORLD_READABLE);
		musicVolume.setProgress(myPrefs.getInt(MUSIC_VOLUME, 70));
		fxVolume.setProgress(myPrefs.getInt(FX_VOLUME, 70));
		saveScores.setChecked(myPrefs.getBoolean(SAVE_SCORES, true));

		musicVolume.setProgress(Config.getMusicVolume());
		fxVolume.setProgress(Config.getFxVolume());
		saveScores.setChecked(Config.isSaveScores());
	}

	public void saveConfig() {
		SharedPreferences myPrefs = this.getSharedPreferences("myPrefs",
				MODE_WORLD_READABLE);
		SharedPreferences.Editor prefsEditor = myPrefs.edit();
		prefsEditor.putBoolean(SAVE_SCORES, saveScores.isChecked());
		prefsEditor.putInt(MUSIC_VOLUME, musicVolume.getProgress());
		prefsEditor.putInt(FX_VOLUME, fxVolume.getProgress());
		prefsEditor.commit();
	}

	@Override
	public void onBackPressed() {
		saveConfig();
		super.onBackPressed();
	}
}
