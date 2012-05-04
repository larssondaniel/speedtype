package com.chalmers.speedtype.activity;

import com.chalmers.speedtype.R;
import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends SwarmActivity {
    
	private Button newGameButton;
	private Button exitButton;
	private Button trophyRoomButton;

	private GameModeFactory gameFactory;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        setUpViews();
        setUpListeners();
        setUpSwarm();

        gameFactory = new GameModeFactory();
    }
    
	private void setUpViews() {
		newGameButton = (Button) findViewById(R.id.new_game_button);
		trophyRoomButton = (Button) findViewById(R.id.trophy_room_button);
		exitButton = (Button) findViewById(R.id.exit_button);
	}
	private void setUpSwarm(){
        Swarm.init(this, 638, "66eebd36a2c3a1541b00530f532d16aa");
        
   	}
	
	private void setUpListeners() {
		newGameButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startGame();
			}
		});
		trophyRoomButton.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		        Swarm.showDashboard();
		    }
		});
		exitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				System.exit(0);
			}
		});
	}

	private void startGame() {
		GameMode g = gameFactory.createGameMode("TimeAttack");

		if (g == null) {
			System.out.print("No activity recieved by gameFactory");
		} else {
			startActivity(new Intent(getApplicationContext(), g.getClass()));
		}
	}
}
