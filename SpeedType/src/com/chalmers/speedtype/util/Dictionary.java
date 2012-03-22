package com.chalmers.speedtype.util;

import java.util.Stack;

import com.chalmers.speedtype.model.Word;

public class Dictionary {
	
	private Stack<Word> dictionary;
	
	public Dictionary(){
		dictionary = new Stack<Word>();
		String[] words = {"banana","apple","onion","orange","carrot"};
		for(String word: words )
			dictionary.add(new Word(word));
	}
	public Word getNextWord(){
		return dictionary.empty() ? null : dictionary.pop();
	}
}
