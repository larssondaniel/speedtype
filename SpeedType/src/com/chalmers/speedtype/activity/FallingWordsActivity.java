package com.chalmers.speedtype.activity;

import java.util.ArrayList;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import com.chalmers.speedtype.R;
import com.chalmers.speedtype.controller.Controller;
import com.chalmers.speedtype.model.FallingWordsModel;

public class FallingWordsActivity extends GameMode {

	private Controller controller;
	private FallingWordsModel model;

	private ArrayList<TextView> wordViews;

	private TextView scoreView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		controller = new Controller();
		model = new FallingWordsModel(this, handler);
		controller.setModel(model);
		controller.setActivity(this);

		setContentView(R.layout.falling_words);
		
		setUpViews();
		setUpTypeFaces();
		model.setUpViews(wordViews, scoreView);
		setUpInput();
		
		/*RelativeLayout layout = (RelativeLayout) findViewById(R.id.falling_words);
		TransitionDrawable drawable = (TransitionDrawable) layout.getBackground();
		
		drawable.setCrossFadeEnabled(true);
		drawable.startTransition(100000);*/
	}

	private void setUpTypeFaces() {
		Typeface mensch = Typeface.createFromAsset(getAssets(), "fonts/mensch.ttf");
		for(TextView word : wordViews)
			word.setTypeface(mensch);
	}

	private void setUpViews() {
		wordViews = new ArrayList<TextView>();
		
		wordViews.add( (TextView) findViewById(R.id.word1) );
		wordViews.add( (TextView) findViewById(R.id.word2) );
		wordViews.add( (TextView) findViewById(R.id.word3) );
		wordViews.add( (TextView) findViewById(R.id.word4) );
		wordViews.add( (TextView) findViewById(R.id.word5) );
		
		
		scoreView = (TextView) findViewById(R.id.score);
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
