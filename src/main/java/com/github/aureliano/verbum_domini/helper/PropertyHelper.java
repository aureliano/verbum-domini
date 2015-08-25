package com.github.aureliano.verbum_domini.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.github.aureliano.verbum_domini.exception.VerbumDominiException;

public final class PropertyHelper {

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
}