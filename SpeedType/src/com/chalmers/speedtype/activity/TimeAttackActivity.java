package com.chalmers.speedtype.activity;

import java.util.Map;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.application.SpeedTypeApplication;
import com.chalmers.speedtype.controller.Controller;
import com.chalmers.speedtype.model.TimeAttackModel;
import com.swarmconnect.SwarmAchievement;
import com.swarmconnect.SwarmAchievement.GotAchievementsMapCB;
import com.swarmconnect.SwarmLeaderboard;
import com.swarmconnect.SwarmLeaderboard.GotLeaderboardCB;

public class TimeAttackActivity extends GameMode {

	private SpeedTypeApplication app;
	private Controller controller;
	private TimeAttackModel model;
	private TextView wordView;
	private TextView nextWordView;
	private TextView timeView;
	private TextView scoreView;
	private TextView powerUpView;
	private TextView speedBonusView;
	private TextView speedBonusScoreView;
	private SwarmLeaderboard timeAttackLeaderboard;
	private Map<Integer, SwarmAchievement> timeAttackAchievements;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		app = (SpeedTypeApplication) getApplication();
		//controller = new Controller();

		setUpSwarm();

		/*model = new TimeAttackModel(this,
				timeAttackLeaderboard, timeAttackAchievements);

		controller.setModel(model);
		controller.setActivity(this);
		setContentView(R.layout.time_attack);
		setUpViews();
		setUpInput();
		model.setViews(this);*/
	}

	private void setUpViews() {
		/*ordView = (TextView) findViewById(R.id.word);
		nextWordView = (TextView) findViewById(R.id.next_word);
		timeView = (TextView) findViewById(R.id.time);
		scoreView = (TextView) findViewById(R.id.score);
		powerUpView = (TextView) findViewById(R.id.multiplier);
		speedBonusView = (TextView) findViewById(R.id.speed_bonus);
		speedBonusScoreView = (TextView) findViewById(R.id.speed_bonus_score);*/
	}

	protected void setUpInput() {
		EditText input = (EditText) findViewById(R.id.input_edit_text);
		input.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				//controller.onTextChanged(s);
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
			}
		});
	}

	private void setUpSwarm() {
		GotLeaderboardCB leaderboardCB = new GotLeaderboardCB() {
			public void gotLeaderboard(SwarmLeaderboard leaderboard) {

				if (leaderboard != null) {

					// Save the leaderboard for later use
					timeAttackLeaderboard = leaderboard;
				}
			}
		};
		SwarmLeaderboard.getLeaderboardById(826, leaderboardCB);

		GotAchievementsMapCB achievementCB = new GotAchievementsMapCB() {

			public void gotMap(Map<Integer, SwarmAchievement> achievements) {

				// Store the map of achievements somewhere to be used later.
				timeAttackAchievements = achievements;
			}
		};
		SwarmAchievement.getAchievementsMap(achievementCB);
	}
}