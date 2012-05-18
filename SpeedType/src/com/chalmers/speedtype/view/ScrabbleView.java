package com.chalmers.speedtype.view;
//TODO NOT WORKING! SHOULD BE FIXED ASAP, NULLPOINTEREXCEPTION.

import com.chalmers.speedtype.model.GameModel;
import com.chalmers.speedtype.model.ScrabbleModel;
import com.chalmers.speedtype.model.Word;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;

public class ScrabbleView extends GameView {
	private ScrabbleModel model;
	private int count;
	private Paint timeLeftPaint;
	private Paint blackPaint;
	private Word activeScrabbledWord;
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
	
	private void initBlackPaint(){
		blackPaint = new Paint();
		blackPaint.setColor(Color.BLACK);
		blackPaint.setAntiAlias(true);
		blackPaint.setStyle(Style.FILL);
	}
	
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		
		initBlackPaint();
		
		activeWord = model.getActiveWord();
		activeScrabbledWord = model.getActiveScrabbledWord();
		int currentCharPos = model.getCurrentCharPos();
		
		drawScore(canvas);
		drawTimeLeft(canvas);
		drawNextWord(canvas, activeScrabbledWord);
		drawCompletedChars(canvas, activeWord, currentCharPos);
		drawIncompletedChars(canvas, activeWord, currentCharPos);
		drawActiveChar(canvas, activeWord, currentCharPos);
	}
	
	private void drawTimeLeft(Canvas canvas){
		whitePaint.setTextSize(100);
		whitePaint.setTypeface(mensch);
		redPaint.setTextSize(100);
		redPaint.setTypeface(mensch);
		
		int timeLeft = model.getTimeLeft();
		String timeLeftString;
		
		if (timeLeft < 10000){
			double timeLeftLow = ((double) timeLeft)/1000;
			timeLeftString = timeLeftLow + "";
			count++;
			if (count == 3){
				count = 0;
				if (timeLeftPaint == whitePaint){
					timeLeftPaint = redPaint;
				} else {
					timeLeftPaint = whitePaint;
				}
			}
		} else {
			timeLeftPaint = whitePaint;
			timeLeft = timeLeft / 1000;
			timeLeftString = timeLeft + "";
		}
		float x = displayWidth / 2 - whitePaint.measureText(timeLeftString) / 2;
		float y = whitePaint.getTextSize() + getDisplayHeightFromPercentage(5);

		canvas.drawText(timeLeftString, x, y, timeLeftPaint);
	}
	
	private void drawNextWord(Canvas canvas, Word nextWord){
		whitePaint.setTextSize(80);
		whitePaint.setTypeface(mensch);
		float x = getDisplayWidthFromPercentage(50) - whitePaint.measureText(nextWord.toString()) / 2;
		float y = whitePaint.getTextSize() + getDisplayHeightFromPercentage(20);
		canvas.drawText(nextWord.toString(), x, y, whitePaint);
	}
	
	private void drawScore(Canvas canvas){
		whitePaint.setTextSize(40);
		whitePaint.setTypeface(mensch);
		int score = model.getScore();
		
		float x = (displayWidth - whitePaint.measureText(score + "") - getDisplayWidthFromPercentage(2));
		float y = whitePaint.getTextSize() + getDisplayHeightFromPercentage(2);
		
		canvas.drawText(score + "", x, y, whitePaint);
	}
	
	private void drawCompletedChars(Canvas canvas, Word activeWord, int currentCharPos){
		greenPaint.setTextSize(100);
		greenPaint.setTypeface(mensch);
		float x = getDisplayWidthFromPercentage(50)
				- greenPaint.measureText(activeWord.toString()) / 2;
		float y = greenPaint.getTextSize() + getDisplayHeightFromPercentage(35);

		canvas.drawText(activeWord.substring(0, currentCharPos), x, y, greenPaint);
	}
	
	private void drawActiveChar(Canvas canvas, Word activeWord, int currentCharPos){
		blackPaint.setTextSize(100);
		blackPaint.setTypeface(mensch);

		float x = getDisplayWidthFromPercentage(50) - blackPaint.measureText(activeWord.toString()) / 2 + blackPaint.measureText(activeWord.substring(0, currentCharPos));
		float y = blackPaint.getTextSize() + getDisplayHeightFromPercentage(35);

		canvas.drawText(
				activeWord.substring(currentCharPos, (currentCharPos + 1)), x,
				y, blackPaint);
	}
	
	private void drawIncompletedChars(Canvas canvas, Word activeWord,
			int currentCharPos) {

		blackPaint.setTextSize(100);
		blackPaint.setTypeface(mensch);

		float x = getDisplayWidthFromPercentage(50)
				- blackPaint.measureText(activeWord.toString())
				/ 2
				+ blackPaint.measureText(activeWord
						.substring(0, currentCharPos + 1));
		float y = blackPaint.getTextSize() + getDisplayHeightFromPercentage(35);
	
		canvas.drawText(
						activeWord.substring(currentCharPos + 1, activeWord.length()), x,
						y, blackPaint);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		timeLeftPaint = whitePaint;
	}
}
