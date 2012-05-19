package com.chalmers.speedtype.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DictionarySQLiteOpenHelper extends SQLiteOpenHelper {
	
	public static final int VERSION = 3;
	public static final String DB_NAME  = "dictionary_db.sqlite";
	public static final String DICTIONARY_TABLE = "dictionary";
	public static final String ID = "id";
	public static final String WORD = "word";

	public DictionarySQLiteOpenHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		dropAndCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	private void dropAndCreate(SQLiteDatabase db) {
		db.execSQL("drop table if exists " + DICTIONARY_TABLE + ";");
		createTables(db);
		fillTable(db);
	}
	
	private void fillTable(SQLiteDatabase db) {
			
	}

	private void createTables(SQLiteDatabase db) {
		db.execSQL(
				"create table " + DICTIONARY_TABLE + " (" + 
				ID + " integer primary key autoincrement not null," + 
				WORD + " text " +
				");"
			);
	}
}
