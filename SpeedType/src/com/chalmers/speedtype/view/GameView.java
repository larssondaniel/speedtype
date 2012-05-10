package com.chalmers.speedtype.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.chalmers.speedtype.model.Model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public abstract class GameView extends View implements PropertyChangeListener {

	protected Typeface mensch;

	private Model model;

	protected int displayWidth;
	protected int displayHeight;

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
		
		setFocusable(true);
		requestFocus();
	}

	public void setModel(Model model) {
		this.model = model;
		model.addChangeListener(this);
	}

	@Override
	protected void onDraw(Canvas canvas) {

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		displayWidth = w;
		displayHeight = h;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		initView();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		postInvalidate();
	}
}