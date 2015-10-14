package com.github.aureliano.verbum_domini.helper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UrlHelperTest {

	@Test
	public void testJoin() {
		String actual = UrlHelper.join("bibles", 1);
		String expected = "/bibles/1";
		
		assertEquals(expected, actual);
		
		actual = UrlHelper.join("bibles", 1, "books", 34, "chapters", 5, "verses", 7);
		expected = "/bibles/1/books/34/chapters/5/verses/7";
		
		assertEquals(expected, actual);
	}
}