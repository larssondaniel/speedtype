package com.chalmers.speedtype.model;

import java.util.ArrayList;
import java.util.LinkedList;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.util.Dictionary;
import android.app.Activity;
import android.os.Handler;
import android.text.Html;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class FallingWordsModel extends Model {

	private Word currentWord;
	private Word nextWord;
	private Word activeWord;

	private ArrayList<TextView> wordViews;
	private LinkedList<TextView> wordQue = new LinkedList<TextView>();
	
	private TextView currentWordView;
	private TextView activeWordView;
	private TextView scoreView;

	private int score = 0;
	private int currentChar = 0;
	private int wordNumber = 0;
	
	private boolean isFinished;
	
	private int durationMillis = 30000;

	public FallingWordsModel(Activity activity,
			final Handler handler) {
		super(activity);
		
		currentWord = Dictionary.getNextWord();
		nextWord = Dictionary.getNextWord();

		//setNewWord();
		//startAnimation(currentWordView);
		
		Runnable runnable = new Runnable() {
			public void run() {
				
				//setNewWord();
				//startAnimation(currentWordView);
				
				while (true) {
					try {
						Thread.sleep(durationMillis/5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					handler.post(new Runnable() {
						public void run() {
							setNewWord();
							startAnimation(currentWordView);
						}
					});
				}
			}
		};
		new Thread(runnable).start();
	}

	private TextView getNextWordView() {
		TextView word = wordViews.get(wordNumber);
		wordQue.add(word);
		wordNumber++;
		wordNumber%=5;
		
		return word;
	}

	@Override
	public void onTextChanged(CharSequence s) {
		
		activeWordView = wordQue.getFirst();
		activeWord = new Word(activeWordView.getText().toString());
		
		System.out.println(activeWord.toString());
		System.out.println(activeWord.charAt(currentChar));
		System.out.println(currentChar);
		if (s.length() > 0 && s.charAt(s.length() - 1) == activeWord.charAt(currentChar)) {
			setTextColors(activeWord, activeWordView);
			currentChar++;
			scoreView.setText("" + ++score);
			if (currentChar == activeWord.length()) {
				wordQue.removeFirst();
				currentChar = 0;
				hideWord(activeWordView);
			}
		}
	}

	private void hideWord(TextView word) {
		word.setAlpha(0);
	}

	private void startAnimation(TextView word) {
		int wordPosX = (int) ((getDisplayWidth() - word.getWidth()) * Math.random());
		int wordPosY = -word.getHeight();
		
		ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) word
				.getLayoutParams();

		mlp.setMargins(wordPosX, wordPosY, 0, 0);
		
		Animation fallAnimation = AnimationUtils.loadAnimation(
				getActivity(), R.anim.fall_animation);

		durationMillis *= 0.99;
		fallAnimation.setDuration(durationMillis);

		word.startAnimation(fallAnimation);
	}

	private void setNewWord() {
		currentWord = nextWord;
		currentWordView = getNextWordView();
		currentWordView.setText(currentWord);
		
		int fontSize = (int)(Math.random()*20 + 30);
		currentWordView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
		
		nextWord = Dictionary.getNextWord();
	}

	private void setTextColors(Word activeWord, TextView activeWordView) {
		System.out.println(activeWord + "!");
		activeWordView.setText(Html.fromHtml("<font color=#00b4ff><b>"
				+ activeWord.substring(0, currentChar + 1) + "</b></font>"));
		activeWordView.append(activeWord.substring(currentChar + 1,
				activeWord.length()));
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setUpViews(ArrayList<TextView> wordViews, TextView scoreView) {
		this.wordViews = wordViews;
		this.scoreView = scoreView;
		
		scoreView.setText("0");
	}
}