package com.chalmers.speedtype.model;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.util.Dictionary;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.text.Html;
import android.view.ViewGroup;
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
	private Handler handler;
	
	private int durationMillis = 3000;
	
	public FallingWordsModel(SQLiteDatabase database, Activity a, final Handler handler) {
		super(database, a);
		activity = a;
		currentWord = Dictionary.getNextWord();
		nextWord = Dictionary.getNextWord();
		this.handler = handler;
		
		Runnable runnable = new Runnable() {
			boolean even = true;
			public void run() {
				while(true) {
					try {
						Thread.sleep(durationMillis);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					handler.post(new Runnable() {
						public void run() {
							even = !even;
							if(even)
								wordView.setBackgroundColor(Color.argb(255, 255, 0, 0));
							else
								wordView.setBackgroundColor(Color.argb(255, 0, 0, 255));
							
							startAnimation();
						}
					});
				}
			}
		};
		new Thread(runnable).start();
		
		/*Thread thread = new Thread(){
			
			@Override
			public void run(){
				while(true){
					try {
						sleep(10);
						//if(wordView.getY() > 100)
							Log.i("l","l");
					} catch (InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		};
		
		thread.start();*/
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
			if (currentChar == currentWord.length()) {
				setNewWord();
			}
		}
	}

	private void startAnimation() {
		setNewWord();
		
		int wordPositionX = (int)((getDisplayWidth()-wordView.getWidth())*Math.random());
		int wordPositionY = -wordView.getHeight();
		
		ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams)wordView.getLayoutParams();
		mlp.setMargins(wordPositionX, wordPositionY, 0, 0);
		
		Animation fallAnimation = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.fall_animation);
		
		durationMillis *= 0.99;
		fallAnimation.setDuration(durationMillis);
		
		wordView.startAnimation(fallAnimation);
	}

	public void updateWord(){
		
	}
	
	private void setNewWord() {
		currentWord = nextWord;
		nextWord = Dictionary.getNextWord();
		wordView.setText(currentWord);
		//nextWordView.setText(nextWord);
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