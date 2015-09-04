package com.github.aureliano.verbum_domini.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.PersistenceManager;

public class DatabaseConnectionServletContextListener implements ServletContextListener {

	private PersistenceManager persistenceManager;
	
	public DatabaseConnectionServletContextListener() {
		persistenceManager = AppConfiguration.instance().getPersistenceManager();
	}
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		this.persistenceManager.startUp();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		this.persistenceManager.shutDown();
	}
}