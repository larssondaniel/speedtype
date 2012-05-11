package com.chalmers.speedtype.controller;

import android.hardware.SensorEvent;
import android.view.KeyEvent;

import com.chalmers.speedtype.model.Model;

public class Controller extends Thread {

	private Model model;

	public Controller(Model model) {
		this.model = model;
	}
	public void startGame(){
		if(model.isRealTime())
			start();
	}

	public void run() {
		while(true){
			try {
				sleep(30);
				model.update();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean onKey(KeyEvent event) {
		model.onInput(event);
		return true;
	}

	public void onSensorChanged(SensorEvent event) {
		model.onSensorChanged(event);
	}
}