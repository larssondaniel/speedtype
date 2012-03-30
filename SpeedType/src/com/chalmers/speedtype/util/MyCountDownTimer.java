package com.chalmers.speedtype.util;

import android.os.CountDownTimer;
import android.widget.TextView;

public class MyCountDownTimer extends CountDownTimer {

	TextView wordView;
	TextView nextWordView;
	TextView timeView;
	TextView scoreView;
	static int score;
	static long timeLeft;

	public MyCountDownTimer(long timeLeft, long updateInterval,
			TextView wordView, TextView nextWordView, TextView timeView,
			TextView scoreView) {
		super(timeLeft, updateInterval);

		System.out.println("Constructor");

		this.wordView = wordView;
		this.nextWordView = nextWordView;
		this.timeView = timeView;
		this.scoreView = scoreView;
		MyCountDownTimer.timeLeft = timeLeft;

	}

	@Override
	public void onFinish() {
		timeView.setText("");
		nextWordView.setText("Game over!");
		wordView.setText(score + "");
	}

	@Override
	public void onTick(long millisTimeLeft) {
		timeView.setText("" + (double) timeLeft / 1000);
		timeLeft = timeLeft - 100;
		System.out.println("Timeleft:" + timeLeft);
	}

	public long getTimeLeft() {
		return timeLeft;
	}
}
