//package com.chalmers.speedtype.activity;
//
//import android.app.Application;
//import android.test.ActivityInstrumentationTestCase2;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.chalmers.speedtype.activity.TimeAttackActivity;
//import com.chalmers.speedtype.application.SpeedTypeApplication;
//import com.chalmers.speedtype.R;
//
//public class TimeAttackActivityTest extends
//		ActivityInstrumentationTestCase2<TimeAttackActivity> {
//
//	private TimeAttackActivity tAA;
//	private TextView score;
//	private TextView time;
//	private TextView word;
//	private TextView nextWord;
//	private EditText input;
//	private Application app;
//	
//	public TimeAttackActivityTest() {
//		super(TimeAttackActivity.class);
//	
//	}
//	
//	protected void setUp() throws Exception{
//		super.setUp();
//		
//		this.setActivityInitialTouchMode(false);
//		tAA = (TimeAttackActivity) this.getInstrumentation().getContext();
//		//		tAA = new TimeAttackActivity(); //If I create a new TimeAttackActivity here I can't access the TextView-fields below.
////		this.setActivity(tAA);
//		
////		 tAA = this.getActivity(); //This should work, afaik. Don't know why it doesn't. It has worked in the past.
//								  //Causes NullPointerException
//		try{ 
//			app = (SpeedTypeApplication) tAA.getApplication();
//		} catch (Exception e){
//			System.out.println("ERROR ERROR ERROR");
//		}
//
//		try{
//			score = (TextView) tAA.findViewById(R.id.score);
//			time = (TextView) tAA.findViewById(R.id.time);
//			word = (TextView) tAA.findViewById(R.id.word);
//			nextWord = (TextView) tAA.findViewById(R.id.next_word);
//			input = (EditText) tAA.findViewById(R.id.input_edit_text);
//		} catch (Exception e){
//			System.out.println("Couldn't find TextViews from tAA");
//		} 	
//	}
//	
//	public void testPreConditions(){
//		assertNotNull(score);
//		assertNotNull(time);
//		assertNotNull(word);
//		assertNotNull(nextWord);
//		assertNotNull(input);
//	}
//	
//	public void testGetCurrentWord(){
//		assertEquals(word.getText().toString(), "carrot"); //Has to be remade due to random strings.
//	}
//
//	public void testGetNextWord(){
//		assertEquals(nextWord.getText().toString(), "orange"); //Has to be remade due to random strings.
//	}
//	
//	public void testInput(){
//		// TODO DO!
//		assertEquals(true, true);
//	}
//
//}
