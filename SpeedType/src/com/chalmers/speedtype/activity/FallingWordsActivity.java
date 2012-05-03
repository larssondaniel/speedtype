package com.chalmers.speedtype.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.application.SpeedTypeApplication;
import com.chalmers.speedtype.controller.Controller;
import com.chalmers.speedtype.model.FallingWordsModel;

public class FallingWordsActivity extends GameMode{
	private SpeedTypeApplication app;
	private Controller controller;
	private FallingWordsModel model;
	private TextView wordView;
	private TextView nextWordView;
	private TextView scoreView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		app = (SpeedTypeApplication)getApplication();
		controller = new Controller();
		model = new FallingWordsModel(app.getDatabase(), this, handler);
		controller.setModel(model);
		controller.setActivity(this);

		setContentView(R.layout.falling_words);
		setUpViews();
		setUpInput();
		model.setViews(wordView, nextWordView, scoreView);
	}

	private void setUpViews() {
		wordView = (TextView) findViewById(R.id.word);
		nextWordView = (TextView) findViewById(R.id.next_word);
		scoreView = (TextView) findViewById(R.id.score);

		wordView.setText(model.getCurrentWord());
		//nextWordView.setText(model.getNextWord());
		scoreView.setText("0");
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
