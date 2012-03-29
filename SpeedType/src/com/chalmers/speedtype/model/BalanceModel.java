package com.chalmers.speedtype.model;

import android.app.Activity;
import android.os.CountDownTimer;
import android.text.Html;
import android.widget.TextView;

public class BalanceModel extends Model {

	private Word currentWord;
	private Word nextWord;

	private TextView wordView;
	private TextView nextWordView;
	private TextView timeView;
	private TextView scoreView;

	CountDownTimer timer;

	private int score = 0;
	private int currentChar = 0;
	public long timeLeft = 10000; // millisec.

	public BalanceModel(Activity activity) {
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
				timer = new CountDownTimer(timeLeft, 100) {

					public void onTick(long millisTimeLeft) {
						timeView.setText("" + (double) timeLeft / 1000);
						timeLeft = timeLeft - 100;
					}

					public void onFinish() {
						timeView.setText("");
						nextWordView.setText("Game over!");
						wordView.setText("Score: 1337");
					}
				}.start();
			}
			if (currentChar == currentWord.length()) {
				currentWord = nextWord;
				nextWord = dictionary.getNextWord();
				wordView.setText(currentWord);
				nextWordView.setText(nextWord);
				currentChar = 0;
				timer.cancel();
				timer = new CountDownTimer(timeLeft += 4000, 100) {

					public void onTick(long millisTimeLeft) {
						timeView.setText("" + (double) timeLeft / 1000);
						timeLeft = timeLeft - 100;
					}

					public void onFinish() {
						timeView.setText("");
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

	public void setViews(TextView wordView, TextView nextWordView,
			TextView timeView, TextView scoreView) {
		this.wordView = wordView;
		this.nextWordView = nextWordView;
		this.timeView = timeView;
		this.scoreView = scoreView;
	}
}