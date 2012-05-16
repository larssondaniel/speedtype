package com.chalmers.speedtype.activity;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.application.SpeedTypeApplication;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class ScrabbleActivity extends GameMode {
	private SpeedTypeApplication app;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		app = (SpeedTypeApplication) getApplication();
	}
}
