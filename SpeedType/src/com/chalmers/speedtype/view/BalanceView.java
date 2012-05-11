package com.chalmers.speedtype.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.BitmapFactory.Options;
import android.hardware.Sensor;
import android.util.AttributeSet;
import com.chalmers.speedtype.R;
import com.chalmers.speedtype.model.BalanceModel;
import com.chalmers.speedtype.model.Model;
import com.chalmers.speedtype.model.Word;
import com.chalmers.speedtype.util.Util;

public class BalanceView extends GameView {

	private BalanceModel model;
	private static final float ballDiameter = 0.006f;

	private Bitmap ballImage;
	private Bitmap boardImage;
	private float xOrigin;
	private float yOrigin;
	private float sensorX;
	private long sensorTimeStamp;
	private long cpuTimeStamp;

	public BalanceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public BalanceView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BalanceView(Context context) {
		super(context);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		// Scaling of the ball
		Bitmap ball = BitmapFactory.decodeResource(getResources(),
				R.drawable.ball);
		final int dstWidth = (int) (ballDiameter * Util.getMetersToPixelsX());
		final int dstHeight = (int) (ballDiameter * Util.getMetersToPixelsY());
		ballImage = Bitmap.createScaledBitmap(ball, dstWidth, dstHeight, true);

		Options opts = new Options();
		opts.inDither = true;
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		Bitmap planka = BitmapFactory.decodeResource(getResources(),
				R.drawable.wood, opts);
		boardImage = Bitmap.createScaledBitmap(planka,
				(int) Util.getMetersToPixelsX(), dstHeight, true);
	}

	public void setModel(Model model) {
		super.setModel(model);
		this.model = (BalanceModel) model;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		canvas.drawBitmap(boardImage, 0,
				Util.getMetersToPixelsY() * model.getVerticalBound() * 2 / 8
						+ 0.005f * Util.getMetersToPixelsY(), null);

		final long now = sensorTimeStamp + (System.nanoTime() - cpuTimeStamp);
		final float sx = sensorX;

		model.updateParticle(sx, now);

		final float xc = xOrigin;
		final float yc = yOrigin;
		final float xs = Util.getMetersToPixelsX();
		final float ys = Util.getMetersToPixelsY();
		final Bitmap bitmap = ballImage;
		final float x = xc + model.getParticle().getPosX() * xs;
		final float y = yc - model.getParticle().getPosY() * ys;
		canvas.drawBitmap(bitmap, x, y, null);

		Word activeWord = model.getActiveWord();
		Word nextWord = model.getNextWord();
		int CurrentCharPos = model.getCurrentCharPos();

		drawScore(canvas);
		drawNextWord(canvas, nextWord);
		drawCompletedChars(canvas, activeWord, CurrentCharPos);
		drawIncompletedChars(canvas, activeWord, CurrentCharPos);

		invalidate();
	}

	private void drawNextWord(Canvas canvas, Word nextWord) {
		grayPaint.setTextSize(60);
		grayPaint.setTypeface(mensch);
		float x = displayWidth / 2 - grayPaint.measureText(nextWord.toString())
				/ 2;
		float y = grayPaint.getTextSize() + getDisplayHeightFromPercentage(30);
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
		float y = greenPaint.getTextSize() + getDisplayHeightFromPercentage(45);

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
		float y = greenPaint.getTextSize() + getDisplayHeightFromPercentage(45);

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

	public void onAccuracyChanged(Sensor arg0, int arg1) {

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// Calculate the middle of the screen
		super.onSizeChanged(w, h, oldw, oldh);
		xOrigin = (w - ballImage.getWidth()) * 0.5f;
		yOrigin = (h - ballImage.getHeight()) * 0.5f;
		model.setHorizontalBound(((w / Util.getMetersToPixelsX() - ballDiameter) * 0.5f));
		model.setVerticalBound(((h / Util.getMetersToPixelsY() - ballDiameter) * 0.5f));
	}
}
