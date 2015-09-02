package com.github.aureliano.verbum_domini.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.github.aureliano.verbum_domini.AppConfiguration;
import com.github.aureliano.verbum_domini.exception.VerbumDominiException;

public class ConnectionSingleton {

	private static ConnectionSingleton instance;
	
	private Connection connection;
	
	private ConnectionSingleton() {
		this.createConnection();
	}
	
	public static ConnectionSingleton instance() {
		if (instance == null) {
			instance = new ConnectionSingleton();
		}
		
		return instance;
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	
	private void createConnection() {
		AppConfiguration config = AppConfiguration.instance();
		String driver = config.getProperty("jdbc.driver_name");
		if (driver == null) {
			this.throwUnsetPropertyException("jdbc.driver_name");
		}
		
		String url = config.getProperty("jdbc.connection.url");
		if (url == null) {
			this.throwUnsetPropertyException("jdbc.connection.url");
		}
		
		String user = config.getProperty("jdbc.connection.user");
		String password = config.getProperty("jdbc.connection.password");
		
		try {
			Class.forName(driver);
			this.connection = DriverManager.getConnection(url, user, password);
		} catch (Exception ex) {
			
		}
	}
	
	private void throwUnsetPropertyException(String property) {
		throw new VerbumDominiException("Property '" + property + "' not set in app-configuration.properties file.");
	}
}