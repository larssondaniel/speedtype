package com.chalmers.speedtype.view;

import java.util.ArrayList;
import java.util.LinkedList;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.chalmers.speedtype.model.FallingWordsModel;
import com.chalmers.speedtype.model.Model;
import com.chalmers.speedtype.model.TimeAttackModel;
import com.chalmers.speedtype.model.Word;

public class FallingWordsView extends GameView {

	private FallingWordsModel model;
	private LinkedList<Word> visibleWords;
	private Word activeWord;
	private int currentCharPos;
	private int score;

	public FallingWordsView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FallingWordsView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FallingWordsView(Context context) {
		super(context);
	}

	public void setModel(Model model) {
		super.setModel(model);
		this.model = (FallingWordsModel) model;
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		activeWord = model.getActiveWord();
		currentCharPos = model.getCurrentCharPos();

		drawScore(canvas);
		drawInactiveWords(canvas);
		drawActiveWord(canvas, activeWord, currentCharPos);
		drawCompletedChars(canvas, activeWord, currentCharPos);
		drawIncompletedChars(canvas, activeWord, currentCharPos);
	}

	private void drawInactiveWords(Canvas canvas) {
		grayPaint.setTextSize(60);
		grayPaint.setTypeface(mensch);
		
		visibleWords = model.getVisibleWords();
		
		for(int i = 1; i < visibleWords.size(); i++)
			canvas.drawText(visibleWords.get(i).toString(), visibleWords.get(i).getX(), visibleWords.get(i).getY(), grayPaint);
		
	}

	private void drawActiveWord(Canvas canvas, Word activeWord, int currentCharPos) {
		drawCompletedChars(canvas, activeWord, currentCharPos);
		drawIncompletedChars(canvas, activeWord, currentCharPos);
	}

	private void drawScore(Canvas canvas) {
		whitePaint.setTextSize(40);
		whitePaint.setTypeface(mensch);
		score = model.getScore();
		float x = (displayWidth - whitePaint.measureText(score + "") - getDisplayWidthFromPercentage(2));
		float y = whitePaint.getTextSize() + getDisplayHeightFromPercentage(2);
		canvas.drawText(model.getScore() + "", x, y, whitePaint);

	}

	private void drawCompletedChars(Canvas canvas, Word activeWord,
			int currentCharPos) {

		greenPaint.setTextSize(60);
		greenPaint.setTypeface(mensch);
		float x = displayWidth / 2
				- greenPaint.measureText(activeWord.toString()) / 2;
		float y = greenPaint.getTextSize() + getDisplayHeightFromPercentage(35);

		canvas.drawText(activeWord.substring(0, currentCharPos), activeWord.getX(), activeWord.getY(),
				greenPaint);
	}

	private void drawIncompletedChars(Canvas canvas, Word activeWord,
			int currentCharPos) {

		whitePaint.setTextSize(60);
		whitePaint.setTypeface(mensch);

		float x = activeWord.getX() + whitePaint.measureText(activeWord.substring(0, currentCharPos));
		float y = greenPaint.getTextSize() + getDisplayHeightFromPercentage(35);

		canvas.drawText(
				activeWord.substring(currentCharPos, activeWord.length()), x,
				activeWord.getY(), whitePaint);
	}
}
