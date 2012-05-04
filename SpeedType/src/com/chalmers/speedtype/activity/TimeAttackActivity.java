package com.chalmers.speedtype.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.application.SpeedTypeApplication;
import com.chalmers.speedtype.controller.Controller;
import com.chalmers.speedtype.model.TimeAttackModel;
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
	public SwarmLeaderboard timeAttackLeaderboard;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		app = (SpeedTypeApplication) getApplication();
		controller = new Controller();

		setUpSwarm();
		model = new TimeAttackModel(app.getDatabase(), this, timeAttackLeaderboard);

		controller.setModel(model);
		controller.setActivity(this);

		setContentView(R.layout.time_attack);
		setUpViews();
		setUpInput();
		model.setViews(this);
	}

	private void setUpViews() {
		wordView = (TextView) findViewById(R.id.word);
		nextWordView = (TextView) findViewById(R.id.next_word);
		timeView = (TextView) findViewById(R.id.time);
		scoreView = (TextView) findViewById(R.id.score);
		powerUpView = (TextView) findViewById(R.id.multiplier);
		speedBonusView = (TextView) findViewById(R.id.speed_bonus);
		speedBonusScoreView = (TextView) findViewById(R.id.speed_bonus_score);

		wordView.setText(model.getCurrentWord());
		nextWordView.setText(model.getNextWord());
		scoreView.setText("0");
		timeView.setText("10.0");
		powerUpView.setVisibility(4);
		speedBonusView.setText("Speed bonus!");
		speedBonusScoreView.setText("+5");
		speedBonusView.setVisibility(4);
		speedBonusScoreView.setVisibility(4);
	}

	protected void setUpInput() {
		EditText input = (EditText) findViewById(R.id.input_edit_text);
		input.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				controller.onTextChanged(s);
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
			}
		});
	}
	private void setUpSwarm(){
		GotLeaderboardCB callback = new GotLeaderboardCB() {
		    public void gotLeaderboard(SwarmLeaderboard leaderboard) {
		
			if (leaderboard != null) {
		
		            // Save the leaderboard for later use
		            timeAttackLeaderboard = leaderboard;
		        }
		    }
		};
		SwarmLeaderboard.getLeaderboardById(826, callback);
	}
}