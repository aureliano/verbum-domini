package com.github.aureliano.verbum_domini.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.github.aureliano.verbum_domini.db.ConnectionSingleton;

public class DatabaseConnectionServletContextListener implements ServletContextListener {

	private ConnectionSingleton connectionSingleton;
	
	public DatabaseConnectionServletContextListener() {
		this.connectionSingleton = ConnectionSingleton.instance();
	}
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		this.connectionSingleton.startUp();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		this.connectionSingleton.shutDown();
	}
}