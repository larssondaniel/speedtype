package com.chalmers.speedtype.activity;

public class GameModeFactory {
	
	String TA = "TimeAttack";
	
	public GameMode createGameMode (String game){
		if(game.equals(TA)){
			return new TimeAttackActivity();
//		}else if( ... ){
//			...
		}else{
			return null;
		}
	}
	public String[] GetGamesModes(){
		String[] GameModes = {TA};
		return GameModes;
	}
}