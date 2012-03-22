package com.chalmers.speedtype.model;

import android.app.Activity;

import com.chalmers.speedtype.util.Dictionary;

public abstract class Model {
	
	protected Activity activity;
	protected Dictionary dictionary;
	
	public Model(Activity activity){
		this.activity = activity;
        dictionary = new Dictionary();
	}

	public abstract CharSequence getCurrentWord();
	public abstract CharSequence getNextWord();

	public abstract void onTextChanged(CharSequence s);
}
