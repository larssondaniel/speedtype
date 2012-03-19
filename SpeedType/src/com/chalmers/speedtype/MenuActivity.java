package com.chalmers.speedtype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {
    
	private Button newGameButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        setUpViews();
        setUpListeners();
    }
    
	private void setUpViews() {
		newGameButton = (Button)findViewById(R.id.new_game_button);
	}
	
	private void setUpListeners() {
		newGameButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MenuActivity.this, GameActivity.class);
				startActivity(intent);
			}
		});
	}

}