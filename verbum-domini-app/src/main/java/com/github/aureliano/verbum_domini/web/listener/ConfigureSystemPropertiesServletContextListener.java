package com.github.aureliano.verbum_domini.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class ConfigureSystemPropertiesServletContextListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(ConfigureSystemPropertiesServletContextListener.class);
	
	public ConfigureSystemPropertiesServletContextListener() {
		super();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("Configure system properties.");
		this.setProperty("org.apache.el.parser.COERCE_TO_ZERO", "false");
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) { }
	
	private void setProperty(String key, String value) {
		logger.info(" => " + key + " = " + value);
		System.setProperty(key, value);
	}
}