package com.chalmers.speedtype.activity;

import android.app.Application;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

import com.chalmers.speedtype.activity.TimeAttackActivity;
import com.chalmers.speedtype.application.SpeedTypeApplication;
import com.chalmers.speedtype.R;

public class TimeAttackActivityTest extends
		ActivityInstrumentationTestCase2<TimeAttackActivity> {

	private TimeAttackActivity tAA;
	private TextView score;
	private TextView time;
	private TextView word;
	private TextView nextWord;
	private EditText input;
	private Application app;
	
	public TimeAttackActivityTest() {
		super(TimeAttackActivity.class);
	
	}
	
	protected void setUp() throws Exception{
		super.setUp();
		
		this.setActivityInitialTouchMode(false);
		
		tAA = new TimeAttackActivity();
		this.setActivity(tAA);
//		tAA = this.getActivity();

		try{ 
			app = (SpeedTypeApplication) tAA.getApplication();
		} catch (Exception e){
			System.out.println("Taskig typkonvertering");
		}

		try{
			score = (TextView) tAA.findViewById(R.id.score);
			time = (TextView) tAA.findViewById(R.id.time);
			word = (TextView) tAA.findViewById(R.id.word);
			nextWord = (TextView) tAA.findViewById(R.id.next_word);
			input = (EditText) tAA.findViewById(R.id.input_edit_text);
		} catch (Exception e){
			System.out.println("Couldn't find TextViews from tAA");
		} 	
	}
	
	public void testPreConditions(){
		assertNotNull(score);
		assertNotNull(time);
		assertNotNull(word);
		assertNotNull(nextWord);
		assertNotNull(input);
	}
	
	public void testGetCurrentWord(){
//		System.out.println(Dictionary.getNextWord().toString());
		assertEquals(word.getText().toString(), "carrot"); 
	}

	public void testGetNextWord(){
		assertEquals(nextWord.getText().toString(), "orange"); 
	}
	
	public void testInput(){
		input.setText("carrot");
//		tAA.runOnUiThread(input.setText("c"));
		assertEquals(word.getText().toString(), input.getText().toString());
	}
	
	// don't even know if I should test this one..
//	public void testOnTextChanged(){
//		
//		System.out.println("Score: " + score.getText().toString());
//		tAM.onTextChanged("c");
//		System.out.println("Score After inc: " + score.getText().toString());
//		assertEquals(word.getText().toString(), tAM.getCurrentWord().toString());
//		assertTrue(0 == Integer.parseInt(score.getText().toString()));
//	}


}
