package com.chalmers.speedtype.activity;

//TODO Improve the overall layout
//TODO make it possible to choose all gamemodes.

import com.chalmers.speedtype.R;
import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MenuActivity extends SwarmActivity {

	private Button newGameButton;
	private Button exitButton;
	private Button leaderboards;
	private Button achievements;
	private Button settingsButton;
	private LinearLayout mainMenu;
	private LinearLayout gameModesMenu;
	private Button timeAttackButton;
	private Button fallingWordsButton;
	private Button balanceButton;
	private Button scrabbleButton;
	private Button backButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_layout);

		setUpViews();
		setUpListeners();
		setUpSwarm();
	}

	private void setUpViews() {
		newGameButton = (Button) findViewById(R.id.play_button);
		leaderboards = (Button) findViewById(R.id.leaderboard_button);
		achievements = (Button) findViewById(R.id.achievements_button);
		settingsButton = (Button) findViewById(R.id.settings_button);
		exitButton = (Button) findViewById(R.id.exit_button);
		mainMenu = (LinearLayout) findViewById(R.id.main_menu);
		gameModesMenu = (LinearLayout) findViewById(R.id.game_modes_menu);
		timeAttackButton = (Button) findViewById(R.id.time_attack_button);
		fallingWordsButton = (Button) findViewById(R.id.falling_words_button);
		balanceButton = (Button) findViewById(R.id.balance_button);
		scrabbleButton = (Button) findViewById(R.id.scrabble_button);
		backButton = (Button) findViewById(R.id.back_button);
	}

	private void setUpSwarm() {
		Swarm.init(this, 638, "66eebd36a2c3a1541b00530f532d16aa");
	}

	private void setUpListeners() {
		newGameButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mainMenu.setVisibility(View.INVISIBLE);
				gameModesMenu.setVisibility(View.VISIBLE);
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
		timeAttackButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startGame("TimeAttack");
			}
		});
		fallingWordsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startGame("FallingWords");
			}
		});
		balanceButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startGame("Balance");
			}
		});
		scrabbleButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startGame("Scrabble");
			}
		});
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mainMenu.setVisibility(View.VISIBLE);
				gameModesMenu.setVisibility(View.INVISIBLE);
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
	}

	public void onResume() {
		super.onResume();
	}
}
