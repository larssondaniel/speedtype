package com.chalmers.speedtype.view;

import com.chalmers.speedtype.model.Model;
import com.chalmers.speedtype.model.TimeAttackModel;
import com.chalmers.speedtype.model.Word;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class TimeAttackView extends GameView {

	private TimeAttackModel model;

	public TimeAttackView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TimeAttackView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TimeAttackView(Context context) {
		super(context);
	}

	public void setModel(Model model) {
		super.setModel(model);
		this.model = (TimeAttackModel) model;
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Word activeWord = model.getActiveWord();
		Word nextWord = model.getNextWord();
		int CurrentCharPos = model.getCurrentCharPos();

		drawScore(canvas);
		drawNextWord(canvas, nextWord);
		drawCompletedChars(canvas, activeWord, CurrentCharPos);
		drawIncompletedChars(canvas, activeWord, CurrentCharPos);
	}

	private void drawNextWord(Canvas canvas, Word nextWord) {
		grayPaint.setTextSize(60);
		grayPaint.setTypeface(mensch);
		float x = displayWidth / 2
		- grayPaint.measureText(nextWord.toString()) / 2;
		float y = grayPaint.getTextSize() + getDisplayHeightFromPercentage(20);
		canvas.drawText(nextWord + "", x, y, grayPaint);
	}

	private void drawScore(Canvas canvas) {
		whitePaint.setTextSize(40);
		whitePaint.setTypeface(mensch);
		int score = model.getScore();
		float x = (displayWidth - whitePaint.measureText(score + "") - getDisplayWidthFromPercentage(2));
		float y = whitePaint.getTextSize() + getDisplayHeightFromPercentage(2);
		canvas.drawText(model.getScore() + "", x, y, whitePaint);

	}

	private void drawCompletedChars(Canvas canvas, Word activeWord,
			int currentCharPos) {

		greenPaint.setTextSize(100);
		greenPaint.setTypeface(mensch);
		float x = displayWidth / 2
				- greenPaint.measureText(activeWord.toString()) / 2;
		float y = greenPaint.getTextSize() + getDisplayHeightFromPercentage(35);

		canvas.drawText(activeWord.substring(0, currentCharPos), x, y,
				greenPaint);
	}

	private void drawIncompletedChars(Canvas canvas, Word activeWord,
			int currentCharPos) {

		whitePaint.setTextSize(100);
		whitePaint.setTypeface(mensch);

		float x = displayWidth
				/ 2
				- whitePaint.measureText(activeWord.toString())
				/ 2
				+ whitePaint.measureText(activeWord
						.substring(0, currentCharPos));
		float y = greenPaint.getTextSize() + getDisplayHeightFromPercentage(35);

		canvas.drawText(
				activeWord.substring(currentCharPos, activeWord.length()), x,
				y, whitePaint);
	}

	private int getDisplayWidthFromPercentage(int i) {
		return displayWidth / 100 * i;
	}

	private int getDisplayHeightFromPercentage(int i) {
		return displayHeight / 100 * i;
	}

}