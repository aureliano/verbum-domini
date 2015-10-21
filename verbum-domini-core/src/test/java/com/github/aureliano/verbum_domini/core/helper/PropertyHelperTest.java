package com.github.aureliano.verbum_domini.core.helper;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

public class PropertyHelperTest {

	@Test
	public void testLoadProperties() {
		Properties p = PropertyHelper.loadProperties("app-configuration.properties");
		
		assertEquals("25", p.getProperty("database.dao.max_elements_by_query"));
	}
}