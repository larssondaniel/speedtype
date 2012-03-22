package com.chalmers.speedtype.model;

import android.app.Activity;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

public class TimeAttackModel extends Model {
	
	private Word currentWord;
	private Word nextWord;
	
	private TextView wordView;
	private TextView nextWordView;
	
	private int currentChar = 0;
	
	public TimeAttackModel(Activity activity){
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
		if(s.length()>0 && s.charAt(s.length()-1) == currentWord.charAt(currentChar)){
			wordView.setText(Html.fromHtml("<font color=#00ff00>" + currentWord.substring(0, currentChar+1) + "</font>"));
			wordView.append(currentWord.substring(currentChar+1, currentWord.length()));
			currentChar++;
			if(currentChar == currentWord.length()){
				currentWord = nextWord;
				nextWord = dictionary.getNextWord();
				wordView.setText(currentWord);
				nextWordView.setText(nextWord);
				currentChar = 0;
				if(currentWord == null)
					activity.finish();
			}
		}
		
	}
	public void setViews(TextView wordView, TextView nextWordView) {
		this.wordView = wordView;
		this.nextWordView = nextWordView;
	}
}