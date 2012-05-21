package com.chalmers.speedtype.model;

import com.chalmers.speedtype.util.Dictionary;
import com.chalmers.speedtype.util.DictionarySQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.view.KeyEvent;


public class ScrabbleModelTest extends AndroidTestCase {
	
	private ScrabbleModel model;
	private Context context;
	private DictionarySQLiteOpenHelper dictionary;
	private SQLiteDatabase database;
	
	/**
	 * This is called before each test.
	 */
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		model = new ScrabbleModel(); 
		context = this.getContext(); 
		
		dictionary = new DictionarySQLiteOpenHelper(context); 
		database = dictionary.getReadableDatabase();
		database.setLockingEnabled(false);
		Dictionary.init(database);
		database.close();
	}
	
	
	/**
	 * Tests the constructor, if the object is created properly.
	 * The various objects should have been created with suitable values.
	 */
	public void testPreConditions(){
		assertNotNull(model);
		assertNotNull(model.activeWord);
		assertNotNull(model.nextWord);
		assertTrue(model.activeWord.length() > 0);
		assertTrue(model.nextWord.length() > 0);
		assertNotSame(model.getActiveScrabbledWord().toString(), model.getActiveWord().toString());
		assertFalse(model.isGameOver);
		assertTrue(model.score == 0);
		assertEquals(model.getTimeLeft(), 15000);
	}
	
	/**
	 * Tests onInput(KeyEvent evt). After the proper key event is sent to the model,
	 * the score and currentCharPos should increase.
	 */
	public void testOnInput(){
		KeyEvent key;
		int scoreBefore = model.getScore();
		int charPosBefore = model.getCurrentCharPos();
		for (int i = 1; i < 255; i++){ //Finds the correct letter. 
			key = new KeyEvent(0, i);
			try{
				model.onInput(key);
			} catch (NullPointerException e){
				e.printStackTrace();
			}
		}
		int charPosAfter = model.getCurrentCharPos();
		int scoreAfter = model.getScore();
		assertTrue(charPosAfter > charPosBefore);
		assertTrue(scoreAfter > scoreBefore);
	}
	
	/**
	 * Tests onCorrectWord() which the onInput(KeyEvent evt) calls whenever the word is completed
	 * onCorrectWord() should set getNewWord to true, if completed correctly.
	 */
	public void testOnCorrectWord(){
		KeyEvent key;
		while(!model.isWordComplete()){ //Finds the correct letters until the word is completed.
			for (int i = 1; i < 255; i++){
				key = new KeyEvent(0, i);
				try {
					model.onInput(key);
				} catch (NullPointerException e){
					e.getStackTrace();
				}
			}
		}
		assertTrue(model.getNewWord()); //When word is completed getNewWord should be true.
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
