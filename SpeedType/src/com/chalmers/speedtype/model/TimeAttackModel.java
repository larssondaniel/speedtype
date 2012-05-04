package com.chalmers.speedtype.model;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.util.Dictionary;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class TimeAttackModel extends Model {

	private TextView wordView;
	private TextView nextWordView;
	private TextView timeView;
	private TextView scoreView;
	private TextView powerUpView;
	private TextView speedBonusView;
	private TextView speedBonusScoreView;
	private Activity activity;

	CountDownTimer timer;
	public long timeLeft = 10000;

	private boolean isFinished;

	public TimeAttackModel(SQLiteDatabase database) {
		super(database);
		currentWord = Dictionary.getNextWord();
		nextWord = Dictionary.getNextWord();
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
		useMultiplier();
		if (s.length() > 0 && s.charAt(s.length() - 1) == getLastChar()) {
			setTextColors();
			currentChar++;
			correctLettersInRow++;
			incScore();
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
				if (speedReward != null) {
					speedReward.addSpeedReward(speedReward, timeLeft, score,
							speedBonusView, speedBonusScoreView, scoreView,
							activity);
				}
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
				startSpeedRewardTimer(timeLeft, currentWord.length(), score);
				setNewWord();
			}
		} else {

			if (powerUpMultiplier != 1) {
				Animation multiplierGoneAnimation = AnimationUtils
						.loadAnimation(getActivity().getApplicationContext(),
								R.anim.multiplier_gone_animation);
				powerUpView.startAnimation(multiplierGoneAnimation);
			}

			correctLettersInRow = 0;
			powerUpMultiplier = 1;
		}
	}

	private void setNewWord() {
		currentWord = nextWord;
		nextWord = Dictionary.getNextWord();
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

	public boolean isFinished() {
		return isFinished;
	}

	public void setViews(Activity activity) {
		super.setViews(activity);
		this.activity = activity;
		wordView = (TextView) activity.findViewById(R.id.word);
		nextWordView = (TextView) activity.findViewById(R.id.next_word);
		timeView = (TextView) activity.findViewById(R.id.time);
		scoreView = (TextView) activity.findViewById(R.id.score);
		powerUpView = (TextView) activity.findViewById(R.id.multiplier);
		speedBonusView = (TextView) activity.findViewById(R.id.speed_bonus);
		speedBonusScoreView = (TextView) activity
				.findViewById(R.id.speed_bonus_score);
	}

	public void incScore() {
		score = score + powerUpMultiplier;
		scoreView.setText("" + score);
	}

	public void startSpeedRewardTimer(long timeLeft, int lenghtOfWord, int score) {
		speedReward = new PowerUp(timeLeft, currentWord.length(), score);
	}
}