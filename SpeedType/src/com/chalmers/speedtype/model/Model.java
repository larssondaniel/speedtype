package com.chalmers.speedtype.model;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.util.Dictionary;

public abstract class Model {

	protected Word currentWord;
	protected Word nextWord;

	protected int score = 0;
	protected int currentChar = 0;
	protected int powerUpMultiplier = 1;
	protected int correctLettersInRow;

	protected PowerUp multiplier;
	public PowerUp speedReward;

	private Activity activity;

	private TextView powerUpView;

	public Model(SQLiteDatabase database) {
		dictionary = new Dictionary(database);
	}

	protected Dictionary dictionary;

	public abstract CharSequence getCurrentWord();

	public abstract CharSequence getNextWord();

	public abstract void onTextChanged(CharSequence s);

	public abstract boolean isFinished();

	public void useMultiplier() {
		if (correctLettersInRow == 5) { // The numbers are way too low, will be
										// changed later on.
			multiplier = new PowerUp(powerUpMultiplier);
			powerUpMultiplier = multiplier
					.incrementMultiplier(powerUpMultiplier);
			powerUpView = (TextView) activity.findViewById(R.id.multiplier);
			Animation multiplierAnimation = AnimationUtils.loadAnimation(
					activity.getApplicationContext(),
					R.anim.multiplier_animation);
			powerUpView.setVisibility(0);
			powerUpView.setText("x2");
			powerUpView.startAnimation(multiplierAnimation);
		}
		if (correctLettersInRow == 10) {
			powerUpMultiplier = multiplier
					.incrementMultiplier(powerUpMultiplier);
			Animation multiplierAnimation = AnimationUtils.loadAnimation(
					activity.getApplicationContext(),
					R.anim.multiplier_animation);
			powerUpView.setText("x4");
			powerUpView.startAnimation(multiplierAnimation);
		}
		if (correctLettersInRow == 15) {
			powerUpMultiplier = multiplier
					.incrementMultiplier(powerUpMultiplier);
			Animation multiplierAnimation = AnimationUtils.loadAnimation(
					activity.getApplicationContext(),
					R.anim.multiplier_animation);
			powerUpView.setText("x8");
			powerUpView.startAnimation(multiplierAnimation);
		}
	}

	public void setViews(Activity activity) {
		this.activity = activity;
	}

	public Activity getActivity() {
		return activity;
	}
}
