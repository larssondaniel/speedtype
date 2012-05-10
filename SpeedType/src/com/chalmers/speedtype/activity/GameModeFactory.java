package com.chalmers.speedtype.activity;

import android.content.Context;

import com.chalmers.speedtype.model.BalanceModel;
import com.chalmers.speedtype.model.Model;
import com.chalmers.speedtype.model.TimeAttackModel;

public class GameModeFactory {

	private static final String TA = "TimeAttack";
	private static final String FW = "FallingWords";
	private static final String BG = "BalanceGame";

	public static Model createGameMode(Context activity, String game) {
		if (game.equals(TA)) {
			return new TimeAttackModel();
		} else if (game.equals(FW)) {
			return null;
		} else if (game.equals(BG)) {
			return new BalanceModel();
		} else {
			return null;
		}
	}

	public static String[] getGamesModes() {
		String[] GameModes = { TA, FW };
		return GameModes;
	}
}