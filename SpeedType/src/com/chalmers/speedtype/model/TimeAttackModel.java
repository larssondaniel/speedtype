package com.chalmers.speedtype.model;

import android.view.KeyEvent;

import com.chalmers.speedtype.R;

public class TimeAttackModel extends Model {
	private static final int LAYOUT_ID = R.layout.time_attack_layout;
	private static final int VIEW_ID = R.id.time_attack_view;
	private int timeLeft = 10000;
	private boolean correctInput;

	public TimeAttackModel() {
		initTimer();
		correctInput = false;
	}


	private void initTimer() {
		Runnable runnable = new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(100);
						setTimeLeft(timeLeft - 100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		new Thread(runnable).start();
	}


	protected void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
		listener.propertyChange(null);
	}
	protected void incTimeLeft(int timeLeft) {
		
	}
	
	public int getTimeLeft(){
		return timeLeft;
	}

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
	
	private void setCorrectInputReport(boolean b){
		correctInput = b;
	}
	
	public boolean correctInputReport(){
		if(correctInput){
			return true;
		} else {
			setCorrectInputReport(true);
			return false;
		}
	}

	@Override
	public void onInput(KeyEvent event) {
		correctInput = true;
		char inputChar = Character.toLowerCase((char) event.getUnicodeChar());
		if (activeWord.charAt(currentCharPos) == inputChar) {
			incScore(1);
			if (isWordComplete()) {
				setTimeLeft(timeLeft + 1000*activeWord.length());
				updateWord();
			} else {
				incCurrentCharPos();
			}
		} else {
			setCorrectInputReport(false);
		}
	}
}

//
// if (timer == null) {
// timer = new CountDownTimer(timeLeft, 1000) {
//
// public void onTick(long millisTimeLeft) {
// timeView.setText("" + timeLeft / 1000);
// timeLeft = timeLeft - 1000;
// }
//
// public void onFinish() {
// if (timeAttackLeaderboard != null) {
//
// // Then submit the score
// timeAttackLeaderboard.submitScore(score);
// }
// timeView.setText("");
// nextWordView.setText("Game over!");
// wordView.setText("Score: " + score);
// }
// }.start();
// }
//
// if (currentChar == currentWord.length()) {
// if (speedReward != null) {
// speedReward.addSpeedReward(speedReward, timeLeft, score,
// speedBonusView, speedBonusScoreView, scoreView,
// activity);
// }
// timer.cancel();
// timer = new CountDownTimer(timeLeft += 4000, 1000) {
//
// public void onTick(long millisTimeLeft) {
// timeView.setText("" + timeLeft / 1000);
// timeLeft = timeLeft - 1000;
// }
//
// public void onFinish() {
// if (timeAttackLeaderboard != null) {
//
// // Then submit the score
// timeAttackLeaderboard.submitScore(score);
// }
// timeView.setText("");
// nextWordView.setText("Game over!");
// wordView.setText("Score: " + score);
// isFinished = true;
// }
// }.start();
// startSpeedRewardTimer(timeLeft, currentWord.length(), score);
// setAchievement();
// setNewWord();
//
// }
// } else {
// setFalseTextColors();
// if (powerUpMultiplier != 1) {
// Animation multiplierGoneAnimation = AnimationUtils
// .loadAnimation(getActivity().getApplicationContext(),
// R.anim.multiplier_gone_animation);
// powerUpView.startAnimation(multiplierGoneAnimation);
// }
// correctLettersInRow = 0;
// powerUpMultiplier = 1;
// }
// tryMultiplierPowerUp();

/*
 * package com.chalmers.speedtype.model;
 * 
 * import java.util.Map;
 * 
 * import com.chalmers.speedtype.R; import
 * com.chalmers.speedtype.util.Dictionary; import
 * com.swarmconnect.SwarmAchievement; import com.swarmconnect.SwarmLeaderboard;
 * 
 * import android.animation.AnimatorInflater; import
 * android.animation.AnimatorSet; import android.app.Activity; import
 * android.os.CountDownTimer; import android.text.Html; import
 * android.view.animation.Animation; import
 * android.view.animation.AnimationUtils; import android.widget.TextView;
 * 
 * public class TimeAttackModel extends Model {
 * 
 * @Override public int getLayoutId() { // TODO Auto-generated method stub
 * return 0; }
 * 
 * @Override public int getViewId() { // TODO Auto-generated method stub return
 * 0; } /* private TextView wordView; private TextView nextWordView; private
 * TextView timeView; private TextView scoreView; private Activity activity;
 * 
 * CountDownTimer timer; public long timeLeft = 10000;
 * 
 * private boolean isFinished;
 * 
 * public TimeAttackModel(Activity activity, SwarmLeaderboard
 * timeAttackLeaderboard, Map<Integer, SwarmAchievement> timeAttackAchievements)
 * {
 * 
 * super(activity);
 * 
 * this.timeAttackLeaderboard = timeAttackLeaderboard;
 * this.timeAttackAchievements = timeAttackAchievements;
 * 
 * currentWord = Dictionary.getNextWord(); nextWord = Dictionary.getNextWord();
 * }
 * 
 * public CharSequence getCurrentWord() { return currentWord; } public
 * CharSequence getNextWord(){ return nextWord; }
 * 
 * @Override
 * 
 * private void setNewWord() { currentWord = nextWord; nextWord =
 * Dictionary.getNextWord(); wordView.setText(currentWord);
 * nextWordView.setText(nextWord); currentChar = 0; }
 * 
 * protected void setAchievement() { super.setAchievement(); }
 * 
 * private void setTextColors() {
 * wordView.setText(Html.fromHtml("<font color=#00ff00>" +
 * currentWord.substring(0, currentChar + 1) + "</font>"));
 * wordView.append(currentWord.substring(currentChar + 1,
 * currentWord.length())); }
 * 
 * private void setFalseTextColors() { AnimatorSet set = (AnimatorSet)
 * AnimatorInflater.loadAnimator(getContext(), R.anim.wrong_input);
 * set.setTarget(wordView); set.start(); }
 * 
 * private char getLastChar() { return currentWord.charAt(currentChar); }
 * 
 * public boolean isFinished() { return isFinished; }
 * 
 * public void setViews(Activity activity) { super.setViews(activity);
 * this.activity = activity; wordView = (TextView)
 * activity.findViewById(R.id.word); nextWordView = (TextView)
 * activity.findViewById(R.id.next_word); timeView = (TextView)
 * activity.findViewById(R.id.time); scoreView = (TextView)
 * activity.findViewById(R.id.score); powerUpView = (TextView)
 * activity.findViewById(R.id.multiplier); speedBonusView = (TextView)
 * activity.findViewById(R.id.speed_bonus); speedBonusScoreView = (TextView)
 * activity .findViewById(R.id.speed_bonus_score);
 * 
 * wordView.setText(getCurrentWord()); nextWordView.setText(getNextWord());
 * scoreView.setText("0"); timeView.setText("10.0");
 * powerUpView.setVisibility(4); speedBonusView.setText("Speed bonus!");
 * speedBonusScoreView.setText("+5"); speedBonusView.setVisibility(4);
 * speedBonusScoreView.setVisibility(4); }
 * 
 * public void incScore() { score = score + powerUpMultiplier;
 * scoreView.setText("" + score); } }
 */