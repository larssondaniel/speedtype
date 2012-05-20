package com.chalmers.speedtype.model;

import com.chalmers.speedtype.util.Dictionary;
import com.chalmers.speedtype.util.DictionarySQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

public class TimeAttackModelTest extends AndroidTestCase{
	
	private TimeAttackModel model;
	private Context context;
	private DictionarySQLiteOpenHelper dictionary;
	private SQLiteDatabase database;
	
	protected void setUp() throws Exception{
		super.setUp();
		
		model = new TimeAttackModel();
		context = this.getContext();
		dictionary = new DictionarySQLiteOpenHelper(context);
		database = dictionary.getReadableDatabase();
		
		Dictionary.init(database);
	}
	
	public void testPreConditions(){
		assertNotNull(model.activeWord);
		assertNotNull(model.nextWord);
		assertTrue(model.activeWord.length() > 0);
		assertTrue(model.nextWord.length() > 0);
		assertFalse(model.isGameOver);
		assertTrue(model.score == 0);
		assertEquals(model.getTimeLeft(), 10000);
	}


	
	public void tearDown(){
		model = null;
		context = null;
		dictionary = null;
		database = null;
	}
}
