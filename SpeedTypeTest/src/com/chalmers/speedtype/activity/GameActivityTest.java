package com.chalmers.speedtype.activity;

import com.chalmers.speedtype.model.TimeAttackModel;
import com.chalmers.speedtype.util.Dictionary;
import com.chalmers.speedtype.util.DictionarySQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;

public class GameActivityTest extends ActivityInstrumentationTestCase2<GameActivity> {
	
	private Context context;
	private DictionarySQLiteOpenHelper dictionary;
	private SQLiteDatabase database;
	private TimeAttackModel model;
	
	public GameActivityTest(){
		super(GameActivity.class);
	}
	
	protected void setUp() throws Exception{
		super.setUp();
		
		model = new TimeAttackModel();
		context = this.getInstrumentation().getContext();
		dictionary = new DictionarySQLiteOpenHelper(context);
		database = dictionary.getReadableDatabase();
		Dictionary.init(database);
		
	}
	
	public void testPreConditions(){
		assertNotNull(model.getActiveWord());
		assertNotNull(model.getNextWord());
		assertTrue(model.getActiveWord().length() > 0);
		assertTrue(model.getNextWord().length() > 0);
		assertFalse(model.isGameOver());
		assertTrue(model.getScore() == 0);
		assertEquals(model.getTimeLeft(), 10000);
	}
	
	public void tearDown(){
		context = null;
	}
}
