package com.chalmers.speedtype;

public class Word implements CharSequence {
	private String word;
	public Word(String word){
		this.word = word;
	}
	public char charAt(int index) {
		return word.charAt(index);
	}

	public int length() {
		return word.length();
	}

	public CharSequence subSequence(int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	public String toString(){
		return word;
	}
}
