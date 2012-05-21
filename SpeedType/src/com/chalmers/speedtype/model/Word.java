package com.chalmers.speedtype.model;

public class Word implements CharSequence {
	private String word;
	private double x;
	private double y;
	private float size;

	public Word(String word, float size, double x, double y) {
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
		return word.subSequence(start, end);
	}
	
	public String substring(int start, int end){
		return word.substring(start, end);
	}
	
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	
	public void setX(double x){
		this.x = x;
	}
	public void setY(double d){
		this.y = d;
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