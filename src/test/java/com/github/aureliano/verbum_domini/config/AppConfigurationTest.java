package com.github.aureliano.verbum_domini.config;

import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

public class AppConfigurationTest {

	@Test
	public void testGetKeys() {
		Set<Object> keys = AppConfiguration.instance().getKeys();
		
		Assert.assertTrue(keys.contains("hibernate.datastore.type"));
		Assert.assertTrue(keys.contains("hibernate.datastore.application"));
	}
	
	@Test
	public void testGetProperty() {
		AppConfiguration config = AppConfiguration.instance();
		
		Assert.assertEquals("sql", config.getProperty("hibernate.datastore.type"));
		Assert.assertEquals("PostgreSQL", config.getProperty("hibernate.datastore.application"));
	}
}