package com.chalmers.speedtype.model;

import java.beans.PropertyChangeListener;

public abstract class Model {
	
	protected PropertyChangeListener listener;
	
	private String word = "walla";
	
	public void addChangeListener(PropertyChangeListener newListener) {
		listener = newListener;
	}
	
	public void setWord(String string) {
		word = string;
		listener.propertyChange(null);
	}
	
	public String getWord() {
		return word;
	}
	
	public abstract int getLayoutId();
	public abstract int getViewId();
	public abstract void gravity();
}
