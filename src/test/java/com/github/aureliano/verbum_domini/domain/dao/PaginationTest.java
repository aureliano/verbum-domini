package com.github.aureliano.verbum_domini.domain.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.github.aureliano.verbum_domini.domain.dao.Pagination;

public class PaginationTest {

	@Test
	public void testGetSize() {
		Pagination<String> pagination = new Pagination<String>();
		pagination.setSize(5);
		
		Assert.assertEquals(new Integer(5), pagination.getSize());
		
		List<String> elements = new ArrayList<String>();
		elements.add("abcdef");
		
		pagination.setElements(elements);
		Assert.assertEquals(1, elements.size());
		Assert.assertEquals(new Integer(5), pagination.getSize());
	}
	
	@Test
	public void testSetSize() {
		Pagination<String> pagination = new Pagination<String>();
		pagination.setSize(5);
		
		Assert.assertEquals(new Integer(5), pagination.getSize());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetSizeLessThanZero() {
		Pagination<String> pagination = new Pagination<String>();
		pagination.setSize(-1);
	}
	
	@Test
	public void testIsEmpty() {
		Pagination<String> pagination = new Pagination<String>();
		Assert.assertTrue(pagination.isEmpty());
		
		pagination.setSize(null);
		Assert.assertTrue(pagination.isEmpty());
		
		pagination.setSize(0);
		Assert.assertTrue(pagination.isEmpty());
		
		pagination.setSize(1);
		Assert.assertFalse(pagination.isEmpty());
	}
}