package com.github.aureliano.verbum_domini.query;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class QueryBuilderTest {

	@Test
	public void testBuild() {
		String sql = new QueryBuilder().select(new String[0]).from("my_table").build();
		Assert.assertEquals("SELECT * FROM my_table", sql);
		
		sql = new QueryBuilder().select("col1", "another").from("test").build();
		Assert.assertEquals("SELECT col1,another FROM test", sql);
		
		sql = new QueryBuilder().select("col1", "another").from("test")
				.where(null, "col1", 4).build();
		Assert.assertEquals("SELECT col1,another FROM test WHERE col1 = ?", sql);
		
		sql = new QueryBuilder().select("col1", "another").from("test")
				.where(null, "col1", 4).where(FilterOperator.OR, "col2", "test")
				.where(FilterOperator.AND, "another", 3.2).build();
		Assert.assertEquals("SELECT col1,another FROM test WHERE col1 = ? OR col2 = ? AND another = ?", sql);
	}
	
	@Test
	public void testIsValidSql() {
		Assert.assertTrue(new QueryBuilder().select(new String[0]).from("my_table").isValidSql());
		Assert.assertFalse(new QueryBuilder().select(new String[0]).isValidSql());
		Assert.assertFalse(new QueryBuilder().from("table").isValidSql());
	}
	
	@Test
	public void testGetFilters() {
		QueryBuilder builder = new QueryBuilder().select(new String[0]).from("my_table");
		Assert.assertTrue(builder.getFilters().isEmpty());
		
		List<Object> filters = new QueryBuilder().select("col1", "another").from("test")
				.where(null, "col1", 4).where(FilterOperator.OR, "col2", "test")
				.where(FilterOperator.AND, "another", 3.2).getFilters();
		
		Assert.assertEquals(4, filters.get(0));
		Assert.assertEquals("test", filters.get(1));
		Assert.assertEquals(3.2, filters.get(2));
	}
}