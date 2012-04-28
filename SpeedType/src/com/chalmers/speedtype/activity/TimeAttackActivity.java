package com.chalmers.speedtype.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.application.SpeedTypeApplication;
import com.chalmers.speedtype.controller.Controller;
import com.chalmers.speedtype.model.TimeAttackModel;

public class TimeAttackActivity extends GameMode {

	private SpeedTypeApplication app;
	private Controller controller;
	private TimeAttackModel model;
	private TextView wordView;
	private TextView nextWordView;
	private TextView timeView;
	private TextView scoreView;
	private ImageView powerUpView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		app = (SpeedTypeApplication) getApplication();
		controller = new Controller();
		model = new TimeAttackModel(app.getDatabase());
		controller.setModel(model);
		controller.setActivity(this);

		setContentView(R.layout.time_attack);
		setUpViews();
		setUpInput();
		model.setViews(wordView, nextWordView, timeView, scoreView, powerUpView);
	}

	private void setUpViews() {
		wordView = (TextView) findViewById(R.id.word);
		nextWordView = (TextView) findViewById(R.id.next_word);
		timeView = (TextView) findViewById(R.id.time);
		scoreView = (TextView) findViewById(R.id.score);
		powerUpView = (ImageView) findViewById(R.id.x2);

		wordView.setText(model.getCurrentWord());
		nextWordView.setText(model.getNextWord());
		scoreView.setText("0");
		timeView.setText("10.0");
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
}