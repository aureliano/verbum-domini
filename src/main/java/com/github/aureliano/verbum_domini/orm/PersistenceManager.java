package com.github.aureliano.verbum_domini.orm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.github.aureliano.verbum_domini.bean.BibleBean;
import com.github.aureliano.verbum_domini.helper.PropertyHelper;

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
		Configuration configuration = this.buildConfiguration();
		StandardServiceRegistryBuilder ssrBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		
		return configuration.buildSessionFactory(ssrBuilder.build());
	}
	
	protected Configuration buildConfiguration() {
		return new Configuration()
			.setProperties(PropertyHelper.loadDataSource())
			.addAnnotatedClass(BibleBean.class);
	}
}