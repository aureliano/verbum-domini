package com.github.aureliano.verbum_domini.orm;

import java.util.Properties;

import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.Test;

public class PersistenceManagerTest {

	@Test
	public void testInstance() {
		Assert.assertNotNull(PersistenceManager.instance());
	}
	
	@Test
	public void testGetUserName() {
		Assert.assertEquals("postgres", PersistenceManager.instance().getUserName());
	}
	
	@Test
	public void testGetUserPassword() {
		Assert.assertEquals("postgres", PersistenceManager.instance().getUserPassword());
	}
	
	@Test
	public void testGetDatabaseUrl() {
		Assert.assertEquals("jdbc:postgresql://localhost:5432/verbum_domini", PersistenceManager.instance().getDatabaseUrl());
	}
	
	@Test
	public void testBuildConfiguration() {
		Configuration configuration = PersistenceManager.instance().buildConfiguration();
		Properties prop = configuration.getProperties();
		PersistenceManager pm = PersistenceManager.instance();
		
		Assert.assertEquals("org.hibernate.dialect.PostgreSQLDialect", prop.getProperty("hibernate.dialect"));
		Assert.assertEquals("org.postgresql.Driver", prop.getProperty("hibernate.connection.driver_class"));
		Assert.assertEquals(pm.getUserName(), prop.getProperty("hibernate.connection.username"));
		Assert.assertEquals(pm.getUserPassword(), prop.getProperty("hibernate.connection.password"));
		Assert.assertEquals(pm.getDatabaseUrl(), prop.getProperty("hibernate.connection.url"));
		Assert.assertEquals("1", prop.getProperty("connection_pool_size"));
		Assert.assertEquals("false", prop.getProperty("show_sql"));
	}
}