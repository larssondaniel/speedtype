package com.chalmers.speedtype.model;

import android.view.KeyEvent;

import com.chalmers.speedtype.R;

public class ExampleModel extends Model{
	private static final int LAYOUT_ID = R.layout.example_layout;
	private static final int VIEW_ID = R.id.example_view;
	
	private int y = 200;
	
	public ExampleModel() {
		
	}

	@Override
	public int getLayoutId() {
		return LAYOUT_ID;
	}

	@Override
	public int getViewId() {
		return VIEW_ID;
	}
	public void gravity() {
		y++;
		listener.propertyChange(null);
	}
	
	public int getY(){
		return y;
	}

	@Override
	public void onInput(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}
}
