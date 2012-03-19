package com.chalmers.speedtype;

import java.util.Stack;

public class Dictionary {
	
	private Stack<Word> dictionary;
	
	public Dictionary(){
		dictionary = new Stack<Word>();
		
		dictionary.add(new Word("Banan"));
		dictionary.add(new Word("Apelsin"));
		dictionary.add(new Word("Morot"));
	}
	public String getNextWord(){
		return dictionary.empty() ? null : dictionary.pop().toString();
	}
}
