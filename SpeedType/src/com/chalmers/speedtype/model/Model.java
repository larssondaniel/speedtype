package com.chalmers.speedtype.model;

import com.chalmers.speedtype.util.Dictionary;

public abstract class Model {
	
	protected Dictionary dictionary;
	
	public Model(){
        dictionary = new Dictionary();
	}

	public abstract CharSequence getCurrentWord();
	public abstract CharSequence getNextWord();

	public abstract void onTextChanged(CharSequence s);
	
	public abstract boolean isFinished();
}
