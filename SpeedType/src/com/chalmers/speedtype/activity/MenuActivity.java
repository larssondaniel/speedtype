package com.chalmers.speedtype.activity;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.application.SpeedTypeApplication;
import com.chalmers.speedtype.util.BackgroundSoundService;
import com.chalmers.speedtype.util.Dictionary;
import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActivity;

import android.app.Application;
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
	
	private SpeedTypeApplication app;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		app = (SpeedTypeApplication) getApplication();
		
		setUpViews();
		setUpListeners();
	}

	private void setUpViews() {
		newGameButton = (Button) findViewById(R.id.new_game_button);
		leaderboards = (Button) findViewById(R.id.leaderboards);
		achievements = (Button) findViewById(R.id.achievements);
		settingsButton = (Button) findViewById(R.id.settings_button);
		exitButton = (Button) findViewById(R.id.exit_button);
	}

	private void setUpListeners() {
		newGameButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startGame();

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

	private void startGame() {
		Dictionary.init(app.getDatabase());
		
		Intent game = new Intent(this, new ExampleGameMode().getClass());
		game.putExtra("gameMode", "Example");
		startActivity(game);
	}
}
