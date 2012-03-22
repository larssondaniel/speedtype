package com.chalmers.speedtype.controller;

import com.chalmers.speedtype.model.Model;

public class Controller {
	
	private Model model;
	
	public void setModel(Model model){
		this.model = model;
	}
	public void onTextChanged(CharSequence s) {
		model.onTextChanged(s);
	}
}