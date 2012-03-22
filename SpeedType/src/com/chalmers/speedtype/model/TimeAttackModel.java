package com.chalmers.speedtype.model;

import android.app.Activity;
import android.os.CountDownTimer;
import android.text.Html;
import android.widget.TextView;

public class TimeAttackModel extends Model {

	private Word currentWord;
	private Word nextWord;

	private TextView wordView;
	private TextView nextWordView;
	private TextView time;

	CountDownTimer timer;

	private int currentChar = 0;
	public long timeLeft = 20000;

	public TimeAttackModel(Activity activity) {
		super(activity);
		currentWord = dictionary.getNextWord();
		nextWord = dictionary.getNextWord();

		timer = new CountDownTimer(timeLeft, 1000) {

			public void onTick(long millisTimeLeft) {
				time.setText("" + timeLeft / 1000);
				timeLeft = timeLeft - 1000;
			}

			public void onFinish() {
				time.setText("");
				nextWordView.setText("Game over!");
				wordView.setText("Score: 1337");
			}
		}.start();
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
			if (currentChar == currentWord.length()) {
				currentWord = nextWord;
				nextWord = dictionary.getNextWord();
				wordView.setText(currentWord);
				nextWordView.setText(nextWord);
				currentChar = 0;
				timer.cancel();
				timer = new CountDownTimer(timeLeft += 4000, 1000) {

					public void onTick(long millisTimeLeft) {
						time.setText("" + timeLeft / 1000);
						timeLeft = timeLeft - 1000;
					}

					public void onFinish() {
						time.setText("");
						nextWordView.setText("Game over!");
						wordView.setText("Score: 1337");
						activity.finish();
					}
				}.start();
				if (currentWord == null)
					activity.finish();
			}
		}

	}

	public void setViews(TextView wordView, TextView nextWordView, TextView time) {
		this.wordView = wordView;
		this.nextWordView = nextWordView;
		this.time = time;
	}
}