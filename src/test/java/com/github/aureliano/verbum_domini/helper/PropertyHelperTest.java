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
}