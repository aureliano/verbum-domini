package com.github.aureliano.verbum_domini.core.impl;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.Environments;
import com.github.aureliano.verbum_domini.core.PersistenceManager;

public final class PersistenceManagerImpl implements PersistenceManager {

	private SessionFactory sessionFactory;
	private ThreadLocal<Session> sessionContext;
	
	public PersistenceManagerImpl() {
		this.sessionContext = new ThreadLocal<Session>();
	}
	
	public Session openSession() {
		Session session = this.sessionContext.get();
		
		if ((session == null) || (!session.isOpen())) {
			session = this.sessionFactory.openSession();
		} else if (session.isDefaultReadOnly()) {
			session.close();
			session = this.sessionFactory.openSession();
		}
		
		this.sessionContext.set(session);
		return session;
	}
	
	public Session openReadOnlySession() {
		Session session = this.sessionContext.get();
		
		if ((session == null) || (!session.isOpen())) {
			session = this.sessionFactory.openSession();
		} else if (!session.isDefaultReadOnly()) {
			session.close();
			session = this.sessionFactory.openSession();
		}
		
		session.setDefaultReadOnly(true);
		session.setFlushMode(FlushMode.MANUAL);
		this.sessionContext.set(session);
		
		return session;
	}
	
	public void startUp() {
		if (this.sessionFactory == null) {
			this.sessionFactory = this.buildSessionFactory();
		}
	}
	
	public void shutDown() {
		if ((this.sessionFactory != null) && (!this.sessionFactory.isClosed())) {
			this.sessionFactory.close();
		}
	}
	
	private SessionFactory buildSessionFactory() {
		Configuration configuration = new Configuration().configure();
		
		if (Environments.TEST.equals(AppConfiguration.instance().getEnvironment())) {
			configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://127.0.0.1:5432/verbum_domini_test");
		}
		
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		SessionFactory factory = configuration.buildSessionFactory(builder.build());
		
		return factory;
	}
}