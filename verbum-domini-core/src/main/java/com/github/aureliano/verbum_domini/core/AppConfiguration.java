package com.github.aureliano.verbum_domini.core;

import java.util.Properties;
import java.util.Set;

import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;
import com.github.aureliano.verbum_domini.core.helper.PropertyHelper;

public class AppConfiguration {

	private static final String APP_CONFIGURATION_RESOURCE_NAME = "app-configuration.properties";
	private static AppConfiguration instance;
	
	private PersistenceManager persistenceManager;
	private Properties properties;
	
	private Environments environment;
	private int maxElementsByQuery;
	
	private AppConfiguration() {
		this.properties = PropertyHelper.loadProperties(APP_CONFIGURATION_RESOURCE_NAME);
		this.environment = this.getDefaultEnvironment();
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
	
	public PersistenceManager getPersistenceManager() {
		if (this.persistenceManager == null) {
			this.persistenceManager = this.createPersistenceManager();
		}
		
		return this.persistenceManager;
	}
	
	public void setEnvironment(Environments env) {
		this.environment = env;
	}
	
	public Environments getEnvironment() {
		return this.environment;
	}
	
	public Integer maxElementsByQuery() {
		this.maxElementsByQuery = Integer.parseInt(this.getProperty("database.dao.max_elements_by_query"));
		if (Environments.TEST.equals(AppConfiguration.instance().getEnvironment())) {
			this.maxElementsByQuery = 25;
		}
		
		return this.maxElementsByQuery;
	}
	
	private PersistenceManager createPersistenceManager() {
		Class<?> clazz = this.findPersistenceManager("com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl");
		if (clazz == null) {
			throw new VerbumDominiException("Could not find any implementation of persistence manager.");
		}
		
		try {
			return (PersistenceManager) clazz.newInstance();
		} catch (Exception ex) {
			throw new VerbumDominiException(ex);
		}
	}
	
	private Class<?> findPersistenceManager(String className) {
		try {
			return Class.forName(className);
		} catch (Exception ex) {
			return null;
		}
	}
	
	private Environments getDefaultEnvironment() {
		if (System.getenv("APP_ENVIRONMENT") != null) {
			return Environments.valueOf(System.getenv("APP_ENVIRONMENT"));
		} else if (System.getProperty("APP_ENVIRONMENT") != null) {
			return Environments.valueOf(System.getProperty("APP_ENVIRONMENT"));
		} else {
			return Environments.DEVELOPMENT;
		}
	}
}