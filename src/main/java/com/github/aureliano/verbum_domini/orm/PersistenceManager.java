package com.github.aureliano.verbum_domini.orm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.github.aureliano.verbum_domini.AppConfiguration;

public final class PersistenceManager {

	private static PersistenceManager instance;
	
	private SessionFactory sessionFactory;
	
	private PersistenceManager() {
		super();
	}
	
	public Session openSession() {
		return this.sessionFactory.openSession();
	}
	
	public static PersistenceManager instance() {
		if (instance == null) {
			instance = new PersistenceManager();
		}
		
		return instance;
	}
	
	public void startUp() {
		if (this.sessionFactory == null) {
			this.sessionFactory = this.buildSessionFactory();
		}
	}
	
	public void shutdown() {
		if ((this.sessionFactory != null) && (!this.sessionFactory.isClosed())) {
			this.sessionFactory.close();
		}
	}
	
	private SessionFactory buildSessionFactory() {
		Configuration configuration = this.createDefaultConfiguration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		SessionFactory factory = configuration.buildSessionFactory(builder.build());
		
		return factory;
	}
	
	private Configuration createDefaultConfiguration() {
		return this.createDefaultConfiguration(AppConfiguration.instance().getProperty("hibernate.datastore.type"));
	}
	
	protected Configuration createDefaultConfiguration(String datastoreType) {
		boolean relationalDatabase = "sql".equalsIgnoreCase(datastoreType);
		boolean nosqlDatabase = "nosql".equalsIgnoreCase(datastoreType);
		
		if (relationalDatabase) {
			return new Configuration();
		} else if (nosqlDatabase) {
			//return new OgmConfiguration();
			return null;
		} else {
			throw new UnsupportedOperationException("Unsupported datastore type in hibernate.datastore.type configuration. Expected one of [sql, nosql].");
		}
	}
}