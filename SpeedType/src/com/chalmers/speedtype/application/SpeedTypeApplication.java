package com.chalmers.speedtype.application;

import com.chalmers.speedtype.util.DictionarySQLiteOpenHelper;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

public class SpeedTypeApplication extends Application {
	
	private SQLiteDatabase database;
	
	@Override
	public void onCreate() {
		super.onCreate();
		DictionarySQLiteOpenHelper helper = new DictionarySQLiteOpenHelper(this);
        database = helper.getWritableDatabase();
	}
	
	@Override
	public void onTerminate() {
		database.close();
		super.onTerminate();
	}
	
	public SQLiteDatabase getDatabase() {
		return database;
	}
}
