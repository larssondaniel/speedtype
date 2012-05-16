package com.chalmers.speedtype.model;

public class Word implements CharSequence {
	private String word;
	private int x;
	private int y;
	private float size;

	public Word(String word, float size, int x, int y) {
		this(word, size);
		this.x = x;
		this.y = y;
	}
	public Word(String word, float size) {
		this(word);
		this.size = size;
	}
	public Word(String word) {
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
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	
	public float getSize() {
		return size;
	}
	public void setSize(float size) {
		this.size = size;
	}
	
	public String toString(){
		return word;
	}
}