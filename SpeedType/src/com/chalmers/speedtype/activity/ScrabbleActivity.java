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
	
	protected void setUpInput() {
		EditText input = (EditText) findViewById(R.id.input_edit_text);
		input.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				//controller.onTextChanged(s);
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
			}
		});
	}
}
