package com.chalmers.speedtype.activity;

import android.content.Context;
import com.chalmers.speedtype.model.ExampleModel;
import com.chalmers.speedtype.model.Model;

public class GameModeFactory {
	
	private static final String TA = "TimeAttack";
	private static final String FW = "FallingWords";
	
	public static Model createGameMode (Context activity, String game) {
		if(game.equals(TA)){
			return null; //new TimeAttackActivity();
		}else if(game.equals(FW)){
			return new ExampleModel();
		}else{
			return null;
		}
	}
	public static String[] getGamesModes() {
		String[] GameModes = {TA, FW};
		return GameModes;
	}
}