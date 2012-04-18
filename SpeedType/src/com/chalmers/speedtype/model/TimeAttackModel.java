package com.chalmers.speedtype.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.text.Html;
import android.widget.TextView;

public class TimeAttackModel extends Model {

	private Word currentWord;
	private Word nextWord;

	private TextView wordView;
	private TextView nextWordView;
	private TextView timeView;
	private TextView scoreView;

	CountDownTimer timer;

	private int score = 0;
	private int currentChar = 0;
	public long timeLeft = 10000;
	
	private boolean isFinished;

	public TimeAttackModel(SQLiteDatabase database) {
		super(database);
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
		if (s.length() > 0 && s.charAt(s.length() - 1) == getLastChar()) {
			setTextColors();
			currentChar++;
			scoreView.setText("" + ++score);
			if (timer == null) {
				timer = new CountDownTimer(timeLeft, 1000) {

					public void onTick(long millisTimeLeft) {
						timeView.setText("" + timeLeft / 1000);
						timeLeft = timeLeft - 1000;
					}

					public void onFinish() {
						timeView.setText("");
						nextWordView.setText("Game over!");
						wordView.setText("Score: 1337");
					}
				}.start();
			}
			if (currentChar == currentWord.length()) {
				setNewWord();
				
				timer.cancel();
				timer = new CountDownTimer(timeLeft += 4000, 1000) {

					public void onTick(long millisTimeLeft) {
						timeView.setText("" + timeLeft / 1000);
						timeLeft = timeLeft - 1000;
					}

					public void onFinish() {
						timeView.setText("");
						nextWordView.setText("Game over!");
						wordView.setText("Score: 1337");
						isFinished = true;
					}
				}.start();
			}
		}
	}

	private void setNewWord() {
		currentWord = nextWord;
		nextWord = dictionary.getNextWord();
		wordView.setText(currentWord);
		nextWordView.setText(nextWord);
		currentChar = 0;
	}

	private void setTextColors() {
		wordView.setText(Html.fromHtml("<font color=#00ff00>"
				+ currentWord.substring(0, currentChar + 1) + "</font>"));
		wordView.append(currentWord.substring(currentChar + 1,
				currentWord.length()));
	}

	private char getLastChar() {
		return currentWord.charAt(currentChar);
	}
	public boolean isFinished(){
		return isFinished;
	}

	public void setViews(TextView wordView, TextView nextWordView,
			TextView timeView, TextView scoreView) {
		this.wordView = wordView;
		this.nextWordView = nextWordView;
		this.timeView = timeView;
		this.scoreView = scoreView;
	}
}