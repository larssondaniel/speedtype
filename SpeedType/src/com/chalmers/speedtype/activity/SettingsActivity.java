package com.chalmers.speedtype.activity;
//TODO Get settings to work with sound.

import com.chalmers.speedtype.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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

	SharedPreferences preferences;
	Editor prefsEditor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		preferences = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
		prefsEditor = preferences.edit();
		setContentView(R.layout.settings_layout);
		setUpViews();
		setUpListeners();
		usePreferences();
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
				prefsEditor.putBoolean(SAVE_SCORES, saveScores.isChecked());
				prefsEditor.commit();
			}
		});
		fxVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				prefsEditor.putInt(FX_VOLUME, fxVolume.getProgress());
				prefsEditor.commit();
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});
		musicVolume
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						prefsEditor.putInt(MUSIC_VOLUME,
								musicVolume.getProgress());
						prefsEditor.commit();
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
		fxVolume.setProgress(preferences.getInt(FX_VOLUME, 70));
		saveScores.setChecked(preferences.getBoolean(SAVE_SCORES, true));
	}
}
