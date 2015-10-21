package com.github.aureliano.verbum_domini.core.web;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

public class PaginationTest {

	@Test
	public void testGetSize() {
		Pagination<String> pagination = new Pagination<String>();
		pagination.setSize(5);
		
		assertEquals(new Integer(5), pagination.getSize());
		
		List<String> elements = new ArrayList<String>();
		elements.add("abcdef");
		
		pagination.setElements(elements);
		assertEquals(1, elements.size());
		assertEquals(new Integer(5), pagination.getSize());
	}
	
	@Test
	public void testSetSize() {
		Pagination<String> pagination = new Pagination<String>();
		pagination.setSize(5);
		
		assertEquals(new Integer(5), pagination.getSize());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetSizeLessThanZero() {
		Pagination<String> pagination = new Pagination<String>();
		pagination.setSize(-1);
	}
	
	@Test
	public void testIsEmpty() {
		Pagination<String> pagination = new Pagination<String>();
		assertTrue(pagination.isEmpty());
		
		pagination.setSize(null);
		assertTrue(pagination.isEmpty());
		
		pagination.setSize(0);
		assertTrue(pagination.isEmpty());
		
		pagination.setSize(1);
		assertFalse(pagination.isEmpty());
	}
}