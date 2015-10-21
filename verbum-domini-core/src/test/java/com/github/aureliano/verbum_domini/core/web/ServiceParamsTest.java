package com.github.aureliano.verbum_domini.core.web;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ServiceParamsTest {

	@Test
	public void testWithStart() {
		ServiceParams params = new ServiceParams().withStart(null);
		assertEquals(new Integer(1), params.getStart());
		
		params.withStart(2L);
		assertEquals(new Integer(2), params.getStart());
		
		params.withStart(0);
		assertEquals(new Integer(1), params.getStart());
	}
	
	@Test
	public void testWithPages() {
		ServiceParams params = new ServiceParams().withPages(null);
		assertEquals(new Integer(1), params.getPages());
		
		params.withPages(2L);
		assertEquals(new Integer(2), params.getPages());
		
		params.withPages(0);
		assertEquals(new Integer(1), params.getPages());
		
		params.withPages(3);
		assertEquals(new Integer(2), params.getPages());
	}
}