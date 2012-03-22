package com.chalmers.speedtype.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public abstract class GameMode extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        
	}
}