package com.github.aureliano.verbum_domini;

import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import com.github.aureliano.verbum_domini.AppConfiguration;

public class AppConfigurationTest {

	@Test
	public void testGetKeys() {
		Set<Object> keys = AppConfiguration.instance().getKeys();
		
		Assert.assertTrue(keys.contains("database.application.type"));
		Assert.assertTrue(keys.contains("database.application.name"));
	}
	
	@Test
	public void testGetProperty() {
		AppConfiguration config = AppConfiguration.instance();
		
		Assert.assertEquals("sql", config.getProperty("database.application.type"));
		Assert.assertEquals("PostgreSQL", config.getProperty("database.application.name"));
	}
}