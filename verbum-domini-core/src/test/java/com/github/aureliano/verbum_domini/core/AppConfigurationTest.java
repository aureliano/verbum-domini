package com.github.aureliano.verbum_domini.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;

public class AppConfigurationTest {
	
	@Test
	public void testInstance() {
		AppConfiguration app1 = AppConfiguration.instance();
		assertNotNull(app1);
		
		AppConfiguration app2 = AppConfiguration.instance();
		assertEquals(app1, app2);
		assertTrue(app1 == app2);
	}

	@Test
	public void testGetKeys() {
		Set<Object> keys = AppConfiguration.instance().getKeys();
		
		assertEquals(1, keys.size());
		assertTrue(keys.contains("database.dao.max_elements_by_query"));
	}
	
	@Test
	public void testGetProperty() {
		AppConfiguration config = AppConfiguration.instance();
		
		assertEquals("25", config.getProperty("database.dao.max_elements_by_query"));
	}
	
	@Test
	public void testGetPersistenceManager() {
		PersistenceManager pm = AppConfiguration.instance().getPersistenceManager();
		assertTrue(pm instanceof PersistenceManagerImpl);
	}
	
	@Test
	public void testAppEnvironmentVariable() {
		AppConfiguration config = AppConfiguration.instance();
		assertEquals(Environments.DEVELOPMENT, config.getEnvironment());
		
		config.setEnvironment(Environments.TEST);
		assertEquals(Environments.TEST, config.getEnvironment());
		
		config.setEnvironment(Environments.PRODUCTION);
		assertEquals(Environments.PRODUCTION, config.getEnvironment());
	}
}