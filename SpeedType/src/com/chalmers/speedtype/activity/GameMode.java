package com.chalmers.speedtype.activity;

import com.chalmers.speedtype.model.ExampleModel;
import com.chalmers.speedtype.model.Model;
import com.chalmers.speedtype.util.Util;
import com.chalmers.speedtype.views.GameView;
import com.swarmconnect.SwarmActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public abstract class GameMode extends SwarmActivity{
	protected Handler handler;
	
	private Model model;
	private GameView view;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        Util.DISPLAY = getWindowManager().getDefaultDisplay();
        
        Bundle extras = getIntent().getExtras();
        String gameMode = extras.getString("gameMode");
        
        // Factory to get the view here
        
        handler = new Handler(); 
		model = new ExampleModel();
        view = new GameView(model, this, handler);
        
        setContentView(view);
	}
}