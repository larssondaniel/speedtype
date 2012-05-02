package com.chalmers.speedtype.model;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.util.Dictionary;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.Html;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class FallingWordsModel extends Model {

	private Word currentWord;
	private Word nextWord;

	private TextView wordView;
	private TextView nextWordView;
	private TextView scoreView;

	private int score = 0;
	private int currentChar = 0;
	private boolean isFinished;

	private Activity activity;
	
	public FallingWordsModel(SQLiteDatabase database, Activity a) {
		super(database);
		activity = a;
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
		//wordView.setRotation(180);
		Context context = activity.getApplicationContext();
		WindowManager mWinMgr = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		int displayWidth = mWinMgr.getDefaultDisplay().getWidth();
		
		wordView.setX((int)((displayWidth/2/*-wordWidth*/)*Math.random()));
		
		Animation hyperspaceJump = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.animation);
		wordView.startAnimation(hyperspaceJump);
		
		if (s.length() > 0 && s.charAt(s.length() - 1) == getLastChar()) {
			setTextColors();
			currentChar++;
			scoreView.setText("" + ++score);
			if (currentChar == currentWord.length()) {
				setNewWord();
			}
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
	public boolean isFinished(){
		return isFinished;
	}

	public void setViews(TextView wordView, TextView nextWordView, TextView scoreView) {
		this.wordView = wordView;
		this.nextWordView = nextWordView;
		this.scoreView = scoreView;
	}
}