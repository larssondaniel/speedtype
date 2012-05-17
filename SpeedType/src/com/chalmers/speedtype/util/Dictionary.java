package com.chalmers.speedtype.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.chalmers.speedtype.model.Word;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Dictionary {
	
private static Random random = new Random();
	
	private static ArrayList<String> dictionary;
	private static SQLiteDatabase database;

	private static InputStream input;
	
	public static void init(SQLiteDatabase database, InputStream input){
		if(Dictionary.database == null) {
			Dictionary.database = database;
			Dictionary.input = input;
			dictionary = new ArrayList<String>();
					
			loadWordsToMemory();
		}
	}
	
	public static String getNextWord(){
		int size = dictionary.size();
		int item = random.nextInt(size);
		return dictionary.get(item);
	}
	
	public static void addWord(String word) {
		if(word != null) {
			ContentValues values = new ContentValues();
			values.put(DictionarySQLiteOpenHelper.WORD, word.toString());
			
			database.insertWithOnConflict(DictionarySQLiteOpenHelper.DICTIONARY_TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
		}
	}
	
	private static void loadWordsToMemory() {
		Cursor cursor = database.query(
				DictionarySQLiteOpenHelper.DICTIONARY_TABLE, 
				new String[] {DictionarySQLiteOpenHelper.WORD}, 
				null, null, null, null, null);
		
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

	public static void fill() {
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(input));
			System.out.println(in);
			while (in.ready()) {
			  addWord(in.readLine());
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Returns a randomized Word from the input.
	 */
    public static Word scrabble(CharSequence input){
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
        return (new Word(scrabble));
    }
}