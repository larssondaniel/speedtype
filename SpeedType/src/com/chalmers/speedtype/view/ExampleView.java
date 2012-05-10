//package com.chalmers.speedtype.view;
//
//import com.chalmers.speedtype.model.ExampleModel;
//import com.chalmers.speedtype.model.Model;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Paint.Style;
//import android.util.AttributeSet;
//
//public class ExampleView extends GameView {
//
//	private ExampleModel model;
//
//	public ExampleView(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//	}
//
//	public ExampleView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//	}
//
//	public ExampleView(Context context) {
//		super(context);
//	}
//
//	public void setModel(Model model) {
//		super.setModel(model);
//		this.model = (ExampleModel) model;
//	}
//
//	protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
//
//		String word = model.getWord();
//
//		Paint p = new Paint();
//		p.setColor(Color.WHITE);
//		p.setAntiAlias(true);
//		p.setStyle(Style.FILL);
//		p.setTextSize(80);
//		p.setTypeface(mensch);
//
//		float wordWidth = p.measureText(word);
//		float center = displayWidth / 2 - wordWidth / 2;
//
//		canvas.drawText(word, center, model.getY(), p);
//	}
//}
