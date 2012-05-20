package com.chalmers.speedtype.util;

import android.content.Context;

import com.chalmers.speedtype.model.*;

public class GameFactory {
	

	private static final String TA = "TimeAttack";
	private static final String FW = "FallingWords";
	private static final String BG = "Balance";
	private static final String SC = "Scrabble";

	public static GameModel createGameMode(Context activity, String game) {
		if (game.equals(TA)) {
			return new TimeAttackModel();
		} else if (game.equals(FW)) {
			return new FallingWordsModel();
		} else if (game.equals(BG)) {
			return new BalanceModel();
		} else if (game.equals(SC)){
			return new ScrabbleModel();
		} else {
			return null;
		}
	}

	public static String[] getGamesModes() {
		String[] GameModes = { TA, FW, BG, SC };
		return GameModes;
	}
}