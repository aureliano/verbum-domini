package com.github.aureliano.verbum_domini.helper;

import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;

public class PropertyHelperTest {

	@Test
	public void testLoadProperties() {
		Properties p = PropertyHelper.loadProperties("app-configuration.properties");
		
		Assert.assertEquals("sql", p.getProperty("database.application.type"));
		Assert.assertEquals("hsqldb", p.getProperty("database.application.name"));
	}
}