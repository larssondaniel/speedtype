package com.chalmers.speedtype.controller;

import android.app.Activity;

import com.chalmers.speedtype.model.Model;

public class Controller {
	private Model model;
	private Activity activity;
	
	public void setModel(Model model){
		this.model = model;
	}
	public void setActivity(Activity activity){
		this.activity = activity;
	}
	
	public void onTextChanged(CharSequence s) {
		model.onTextChanged(s);
		
		if(model.isFinished())
			activity.finish();
	}
}