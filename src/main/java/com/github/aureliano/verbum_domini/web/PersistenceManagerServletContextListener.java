package com.github.aureliano.verbum_domini.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.github.aureliano.verbum_domini.orm.PersistenceManager;

public class PersistenceManagerServletContextListener implements ServletContextListener {

	private PersistenceManager persistenceManager;
	
	public PersistenceManagerServletContextListener() {
		this.persistenceManager = PersistenceManager.instance();
	}
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		this.persistenceManager.startUp();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		this.persistenceManager.shutdown();
	}
}