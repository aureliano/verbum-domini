package com.github.aureliano.verbum_domini.web;

import junit.framework.Assert;

import org.junit.Test;

public class ServiceParamsTest {

	@Test
	public void testWithStart() {
		ServiceParams params = new ServiceParams().withStart(null);
		Assert.assertEquals(new Integer(1), params.getStart());
		
		params.withStart(2L);
		Assert.assertEquals(new Integer(2), params.getStart());
		
		params.withStart(0);
		Assert.assertEquals(new Integer(1), params.getStart());
	}
	
	@Test
	public void testWithPages() {
		ServiceParams params = new ServiceParams().withPages(null);
		Assert.assertEquals(new Integer(1), params.getPages());
		
		params.withPages(2L);
		Assert.assertEquals(new Integer(2), params.getPages());
		
		params.withPages(0);
		Assert.assertEquals(new Integer(1), params.getPages());
		
		params.withPages(3);
		Assert.assertEquals(new Integer(2), params.getPages());
	}
}