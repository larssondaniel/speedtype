package com.chalmers.speedtype.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.chalmers.speedtype.model.GameModel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public abstract class GameView extends View implements PropertyChangeListener {

	protected Typeface mensch;

	private GameModel model;

	protected int displayWidth;
	protected int displayHeight;
	protected Paint whitePaint;
	protected Paint grayPaint;
	protected Paint greenPaint;
	protected Paint redPaint;
	protected Paint bluePaint;
	
	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GameView(Context context) {
		super(context);
	}

	private void initView() {
		mensch = Typeface.createFromAsset(getResources().getAssets(),
				"fonts/mensch.ttf");

		whitePaint = new Paint();
		whitePaint.setColor(Color.WHITE);
		whitePaint.setAntiAlias(true);
		whitePaint.setStyle(Style.FILL);

		grayPaint = new Paint();
		grayPaint.setColor(Color.GRAY);
		grayPaint.setAntiAlias(true);
		grayPaint.setStyle(Style.FILL);

		greenPaint = new Paint();
		greenPaint.setColor(Color.GREEN);
		greenPaint.setAntiAlias(true);
		greenPaint.setStyle(Style.FILL);

		redPaint = new Paint();
		redPaint.setColor(Color.RED);
		redPaint.setAntiAlias(true);
		redPaint.setStyle(Style.FILL);
		
		bluePaint = new Paint();
		bluePaint.setColor(Color.BLUE);
		bluePaint.setAntiAlias(true);
		bluePaint.setStyle(Style.FILL);

		setFocusable(true);
		requestFocus();
	}

	protected int getDisplayWidthFromPercentage(double i) {
		return (int) (displayWidth * (i / 100));
	}

	protected int getDisplayHeightFromPercentage(double i) {
		return (int) (displayHeight * (i / 100));
	}

	public void setModel(GameModel model) {
		this.model = model;
		model.addChangeListener(this);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		displayWidth = w;
		displayHeight = h;
		model.setDisplaySize(w, h);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		initView();
	}

	public void propertyChange(PropertyChangeEvent event) {
		postInvalidate();
	}
}