
package com.chalmers.speedtype.model;

=======
package com.chalmers.speedtype.tests;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;
import com.chalmers.speedtype.model.TimeAttackModel;

public class TimeAttackModelTest {
	;

	private TimeAttackModel tam = new TimeAttackModel(null);
	private Stack<String> words = new Stack<String>();
	private final String[] STATICWORDS = { "banana", "apple", "onion",
			"orange", "carrot" }; // Current static Strings in Dictionary

	@Test
	public void testGetCurrentWord() {
		// Will be rewritten once the database has been implemented.
		for (String s : STATICWORDS) {
			words.add(s);
		}
		CharSequence cs = tam.getCurrentWord();
		String s = cs.toString();
		assertTrue(s == words.pop());

	}

	@Test
	public void testGetNextWord() {
		/*
		 * Will be rewritten when Dictionary class is fully operational. Waiting
		 * for the databse to be implemented.
		 */
		for (String s : STATICWORDS) {
			words.add(s);
		}
		CharSequence cs = tam.getNextWord();
		String s = cs.toString();
		words.pop();
		assertTrue(s == words.pop());
	}

	@Test
	public void testOnTextChanged() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testIsFinished() {
		// No real reason to test a instance variable.
	}

	@Test
	public void testTimeAttackModel() {
		fail("Not yet implemented"); // TODO
	}
}
