package com.chalmers.speedtype.application;

import com.chalmers.speedtype.util.BackgroundSoundService;
import com.chalmers.speedtype.util.Dictionary;
import com.chalmers.speedtype.util.DictionarySQLiteOpenHelper;

import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

public class SpeedTypeApplication extends Application {

	private SQLiteDatabase database;
	private Intent backgroundSoundServiceIntent;

	@Override
	public void onCreate() {
		super.onCreate();

		DictionarySQLiteOpenHelper helper = new DictionarySQLiteOpenHelper(this);
		database = helper.getWritableDatabase();
		Dictionary.init(database);

		backgroundSoundServiceIntent = new Intent(this,
				BackgroundSoundService.class);
		//startService(backgroundSoundServiceIntent);
	}

	@Override
	public void onTerminate() {
		database.close();
		// stopService(new Intent(this, BackgroundSoundService.class));
		// stopService(backgroundSoundServiceIntent);
		super.onTerminate();
	}

	public Intent getbackgroundSoundServiceIntent() {
		return backgroundSoundServiceIntent;
	}
}