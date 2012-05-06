package com.chalmers.speedtype.model;

import java.util.Map;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.util.Dictionary;
import com.swarmconnect.SwarmAchievement;
import com.swarmconnect.SwarmLeaderboard;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
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
	private Activity activity;

	CountDownTimer timer;
	public long timeLeft = 10000;

	private boolean isFinished;

	public TimeAttackModel(SQLiteDatabase database, Activity activity,
			SwarmLeaderboard timeAttackLeaderboard,
			Map<Integer, SwarmAchievement> timeAttackAchievements) {

		super(database, activity);

		this.timeAttackLeaderboard = timeAttackLeaderboard;
		this.timeAttackAchievements = timeAttackAchievements;

		currentWord = Dictionary.getNextWord();
		nextWord = Dictionary.getNextWord();
	}

	public CharSequence getCurrentWord() {
		return currentWord;
	}
	public CharSequence getNextWord(){
		return nextWord;
	}

	@Override
	public void onTextChanged(CharSequence s) {
		
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
						if (timeAttackLeaderboard != null) {

							// Then submit the score
							timeAttackLeaderboard.submitScore(score);
						}
						timeView.setText("");
						nextWordView.setText("Game over!");
						wordView.setText("Score: " + score);
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
						if (timeAttackLeaderboard != null) {

							// Then submit the score
							timeAttackLeaderboard.submitScore(score);
						}
						timeView.setText("");
						nextWordView.setText("Game over!");
						wordView.setText("Score: " + score);
						isFinished = true;
					}
				}.start();
				startSpeedRewardTimer(timeLeft, currentWord.length(), score);
				setAchievement();
				setNewWord();

			}
		} else {
			setFalseTextColors();
			if (powerUpMultiplier != 1) {
				Animation multiplierGoneAnimation = AnimationUtils
						.loadAnimation(getActivity().getApplicationContext(),
								R.anim.multiplier_gone_animation);
				powerUpView.startAnimation(multiplierGoneAnimation);
			}
			correctLettersInRow = 0;
			powerUpMultiplier = 1;
		}
		tryMultiplierPowerUp();
	}

	private void setNewWord() {
		currentWord = nextWord;
		nextWord = Dictionary.getNextWord();
		wordView.setText(currentWord);
		nextWordView.setText(nextWord);
		currentChar = 0;
	}

	protected void setAchievement() {
		super.setAchievement();
	}

	private void setTextColors() {
		wordView.setText(Html.fromHtml("<font color=#00ff00>"
				+ currentWord.substring(0, currentChar + 1) + "</font>"));
		wordView.append(currentWord.substring(currentChar + 1,
				currentWord.length()));
	}

	private void setFalseTextColors() {
		AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
			    R.anim.wrong_input);
		set.setTarget(wordView);
		set.start();		
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

		wordView.setText(getCurrentWord());
		nextWordView.setText(getNextWord());
		scoreView.setText("0");
		timeView.setText("10.0");
		powerUpView.setVisibility(4);
		speedBonusView.setText("Speed bonus!");
		speedBonusScoreView.setText("+5");
		speedBonusView.setVisibility(4);
		speedBonusScoreView.setVisibility(4);
	}

	public void incScore() {
		score = score + powerUpMultiplier;
		scoreView.setText("" + score);
	}
}