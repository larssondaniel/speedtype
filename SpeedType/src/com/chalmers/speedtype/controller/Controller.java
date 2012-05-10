package com.chalmers.speedtype.controller;

import android.view.KeyEvent;

import com.chalmers.speedtype.model.Model;

public class Controller extends Thread {
	
	private Model model;
	
	public Controller(Model model) {
		this.model = model;
		
	}
	
	@Override
    public synchronized void run() {
        while (true) {
            try {
            	sleep(30);
//                model.gravity();
            } catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
                
            }
        }
    }

	public boolean onKey(KeyEvent event) {
		model.onInput(event);
		return true;
	}
}