package com.chalmers.speedtype.activity;

import com.swarmconnect.SwarmActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public abstract class GameMode extends SwarmActivity {
	protected Handler handler;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        
        handler = new Handler();
	}
}