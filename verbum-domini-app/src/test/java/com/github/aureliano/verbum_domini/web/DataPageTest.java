package com.github.aureliano.verbum_domini.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DataPageTest {

	@Test
	public void testDefaultValues() {
		DataPage dp = new DataPage();
		assertEquals(new Integer(10), dp.getPageSize());
		assertEquals(new Integer(1), dp.getPageIndex());
	}
	
	@Test
	public void testCalculatedValues() {
		DataPage dp = new DataPage()
			.withPageSize(10)
			.withTotal(45);
		
		assertEquals(new Integer(5), dp.getPages());
		assertEquals(new Integer(1), dp.getPageIndex());
		assertEquals(dp.getPageRange(), new PageRange(1, 5));
	}
	
	@Test
	public void testHasNextPage() {
		DataPage dp = new DataPage()
			.withPageSize(10)
			.withTotal(45);
		
		assertTrue(dp.isHaveNextPage());
		
		assertEquals(new Integer(2), dp.getNextPage());
		assertTrue(dp.isHaveNextPage());
		
		dp.withPageIndex(2);
		assertEquals(new Integer(3), dp.getNextPage());
		assertTrue(dp.isHaveNextPage());
		
		dp.withPageIndex(3);
		assertEquals(new Integer(4), dp.getNextPage());
		assertTrue(dp.isHaveNextPage());
		
		dp.withPageIndex(4);
		assertEquals(new Integer(5), dp.getNextPage());
		assertTrue(dp.isHaveNextPage());
		
		dp.withPageIndex(5);
		assertEquals(new Integer(5), dp.getNextPage());
		assertFalse(dp.isHaveNextPage());
	}
	
	@Test
	public void testHasPreviousPage() {
		DataPage dp = new DataPage()
			.withPageSize(10)
			.withTotal(30);
		
		dp.withPageIndex(3);
		assertEquals(new Integer(2), dp.getPreviousPage());
		assertTrue(dp.isHavePreviousPage());
		
		dp.withPageIndex(2);
		assertEquals(new Integer(1), dp.getPreviousPage());
		assertTrue(dp.isHavePreviousPage());
		
		dp.withPageIndex(1);
		assertEquals(new Integer(1), dp.getPreviousPage());
		assertFalse(dp.isHavePreviousPage());
	}
	
	@Test
	public void testHasPageIndex() {
		DataPage dp = new DataPage()
			.withPageSize(10)
			.withTotal(30);
		
		assertTrue(dp.isHavePageIndex(1));
		assertTrue(dp.isHavePageIndex(2));
		assertTrue(dp.isHavePageIndex(3));
		assertFalse(dp.isHavePageIndex(4));
	}
	
	@Test
	public void testHasNextPagination() {
		DataPage dp = new DataPage()
			.withPageSize(10)
			.withTotal(300);
		
		assertTrue(dp.isHaveNextPageRange());
		assertEquals(new Integer(11), dp.getNextPageBlock());
		
		dp.withPageIndex(11);
		assertTrue(dp.isHaveNextPageRange());
		assertEquals(new Integer(21), dp.getNextPageBlock());
		
		dp.withPageIndex(29);
		assertFalse(dp.isHaveNextPageRange());
		assertEquals(new Integer(29), dp.getNextPageBlock());
	}
	
	@Test
	public void testHasPreviousPagination() {
		DataPage dp = new DataPage()
			.withPageSize(10)
			.withTotal(300);
		
		dp.withPageIndex(30);
		assertTrue(dp.isHavePreviousPageRange());
		assertEquals(new Integer(20), dp.getPreviousPageBlock());
		
		dp.withPageIndex(20);
		assertTrue(dp.isHavePreviousPageRange());
		assertEquals(new Integer(10), dp.getPreviousPageBlock());
		
		dp.withPageIndex(9);
		assertFalse(dp.isHavePreviousPageRange());
		assertEquals(new Integer(9), dp.getPreviousPageBlock());
	}
}