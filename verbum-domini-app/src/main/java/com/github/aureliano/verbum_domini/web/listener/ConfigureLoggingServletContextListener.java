package com.github.aureliano.verbum_domini.web.listener;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ConfigureLoggingServletContextListener implements ServletContextListener {

	public ConfigureLoggingServletContextListener() {
		super();
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Logger.getLogger("org.mongodb").setLevel(Level.SEVERE);
		Logger.getLogger("com.mongodb").setLevel(Level.SEVERE);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) { }
}