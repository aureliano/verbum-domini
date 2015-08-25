package com.github.aureliano.verbum_domini.orm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

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
		Configuration configuration = new Configuration();
		StandardServiceRegistryBuilder ssrBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		
		return configuration.buildSessionFactory(ssrBuilder.build());
	}
}