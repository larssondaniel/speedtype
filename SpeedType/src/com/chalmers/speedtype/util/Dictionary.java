package com.chalmers.speedtype.util;

import java.util.ArrayList;
import java.util.Random;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chalmers.speedtype.model.Word;

public class Dictionary {
	
private static Random random = new Random();
	
	private static ArrayList<Word> dictionary;
	private static SQLiteDatabase database;
	
	public static void init(SQLiteDatabase database){
		if(Dictionary.database == null) {
			Dictionary.database = database;
			dictionary = new ArrayList<Word>();
			
			String[] words = {"banana","apple","onion","onion","orange","carrot"};
			
			for(String word: words){
				addWord(new Word(word));
			}
			
			loadWords();
		}
	}
	
	public static Word getNextWord(){
		int size = dictionary.size();
		int item = random.nextInt(size);
		return dictionary.get(item);
	}
	
	public static void addWord(Word word) {
		if(word != null) {
			ContentValues values = new ContentValues();
			values.put(DictionarySQLiteOpenHelper.WORD, word.toString());
			
			database.insertWithOnConflict(DictionarySQLiteOpenHelper.DICTIONARY_TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
		}
	}
	
	private static void loadWords() {
		Cursor cursor = database.query(
				DictionarySQLiteOpenHelper.DICTIONARY_TABLE, 
				new String[] {DictionarySQLiteOpenHelper.WORD}, 
				null, null, null, null, null);
		
		cursor.moveToFirst();
		Word word;
		if (!cursor.isAfterLast()) {
			do {
				word = new Word(cursor.getString(0));
				dictionary.add(word);
			} while (cursor.moveToNext());
		}
		cursor.close();
	}
}