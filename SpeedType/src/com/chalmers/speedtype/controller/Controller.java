package com.chalmers.speedtype.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.chalmers.speedtype.model.GameModel;
import com.swarmconnect.SwarmLeaderboard;
import com.swarmconnect.SwarmLeaderboard.GotLeaderboardCB;

public class Controller extends Thread {

	public static final int STATE_RUNNING = 1;
	public static final int STATE_READY = 2;
	public static final int STATE_PAUSED = 3;
	public static final int STATE_GAMEOVER = 4;

	private final String SAVE_SCORES = "saveScores";

	private int gameState;
	private boolean isRunning = false;

	private SwarmLeaderboard leaderboard;

	SharedPreferences preferences;

	private GameModel model;
	private Handler handler;
	private Context context;

	public Controller(GameModel model, Context context, Handler handler) {
		this.model = model;
		this.context = context;
		this.handler = handler;

		setUpSwarmLeaderboard();

		setState(STATE_READY);
	}

	private void setUpSwarmLeaderboard() {
		GotLeaderboardCB callback = new GotLeaderboardCB() {
			public void gotLeaderboard(SwarmLeaderboard leaderboard1) {

				if (leaderboard1 != null) {
					// Save the leaderboard for later use
					leaderboard = leaderboard1;
				}
			}
		};
		SwarmLeaderboard.getLeaderboardById(model.getSwarmLeaderBoardID(),
				callback);
	}

	public synchronized void setState(int mode) {
		gameState = mode;

		Message message = handler.obtainMessage();
		Bundle bundle = new Bundle();
		String text = "";
		String manualText = "";
		String startText = "";

		if (gameState == STATE_RUNNING) {
			bundle.putString("text", text);
			bundle.putInt("visibility", View.INVISIBLE);
			bundle.putString("manualText", manualText);
			bundle.putString("startText", startText);
			message.setData(bundle);
			handler.sendMessage(message);
			model.setGameState(model.STATE_RUNNING);
		} else {
			switch (gameState) {
			case STATE_READY:
				text = "READY";
				startText = "Tap the screen to start";
				break;
			case STATE_PAUSED:
				text = "PAUSED";
				startText = "Tap the screen to continue";
				break;
			case STATE_GAMEOVER:
				text = "GAME OVER\n\nScore: " + model.getScore();
				preferences = context.getSharedPreferences("myPrefs",
						Context.MODE_WORLD_READABLE);
				if (preferences.getBoolean(SAVE_SCORES, true)) {
					registerHighscore();
				}
				endGame();
				break;
			default:
				text = "SOMETHING IS WRONG";
				break;
			}

			bundle.putString("text", text);
			bundle.putString("manualText", manualText);
			bundle.putInt("visibility", View.VISIBLE);
			bundle.putString("startText", startText);
			bundle.putInt("manualVisibility", View.VISIBLE);
			bundle.putInt("startTextVisibility", View.VISIBLE);
			message.setData(bundle);
			handler.sendMessage(message);
		}
	}

	private void registerHighscore() {
		if (leaderboard != null) {
			leaderboard.submitScore(model.getScore());
		}
	}

	public void startGame() {
		isRunning = true;
		if (model.isContinuous())
			start();
	}

	public void endGame() {
		if (model.isContinuous())
			isRunning = false;
	}

	@Override
	public void run() {
		synchronized (model) {
			while (isRunning) {
				if (gameState == STATE_RUNNING) {
					model.update();
					if (model.isGameOver()) {
						setState(STATE_GAMEOVER);
					} else if (model.getGameState() == model.STATE_PAUSED) {
						pause();
					}
				}
			}
		}
	}

	public int getGameState() {
		return gameState;
	}

	public void pause() {
		if (gameState == STATE_RUNNING)
			setState(STATE_PAUSED);
	}

	public boolean onKey(KeyEvent event) {
		if (gameState == STATE_RUNNING) {
			model.onInput(event);
			return true;
		}
		return false;
	}

	public void onSensorChanged(SensorEvent event, int displayRotation) {
		if (gameState == STATE_RUNNING && model.isSensorDependent())
			model.onSensorChanged(event, displayRotation);
	}

	public boolean onTouch(MotionEvent event) {
		if (gameState == STATE_READY || gameState == STATE_PAUSED) {
			setState(STATE_RUNNING);
			return true;
		}
		return false;
	}
}