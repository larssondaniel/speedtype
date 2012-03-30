package com.chalmers.speedtype.model;

import com.chalmers.speedtype.util.MyCountDownTimer;

import android.app.Activity;
import android.text.Html;
import android.widget.TextView;

public class TimeAttackModel extends Model {

	private Word currentWord;
	private Word nextWord;

	private TextView wordView;
	private TextView nextWordView;

	private TextView timeView;
	private TextView scoreView;

	MyCountDownTimer timer;

	private int score = 0;
	private int currentChar = 0;
	private long startTimeLeft = 10000; // millisec.
	private long tempTimeLeft;

	public TimeAttackModel(Activity activity) {
		super(activity);
		currentWord = dictionary.getNextWord();
		nextWord = dictionary.getNextWord();
	}

	public CharSequence getCurrentWord() {
		return currentWord;
	}

	@Override
	public CharSequence getNextWord() {
		return nextWord;
	}

	@Override
	public void onTextChanged(CharSequence s) {
		// garbage
		if (s.length() > 0
				&& s.charAt(s.length() - 1) == currentWord.charAt(currentChar)) {
			wordView.setText(Html.fromHtml("<font color=#00ff00>"
					+ currentWord.substring(0, currentChar + 1) + "</font>"));
			wordView.append(currentWord.substring(currentChar + 1,
					currentWord.length()));
			currentChar++;
			scoreView.setText("" + ++score);
			if (timer == null) {

				timer = new MyCountDownTimer(startTimeLeft, 100, wordView,
						nextWordView, timeView, scoreView);
				timer.start();
			}

			if (currentChar == currentWord.length()) {
				currentWord = nextWord;
				nextWord = dictionary.getNextWord();
				wordView.setText(currentWord);
				nextWordView.setText(nextWord);
				currentChar = 0;
				tempTimeLeft = timer.getTimeLeft();
				timer.cancel();

				timer = new MyCountDownTimer(tempTimeLeft + 4000, 100,
						wordView, nextWordView, timeView, scoreView);
				timer.start();

				if (currentWord == null)
					activity.finish();
			}
		}

	}

	public void setViews(TextView wordView, TextView nextWordView,
			TextView timeView, TextView scoreView) {
		this.wordView = wordView;
		this.nextWordView = nextWordView;
		this.timeView = timeView;
		this.scoreView = scoreView;
	}
}