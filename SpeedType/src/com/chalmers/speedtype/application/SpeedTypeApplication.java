package com.chalmers.speedtype.application;

import java.io.InputStream;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.util.Dictionary;
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
        
        InputStream input = getResources().openRawResource(R.raw.words);
        Dictionary.init(database, input);
	}
	
	@Override
	public void onTerminate() {
		database.close();
		super.onTerminate();
	}
}
