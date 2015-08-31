package com.github.aureliano.verbum_domini.config;

import java.util.Properties;
import java.util.Set;

import com.github.aureliano.verbum_domini.helper.PropertyHelper;

public class AppConfiguration {

	private static final String APP_CONFIGURATION_RESOURCE_NAME = "app-configuration.properties";
	private static AppConfiguration instance;
	
	private Properties properties;
	
	private AppConfiguration() {
		this.properties = PropertyHelper.loadProperties(APP_CONFIGURATION_RESOURCE_NAME);
	}
	
	public static AppConfiguration instance(){
		if (instance == null) {
			instance = new AppConfiguration();
		}
		
		return instance;
	}
	
	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}
	
	public Set<Object> getKeys() {
		return this.properties.keySet();
	}
}