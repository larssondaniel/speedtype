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

		invalidate();
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
