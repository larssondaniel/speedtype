package com.chalmers.speedtype.view;

//TODO NOT WORKING! SHOULD BE FIXED ASAP, NULLPOINTEREXCEPTION.

import com.chalmers.speedtype.model.GameModel;
import com.chalmers.speedtype.model.ScrabbleModel;
import com.chalmers.speedtype.model.Word;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class ScrabbleView extends GameView {
	private ScrabbleModel model;
	private int count;
	private Paint timeLeftPaint;
	private Paint timeLeftPlainPaint;
	private Paint timeLeftCriticalPaint;

	private String activeScrabbledWord;
	private Word activeWord;

	public ScrabbleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ScrabbleView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrabbleView(Context context) {
		super(context);
	}

	public void setModel(GameModel model) {
		super.setModel(model);
		this.model = (ScrabbleModel) model;
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		activeWord = model.getActiveWord();
		activeScrabbledWord = model.getActiveScrabbledWord();
		int currentCharPos = model.getCurrentCharPos();

		drawScore(canvas);
		drawTimeLeft(canvas);
		drawScrabbledWord(canvas, activeScrabbledWord);
		drawCompletedChars(canvas, activeWord, currentCharPos);
	}

	private void drawTimeLeft(Canvas canvas) {
		int timeLeft = model.getTimeLeft();
		String timeLeftString;

		if (timeLeft < 10000) {
			timeLeft = timeLeft / 100 * 100;
			double timeLeftLow = ((double) timeLeft) / 1000;
			timeLeftString = timeLeftLow + "";
			count++;
			if (count == 3) {
				count = 0;
				if (timeLeftPaint == timeLeftPlainPaint) {
					timeLeftPaint = timeLeftCriticalPaint;
				} else {
					timeLeftPaint = timeLeftPlainPaint;
				}
			}
		} else {
			timeLeftPaint = timeLeftPlainPaint;
			timeLeft = timeLeft / 1000;
			timeLeftString = timeLeft + "";
		}
		float x = displayWidth / 2 - timeLeftPaint.measureText(timeLeftString)
				/ 2;
		float y = timeLeftPaint.getTextSize()
				+ getDisplayHeightFromPercentage(5);

		canvas.drawText(timeLeftString, x, y, timeLeftPaint);
	}

	private void drawScrabbledWord(Canvas canvas, String word) {
		whitePaint.setTextSize(80);
		whitePaint.setTypeface(mensch);
		float x = getDisplayWidthFromPercentage(50)
				- whitePaint.measureText(word.toString()) / 2;
		float y = whitePaint.getTextSize() + getDisplayHeightFromPercentage(20);
		canvas.drawText(word.toString(), x, y, whitePaint);
	}

	private void drawScore(Canvas canvas) {
		whitePaint.setTextSize(40);
		whitePaint.setTypeface(mensch);
		int score = model.getScore();

		float x = (displayWidth - whitePaint.measureText(score + "") - getDisplayWidthFromPercentage(2));
		float y = whitePaint.getTextSize() + getDisplayHeightFromPercentage(2);

		canvas.drawText(score + "", x, y, whitePaint);
	}

	private void drawCompletedChars(Canvas canvas, Word activeWord,
			int currentCharPos) {
		yellowPaint.setTextSize(100);
		yellowPaint.setTypeface(mensch);
		float x = getDisplayWidthFromPercentage(50)
				- yellowPaint.measureText(activeWord.toString()) / 2;
		float y = yellowPaint.getTextSize()
				+ getDisplayHeightFromPercentage(35);
		canvas.drawText(activeWord.substring(0, currentCharPos), x, y,
				yellowPaint);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		timeLeftPaint = whitePaint;

		timeLeftPlainPaint = new Paint(whitePaint);
		timeLeftPlainPaint.setTextSize(70);
		timeLeftPlainPaint.setTypeface(Typeface.MONOSPACE);

		timeLeftCriticalPaint = new Paint(timeLeftPlainPaint);
		timeLeftCriticalPaint.setColor(Color.RED);

		timeLeftPaint = timeLeftPlainPaint;
	}
}
