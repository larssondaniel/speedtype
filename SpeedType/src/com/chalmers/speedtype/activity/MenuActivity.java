package com.chalmers.speedtype.activity;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.util.BackgroundSoundService;
import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends SwarmActivity {

	private Button newGameButton;
	private Button exitButton;
	private Button leaderboards;
	private Button achievements;
	private Button settingsButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_layout);
		
		setUpViews();
		setUpListeners();
		setUpSwarm();
		
		startService(new Intent(this, BackgroundSoundService.class));
	}

	private void setUpViews() {
		newGameButton = (Button) findViewById(R.id.new_game_button);
		leaderboards = (Button) findViewById(R.id.leaderboards);
		achievements = (Button) findViewById(R.id.achievements);
		settingsButton = (Button) findViewById(R.id.settings_button);
		exitButton = (Button) findViewById(R.id.exit_button);
	}

	private void setUpSwarm() {
		Swarm.init(this, 638, "66eebd36a2c3a1541b00530f532d16aa");
	}

	private void setUpListeners() {
		newGameButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startGame("Scrabble");
//				startGame("TimeAttack");
//				startGame("FallingWords");

			}
		});
		leaderboards.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Swarm.showLeaderboards();
			}
		});
		achievements.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Swarm.showAchievements();
			}
		});
		settingsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SettingsActivity settingsActivity = new SettingsActivity();
				startActivity(new Intent(getApplicationContext(),
						settingsActivity.getClass()));
			}
		});
		exitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				System.exit(0);
			}
		});
	}

	private void startGame(String gameMode) {
		Intent intent = new Intent(this, GameActivity.class);
		
		Bundle bundle = new Bundle();
		bundle.putString("gameMode", gameMode);
		intent.putExtras(bundle);
		
		startActivity(intent);
	}

	public void onPause() {
		super.onPause();
		stopService(new Intent(this, BackgroundSoundService.class));
	}

	public void onResume() {
		super.onResume();
		startService(new Intent(this, BackgroundSoundService.class));
	}
}
