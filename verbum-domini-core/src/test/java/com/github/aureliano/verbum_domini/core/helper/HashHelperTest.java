package com.github.aureliano.verbum_domini.core.helper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HashHelperTest {

	@Test
	public void testGenerateHash() {
		String text = "123456";
		String expected = "e10adc3949ba59abbe56e057f20f883e";
		String hash = HashHelper.generateHash("md5", text);
		
		assertEquals(expected, hash);
	}
	
	@Test
	public void testGenerateSaltNumber() {
		assertEquals(5, HashHelper.generateSaltNumber().length());
		assertEquals(5, HashHelper.generateSaltNumber().length());
		assertEquals(5, HashHelper.generateSaltNumber().length());
		assertEquals(5, HashHelper.generateSaltNumber().length());
		assertEquals(5, HashHelper.generateSaltNumber().length());
	}
}