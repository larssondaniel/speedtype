package com.chalmers.speedtype.util;

public class Config {

	private static boolean saveScores;
	private static int fxVolume;
	private static int musicVolume;

	public static boolean isSaveScores() {
		return saveScores;
	}

	public static void setSaveScores(boolean saveScores) {
		Config.saveScores = saveScores;
	}

	public static int getFxVolume() {
		return fxVolume;
	}

	public static void setFxVolume(int fxVolume) {
		Config.fxVolume = fxVolume;
	}

	public static int getMusicVolume() {
		return musicVolume;
	}

	public static void setMusicVolume(int musicVolume) {
		Config.musicVolume = musicVolume;
	}
}
