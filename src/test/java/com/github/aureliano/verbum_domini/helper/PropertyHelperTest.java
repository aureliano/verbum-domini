package com.github.aureliano.verbum_domini.helper;

import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;

public class PropertyHelperTest {

	@Test
	public void testLoadProperties() {
		Properties properties = PropertyHelper.loadProperties("test.properties");
		Assert.assertEquals("ok", properties.getProperty("property.key"));
		Assert.assertEquals("Raml", properties.getProperty("api.model.name"));
	}
	
	@Test
	public void testLoadDataSource() {
		Properties properties = PropertyHelper.loadDataSource();
		Assert.assertEquals("org.hibernate.dialect.PostgreSQLDialect", properties.getProperty("hibernate.dialect"));
		Assert.assertEquals("org.postgresql.Driver", properties.getProperty("hibernate.connection.driver_class"));
		Assert.assertEquals("postgres", properties.getProperty("hibernate.connection.username"));
		Assert.assertEquals("postgres", properties.getProperty("hibernate.connection.password"));
		Assert.assertEquals("jdbc:postgresql://localhost:5432/verbum_domini", properties.getProperty("hibernate.connection.url"));
		Assert.assertEquals("1", properties.getProperty("connection_pool_size"));
		Assert.assertEquals("false", properties.getProperty("show_sql"));
	}
}