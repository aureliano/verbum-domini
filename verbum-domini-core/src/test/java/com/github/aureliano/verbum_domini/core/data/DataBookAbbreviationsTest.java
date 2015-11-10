package com.github.aureliano.verbum_domini.core.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;

public class DataBookAbbreviationsTest {

	@Test(expected = VerbumDominiException.class)
	public void testDone() {
		DataBookAbbreviations data = new DataBookAbbreviations();
		data.done();
		
		data.addAbbreviation("test", "test1");
	}
	
	@Test
	public void testAddAbbreviation() {
		DataBookAbbreviations data = new DataBookAbbreviations();
		DataBookAbbreviations ob = data.addAbbreviation("genesis", "gen", "ge");
		
		assertTrue(data == ob);
		assertEquals(1, data.abbreviations().size());
	}
	
	@Test
	public void testAbbreviations() {
		DataBookAbbreviations data = new DataBookAbbreviations()
			.addAbbreviation("genesis", "gen", "ge")
			.addAbbreviation("exodus", "exod", "ex");
		
		assertEquals(2, data.abbreviations().size());
	}
	
	@Test
	public void testAbbreviationsByBook() {
		DataBookAbbreviations data = new DataBookAbbreviations()
			.addAbbreviation("genesis", "gen", "ge", "gn")
			.addAbbreviation("exodus", "exod", "ex");
		
		assertEquals(3, data.abbreviations("genesis").length);
		assertEquals(2, data.abbreviations("exodus").length);
		assertNull(data.abbreviations(""));
		assertNull(data.abbreviations("test"));
	}
	
	@Test
	public void testFindBookByAbbreviation() {
		DataBookAbbreviations data = new DataBookAbbreviations()
			.addAbbreviation("genesis", "gen", "ge", "gn")
			.addAbbreviation("exodus", "exod", "ex");
		
		assertEquals("genesis", data.findBookByAbbreviation("gen"));
		assertEquals("genesis", data.findBookByAbbreviation("ge"));
		assertEquals("genesis", data.findBookByAbbreviation("gn"));
		assertEquals("exodus", data.findBookByAbbreviation("exod"));
		assertEquals("exodus", data.findBookByAbbreviation("ex"));
		assertNull(data.findBookByAbbreviation(""));
		assertNull(data.findBookByAbbreviation("test"));
	}
}