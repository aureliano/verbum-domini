package com.github.aureliano.verbum_domini.orm;

import java.util.Properties;

import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.Test;

public class PersistenceManagerTest {

	@Test
	public void testInstance() {
		PersistenceManager p1 = PersistenceManager.instance();
		Assert.assertNotNull(p1);
		
		PersistenceManager p2 = PersistenceManager.instance();
		Assert.assertEquals(p1, p2);
		Assert.assertTrue(p1 == p2);
	}
	
	@Test
	public void testBuildConfiguration() {
		Configuration configuration = PersistenceManager.instance().buildConfiguration();
		Properties prop = configuration.getProperties();
		
		Assert.assertEquals("org.hibernate.dialect.PostgreSQLDialect", prop.getProperty("hibernate.dialect"));
		Assert.assertEquals("org.postgresql.Driver", prop.getProperty("hibernate.connection.driver_class"));
		Assert.assertEquals("postgres", prop.getProperty("hibernate.connection.username"));
		Assert.assertEquals("postgres", prop.getProperty("hibernate.connection.password"));
		Assert.assertEquals("jdbc:postgresql://localhost:5432/verbum_domini", prop.getProperty("hibernate.connection.url"));
		Assert.assertEquals("1", prop.getProperty("connection_pool_size"));
		Assert.assertEquals("false", prop.getProperty("show_sql"));
	}
}