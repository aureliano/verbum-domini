package com.github.aureliano.verbum_domini.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.github.aureliano.verbum_domini.exception.VerbumDominiException;

public final class PropertyHelper {

	public static final String DATA_SOURCE_RESOURCE_NAME = "data-source.properties";
	
	private PropertyHelper() {
		super();
	}
	
	public static Properties loadProperties(String resourceName) {
		Properties properties = new Properties();
		
		try(InputStream stream = ClassLoader.getSystemResourceAsStream(resourceName)) {
			properties.load(stream);
		} catch (IOException ex) {
			throw new VerbumDominiException(ex);
		}
		
		return properties;
	}
	
	public static Properties loadDataSource() {
		return loadProperties(DATA_SOURCE_RESOURCE_NAME);
	}
}