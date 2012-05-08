package com.chalmers.speedtype.model;

public class Word implements CharSequence {
	
	private String word;
	
	public Word(String word){
		this.word = word;
	}
	
	public char charAt(int index) {
		return word.charAt(index);
	}
	
	public char getLastChar(){
		return charAt(length()-1);
	}

	public int length() {
		return word.length();
	}

	public CharSequence subSequence(int start, int end) {
		word.subSequence(start, end);
		return null;
	}
	public String substring(int start, int end){
		return word.substring(start, end);
	}

	public String toString(){
		return word;
	}
}
