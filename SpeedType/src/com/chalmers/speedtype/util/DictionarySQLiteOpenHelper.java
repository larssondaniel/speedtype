package com.chalmers.speedtype.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.chalmers.speedtype.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DictionarySQLiteOpenHelper extends SQLiteOpenHelper {

	public static final int VERSION = 5;
	public static final String DB_NAME = "dictionary_db.sqlite";
	public static final String DICTIONARY_TABLE = "dictionary";
	public static final String ID = "id";
	public static final String WORD = "word";

	ContentValues values;
	InputStream input;

	public DictionarySQLiteOpenHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		input = context.getResources().openRawResource(R.raw.words);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		dropAndCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		dropAndCreate(db);
	}

	private void dropAndCreate(SQLiteDatabase db) {
		db.execSQL("drop table if exists " + DICTIONARY_TABLE + ";");
		createTables(db);
		fillTable(db);
	}

	private void fillTable(SQLiteDatabase db) {
		values = new ContentValues();

		InputStreamReader inputStreamReader = new InputStreamReader(input);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String line;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				addWord(db, line);
			}
		} catch (IOException e) {
		}
	}

	private void createTables(SQLiteDatabase db) {
		db.execSQL("create table " + DICTIONARY_TABLE + " (" + ID
				+ " integer primary key autoincrement not null," + WORD
				+ " text " + ");");
	}

	public void addWord(SQLiteDatabase db, String word) {
		if (word != null) {
			values.put(DictionarySQLiteOpenHelper.WORD, word);
			db.insertWithOnConflict(
					DictionarySQLiteOpenHelper.DICTIONARY_TABLE, null, values,
					SQLiteDatabase.CONFLICT_IGNORE);
		}
	}
}