package com.chalmers.speedtype.model;

import android.view.KeyEvent;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.util.ExtendedCountDownTimer;

public class TimeAttackModel extends Model {
	private static final int LAYOUT_ID = R.layout.time_attack_layout;
	private static final int VIEW_ID = R.id.time_attack_view;
	ExtendedCountDownTimer timer;
	
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return LAYOUT_ID;
	}

	@Override
	public int getViewId() {
		// TODO Auto-generated method stub
		return VIEW_ID;
	}

	@Override
	public void onInput(KeyEvent event) {
		char inputChar = (char) event.getUnicodeChar();
		if(activeWord.charAt(currentCharPos) == inputChar){
			incScore(1);
			if (isWordComplete()){
				updateWord();
			}else{
				incCurrentCharPos();
			}
		}
	}
}
	
//		
//			if (timer == null) {
//				timer = new CountDownTimer(timeLeft, 1000) {
//
//					public void onTick(long millisTimeLeft) {
//						timeView.setText("" + timeLeft / 1000);
//						timeLeft = timeLeft - 1000;
//					}
//
//					public void onFinish() {
//						if (timeAttackLeaderboard != null) {
//
//							// Then submit the score
//							timeAttackLeaderboard.submitScore(score);
//						}
//						timeView.setText("");
//						nextWordView.setText("Game over!");
//						wordView.setText("Score: " + score);
//					}
//				}.start();
//			}
//
//			if (currentChar == currentWord.length()) {
//				if (speedReward != null) {
//					speedReward.addSpeedReward(speedReward, timeLeft, score,
//							speedBonusView, speedBonusScoreView, scoreView,
//							activity);
//				}
//				timer.cancel();
//				timer = new CountDownTimer(timeLeft += 4000, 1000) {
//
//					public void onTick(long millisTimeLeft) {
//						timeView.setText("" + timeLeft / 1000);
//						timeLeft = timeLeft - 1000;
//					}
//
//					public void onFinish() {
//						if (timeAttackLeaderboard != null) {
//
//							// Then submit the score
//							timeAttackLeaderboard.submitScore(score);
//						}
//						timeView.setText("");
//						nextWordView.setText("Game over!");
//						wordView.setText("Score: " + score);
//						isFinished = true;
//					}
//				}.start();
//				startSpeedRewardTimer(timeLeft, currentWord.length(), score);
//				setAchievement();
//				setNewWord();
//
//			}
//		} else {
//			setFalseTextColors();
//			if (powerUpMultiplier != 1) {
//				Animation multiplierGoneAnimation = AnimationUtils
//						.loadAnimation(getActivity().getApplicationContext(),
//								R.anim.multiplier_gone_animation);
//				powerUpView.startAnimation(multiplierGoneAnimation);
//			}
//			correctLettersInRow = 0;
//			powerUpMultiplier = 1;
//		}
//		tryMultiplierPowerUp();
