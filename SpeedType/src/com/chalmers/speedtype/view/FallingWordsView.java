package com.chalmers.speedtype.view;

import java.util.LinkedList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.chalmers.speedtype.model.FallingWordsModel;
import com.chalmers.speedtype.model.Model;
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

		if (activeWord != null) {
			drawActiveWord(canvas, activeWord, currentCharPos);
			drawInactiveWords(canvas);
		}

		drawScore(canvas);
	}

	private void drawActiveWord(Canvas canvas, Word word,
			int currentCharPos) {
		drawCompletedChars(canvas, word, currentCharPos);
		drawIncompletedChars(canvas, word, currentCharPos);
	}

	private void drawCompletedChars(Canvas canvas, Word word,
			int currentCharPos) {

		greenPaint.setTextSize(activeWord.getSize());
		greenPaint.setTypeface(mensch);
		
		int x = calculateX(word, greenPaint);

		canvas.drawText(word.substring(0, currentCharPos),
				x, word.getY(), greenPaint);
	}

	private void drawIncompletedChars(Canvas canvas, Word word,
			int currentCharPos) {

		whitePaint.setTextSize(word.getSize());
		whitePaint.setTypeface(mensch);

		float x = calculateX(word, whitePaint) + whitePaint.measureText(word
				.substring(0, currentCharPos));

		canvas.drawText(
				word.substring(currentCharPos, word.length()), x,
				word.getY(), whitePaint);
	}

	private void drawInactiveWords(Canvas canvas) {
		grayPaint.setTypeface(mensch);

		visibleWords = model.getVisibleWords();

		int x;
		for (int i = 1; i < visibleWords.size(); i++) {
			grayPaint.setTextSize(visibleWords.get(i).getSize());
			x = calculateX(visibleWords.get(i), grayPaint);
			canvas.drawText(visibleWords.get(i).toString(), x, visibleWords
					.get(i).getY(), grayPaint);
		}
	}

	private void drawScore(Canvas canvas) {
		whitePaint.setTextSize(40);
		whitePaint.setTypeface(Typeface.SANS_SERIF);
		score = model.getScore();

		float margin = getDisplayWidthFromPercentage(2);
		float x = (displayWidth - whitePaint.measureText(score + "") - margin);
		float y = whitePaint.getTextSize() + margin;
		canvas.drawText(model.getScore() + "", x, y, whitePaint);
	}

	private int calculateX(Word word, Paint paint) {
		int span = (int) (displayWidth - paint.measureText(word.toString()));
		return word.getX() * span / 100;
	}
}