package com.chalmers.speedtype.model;

import com.chalmers.speedtype.util.Dictionary;
import com.chalmers.speedtype.util.DictionarySQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.view.KeyEvent;

public class TimeAttackModelTest extends AndroidTestCase{
	
	private TimeAttackModel model;
	private Context context;
	private DictionarySQLiteOpenHelper dictionary;
	private SQLiteDatabase database;
	
	/**
	 * This is called before each test.
	 */
	@Override	
	protected void setUp() throws Exception{
		super.setUp();
		
		model = new TimeAttackModel();
		context = this.getContext();
		dictionary = new DictionarySQLiteOpenHelper(context);
		database = dictionary.getReadableDatabase();
		
		Dictionary.init(database);
		database.close();
	}
	
	/**
	 * Tests the constructor, if the object is created properly.
	 * The various objects should have been created with suitable values.
	 */
	public void testPreConditions(){
		assertNotNull(model.activeWord);
		assertNotNull(model.nextWord);
		assertTrue(model.activeWord.length() > 0);
		assertTrue(model.nextWord.length() > 0);
		assertFalse(model.isGameOver);
		assertTrue(model.score == 0);
		assertEquals(model.getTimeLeft(), 10000);
	}

	/**
	 * Tests onInput(KeyEvent evt). After the proper key event is sent to the model,
	 * the score and currentCharPos should increase.
	 */
	public void testOnInput(){
		KeyEvent key;
		int scoreBefore = model.getScore();
		int charPosBefore = model.getCurrentCharPos();
		for (int i = 1; i < 255; i++){
			key = new KeyEvent(0, i);
			model.onInput(key);
		}
		int charPosAfter = model.getCurrentCharPos();
		int scoreAfter = model.getScore();
		assertTrue(charPosAfter > charPosBefore);
		assertTrue(scoreAfter > scoreBefore);
	}
	
	/**
	 * Tests onCorrectWord() which the onInput(KeyEvent evt) calls whenever the word is completed.
	 * onCorrectWord() should increase timeLeft when finished correctly.
	 */
	public void testOnCorrectWord(){
		KeyEvent key;
		int timeLeftBefore = model.getTimeLeft();
		int count = 0;
		int after = 0;
		int length = model.activeWord.length();
		while(!model.isWordComplete()){ //Finds the correct letters until the word is completed.
			for (int i = 1; i < 255; i++){
				key = new KeyEvent(0, i);
				try {
					count++;
					model.onInput(key);
				} catch (NullPointerException e){
					e.getStackTrace();
				}
			}
			System.out.println("iswordcomplete: " + model.isWordComplete());
		}
		System.out.println(count + "word.length " + length);
		int timeLeftAfter = model.getTimeLeft();
		try{
			model.onCorrectWord();
		} catch (NullPointerException e){
			e.printStackTrace();
		}
		System.out.println("before: " + timeLeftBefore + " after: " + timeLeftAfter + " efter fast inne: " + after);
		assertTrue(timeLeftBefore != timeLeftAfter);
	}
		
	/**
	 * This is called after each test,
	 * to insure that each test is ran individually. 
	 */
	@Override
	public void tearDown() throws Exception{
		super.tearDown();
		model = null;
		context = null;
		dictionary = null;
		database = null;
	}
}
