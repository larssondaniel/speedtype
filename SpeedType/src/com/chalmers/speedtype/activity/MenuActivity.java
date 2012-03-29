package com.chalmers.speedtype.activity;

import com.chalmers.speedtype.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {

	private Button newTimeAttackGameButton;
	private Button newBalanceGameButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		setUpViews();
		setUpListeners();

	}

	private void setUpViews() {
		newTimeAttackGameButton = (Button) findViewById(R.id.new_timeAttack_button);
		newBalanceGameButton = (Button) findViewById(R.id.new_balance_button);
	}

	private void setUpListeners() {
		newTimeAttackGameButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startGame("timeAttack");
			}
		});
		newBalanceGameButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startGame("balance");
			}
		});
	}

	private void startGame(String gameMode) {
		if (gameMode.equals("timeAttack")) {
			Intent intent = new Intent(getApplicationContext(),
					TimeAttackActivity.class);
			startActivity(intent);
		} else if (gameMode.equals("balance")) {
			Intent intent = new Intent(getApplicationContext(),
					BalanceActivity.class);
			startActivity(intent);
		}

	}

}