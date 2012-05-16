package com.chalmers.speedtype.view;

import com.chalmers.speedtype.model.Model;
import com.chalmers.speedtype.model.ScrabbleModel;
import com.chalmers.speedtype.model.Word;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

public class ScrabbleView extends GameView {
	private ScrabbleModel model;
	private int count;
	private Paint timeLeftPaint;
	
	public ScrabbleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public ScrabbleView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrabbleView(Context context) {
		super(context);
	}
	
	public void setModel(Model model) {
		super.setModel(model);
		this.model = (ScrabbleModel) model;
	}
	
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		
		Word activeScrabbledWord = model.getActiveScrabbledWord();
		Word nextScrabbledWord = model.getNextScrabbledWord();
		int currentCharPos = model.getCurrentCharPos();
		
		drawScore(canvas);
		drawTimeLeft(canvas);
		drawNextWord(canvas, nextScrabbledWord);
		drawCompletedChars(canvas, activeScrabbledWord, currentCharPos);
		drawIncompletedChars(canvas, activeScrabbledWord, currentCharPos);
		drawActiveChar(canvas, activeScrabbledWord, currentCharPos);
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
		grayPaint.setTextSize(60);
		grayPaint.setTypeface(mensch);
		float x = getDisplayWidthFromPercentage(50) - grayPaint.measureText(nextWord.toString()) / 2;
		float y = grayPaint.getTextSize() + getDisplayHeightFromPercentage(20);
		canvas.drawText(nextWord.toString(), x, y, grayPaint);
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
		whitePaint.setTextSize(100);
		whitePaint.setTypeface(mensch);
		redPaint.setTextSize(100);
		redPaint.setTypeface(mensch);
		Paint activePaint = whitePaint;
		
		float x = getDisplayWidthFromPercentage(50) - whitePaint.measureText(activeWord.toString()) / 2 + whitePaint.measureText(activeWord.substring(0, currentCharPos));
		float y = greenPaint.getTextSize() + getDisplayHeightFromPercentage(35);
		
		if(model.correctInputReport() != true){
			activePaint = redPaint;
		}
		
		canvas.drawText(
				activeWord.substring(currentCharPos, (currentCharPos + 1)), x,
				y, activePaint);
	}
	
	private void drawIncompletedChars(Canvas canvas, Word activeWord,
			int currentCharPos) {

		whitePaint.setTextSize(100);
		whitePaint.setTypeface(mensch);

		float x = getDisplayWidthFromPercentage(50)
				- whitePaint.measureText(activeWord.toString())
				/ 2
				+ whitePaint.measureText(activeWord
						.substring(0, currentCharPos + 1));
		float y = greenPaint.getTextSize() + getDisplayHeightFromPercentage(35);

		canvas.drawText(
				activeWord.substring(currentCharPos + 1, activeWord.length()), x,
				y, whitePaint);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		timeLeftPaint = whitePaint;
	}
}