package com.chalmers.speedtype.model;

import com.chalmers.speedtype.util.Dictionary;
import com.chalmers.speedtype.util.DictionarySQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;


public class ScrabbleModelTest extends AndroidTestCase {
	
	private ScrabbleModel model;
	private Context context;
	private Context appContext;
	private DictionarySQLiteOpenHelper dictionary;
	private SQLiteDatabase database;
	
	protected void setUp() throws Exception{
		super.setUp();
		model = new ScrabbleModel();
		context = this.getContext(); 
		while (appContext == null){ 
			appContext = context.getApplicationContext();
		}
		System.out.println(appContext);
		dictionary = new DictionarySQLiteOpenHelper(appContext);
		database = dictionary.getReadableDatabase();
		
		Dictionary.init(database);
	}
	
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
	
	public void tearDown(){
		model = null;
		context = null;
		dictionary = null;
		database = null;	
	}
}
