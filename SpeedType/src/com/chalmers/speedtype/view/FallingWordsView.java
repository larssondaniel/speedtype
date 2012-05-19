package com.chalmers.speedtype.view;

import java.util.LinkedList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.util.AttributeSet;

import com.chalmers.speedtype.model.FallingWordsModel;
import com.chalmers.speedtype.model.GameModel;
import com.chalmers.speedtype.model.Word;

public class FallingWordsView extends GameView {

	private FallingWordsModel model;
	
	private LinkedList<Word> visibleWords;
	private Word activeWord;
	private int currentCharPos;
	private int score;
	
	private Paint completedCharsPaint;
	private Paint incompleteCharsPaint;
	private Paint scorePaint;
	private Paint linePaint;

	private Paint inactiveWordsPaint;

	public FallingWordsView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FallingWordsView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FallingWordsView(Context context) {
		super(context);
	}

	public void setModel(GameModel model) {
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
		canvas.drawLine(0, getDisplayHeightFromPercentage(50) + 3, displayWidth, getDisplayHeightFromPercentage(50), linePaint);
	}

	private void drawActiveWord(Canvas canvas, Word word,
			int currentCharPos) {
		drawCompletedChars(canvas, word, currentCharPos);
		drawIncompletedChars(canvas, word, currentCharPos);
	}

	private void drawCompletedChars(Canvas canvas, Word word,
			int currentCharPos) {

		completedCharsPaint.setTextSize(activeWord.getSize());
		
		int x = calculateX(word, completedCharsPaint);

		canvas.drawText(word.substring(0, currentCharPos),
				x, (int) word.getY(), completedCharsPaint);
	}

	private void drawIncompletedChars(Canvas canvas, Word word,
			int currentCharPos) {

		incompleteCharsPaint.setTextSize(word.getSize());

		float x = calculateX(word, incompleteCharsPaint) + incompleteCharsPaint.measureText(word
				.substring(0, currentCharPos));

		canvas.drawText(
				word.substring(currentCharPos, word.length()), x,
				(int) word.getY(), incompleteCharsPaint);
	}

	private void drawInactiveWords(Canvas canvas) {
		visibleWords = model.getVisibleWords();

		int x;
		for (int i = 1; i < visibleWords.size(); i++) {
			Word word = visibleWords.get(i);
			inactiveWordsPaint.setTextSize(word.getSize());
			x = calculateX(word, inactiveWordsPaint);

			canvas.drawText(word.toString(), x, (int) word.getY(), inactiveWordsPaint);
		}
	}

	private void drawScore(Canvas canvas) {
		score = model.getScore();

		float margin = getDisplayWidthFromPercentage(2);
		float x = (displayWidth - scorePaint.measureText(score + "") - margin);
		float y = scorePaint.getTextSize() + margin;
		canvas.drawText(model.getScore() + "", x, y, scorePaint);
	}

	private int calculateX(Word word, Paint paint) {
		int span = (int) (displayWidth - paint.measureText(word.toString()));
		return (int)word.getX() * span / 100;
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		completedCharsPaint = greenPaint;
		completedCharsPaint.setAntiAlias(true);
		completedCharsPaint.setStyle(Style.FILL);
		completedCharsPaint.setFakeBoldText(true);
		
		incompleteCharsPaint = new Paint(whitePaint);
		incompleteCharsPaint.setFakeBoldText(true);
		
		inactiveWordsPaint = new Paint(whitePaint);
		inactiveWordsPaint.setColor(Color.argb(150, 255, 255, 255));
		
		scorePaint = new Paint(whitePaint);
		scorePaint.setTextSize(40);
		scorePaint.setTypeface(Typeface.SANS_SERIF);
		
		linePaint = new Paint(redPaint);
		linePaint.setStyle(Style.FILL);
		
	}
}