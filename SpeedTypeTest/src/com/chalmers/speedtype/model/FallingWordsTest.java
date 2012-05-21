package com.chalmers.speedtype.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.chalmers.speedtype.util.Dictionary;
import com.chalmers.speedtype.util.DictionarySQLiteOpenHelper;

public class FallingWordsTest extends AndroidTestCase {
	private FallingWordsModel model;
	private Context context;
	private DictionarySQLiteOpenHelper dictionary;
	private SQLiteDatabase database;
	
	/**
	 * This is called before each test.
	 */
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		model = new FallingWordsModel();
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
		assertNotNull(model);
		assertNotNull(model.activeWord);
		assertNotNull(model.nextWord);
		assertTrue(model.activeWord.length() > 0);
		assertTrue(model.nextWord.length() > 0);
		assertFalse(model.isGameOver);
		assertTrue(model.score == 0);
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
