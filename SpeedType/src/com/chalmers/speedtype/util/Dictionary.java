package com.chalmers.speedtype.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Dictionary {

	private static Random random = new Random();

	private static ArrayList<String> dictionary;
	private static SQLiteDatabase database;

	public static void init(SQLiteDatabase database) {
		if (Dictionary.database == null) {
			Dictionary.database = database;
			dictionary = new ArrayList<String>();

			loadWordsToMemory();
		}
	}

	public static String getNextWord() {
		if (dictionary == null || dictionary.size() < 1) {
			// This if-statement is solely for testing purposes.
			return "banana";
		} else {
			int size = dictionary.size();
			int item = random.nextInt(size);
			return dictionary.get(item);
		}
	}

	private static void loadWordsToMemory() {
		Cursor cursor = database.query(
				DictionarySQLiteOpenHelper.DICTIONARY_TABLE,
				new String[] { DictionarySQLiteOpenHelper.WORD }, null, null,
				null, null, null);

		cursor.moveToFirst();
		String word;
		if (!cursor.isAfterLast()) {
			do {
				word = cursor.getString(0);
				dictionary.add(word);
			} while (cursor.moveToNext());
		}
		cursor.close();
	}

	/*
	 * Returns a randomized Word from the input.
	 */
	public static String scrabble(CharSequence input){
    	String s = input.toString();
        s = s.toLowerCase();
        List<Character> string = new ArrayList<Character>();
        Random random = new Random();
        int randomNumber;
        String scrabble = "";
        
        while(s.length() > 0){
        	string.add(s.charAt(s.length() - 1));
        	s = s.substring(0, s.length() -1);
        }
        while(string.size() > 0){
            randomNumber = random.nextInt(string.size());
            scrabble = scrabble + (string.remove(randomNumber));
        }
        
        if(scrabble.equals(s))
        	scrabble(input);

        return scrabble;
    }
}