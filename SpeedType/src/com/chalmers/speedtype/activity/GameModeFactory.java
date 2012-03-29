package com.chalmers.speedtype.activity;

import android.content.Context;
import android.content.Intent;

public class GameModeFactory {
	
	String TA = "TimeAttack";
	
	public Intent createGameMode (String game, Context c){
		if(game.equals(TA)){
			//Intent is not working properly, pushing it up on request of Olle so he can give it a try and fix it <3
			return new Intent(c, TimeAttackActivity.class);
		}
		return null;
	}
	public String[] GetGamesModes(){
		String[] GameModes = {TA};
		return GameModes;
	}
	
}
