package com.chalmers.speedtype;

import java.util.Stack;

public class Dictionary {
	
	private Stack<Word> dictionary;
	
	public Dictionary(){
		dictionary = new Stack<Word>();
		String[] words = {"banana","apple","onion","orange","carrot"};
		for(String word: words )
			dictionary.add(new Word(word));
	}
	public String getNextWord(){
		return dictionary.empty() ? null : dictionary.pop().toString();
	}
}
