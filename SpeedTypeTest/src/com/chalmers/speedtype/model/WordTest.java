package com.chalmers.speedtype.model;

import android.test.AndroidTestCase;

public class WordTest extends AndroidTestCase{
	private Word word;
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		word = new Word("test", 4, 3, 2);
	}
	
	public void testPreConditions(){
		assertTrue(word != null);
		assertTrue(word.toString() == "test");
		assertTrue(word.getSize() == 4);
		assertTrue(word.getX() == 3);
		assertTrue(word.getY() == 2);
	}
	
	public void testGetLastChar(){
		assertTrue('t' == word.getLastChar());
	}
	
	public void testGetLength(){
		assertTrue(word.length() == 4);
	}
	
	public void testSubSequence(){
		CharSequence cs = "st";
		assertEquals(cs, word.subSequence(2, 4));
	}
	
	public void testSubString(){
		assertEquals("te", word.substring(0, 2));
	}
}
