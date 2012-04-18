package com.chalmers.speedtype.model;

import android.database.sqlite.SQLiteDatabase;

import com.chalmers.speedtype.util.Dictionary;

public abstract class Model {
	
	protected Dictionary dictionary;
	
	public Model(SQLiteDatabase database){
        dictionary = new Dictionary(database);
	}

	public abstract CharSequence getCurrentWord();
	public abstract CharSequence getNextWord();

	public abstract void onTextChanged(CharSequence s);
	
	public abstract boolean isFinished();
}
