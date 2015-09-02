package com.github.aureliano.verbum_domini.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.github.aureliano.verbum_domini.AppConfiguration;
import com.github.aureliano.verbum_domini.exception.VerbumDominiException;

public class ConnectionSingleton {

	private static ConnectionSingleton instance;
	
	private Connection connection;
	
	private ConnectionSingleton() {
		super();
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
	
	public void startUp() {
		if (!this.isInitialized()) {
			this.createConnection();
		}
	}
	
	public void shutDown() {
		try {
			this.connection.close();
		} catch (SQLException ex) {
			throw new VerbumDominiException(ex);
		}
	}
	
	public boolean isInitialized() {
		return (this.connection != null);
	}
	
	private void createConnection() {
		AppConfiguration config = AppConfiguration.instance();
		String driver = config.getProperty("jdbc.driver_class");
		if (driver == null) {
			this.throwUnsetPropertyException("jdbc.driver_class");
		}
		
		String url = config.getProperty("jdbc.connection.url");
		if (url == null) {
			this.throwUnsetPropertyException("jdbc.connection.url");
		}
		
		String user = config.getProperty("jdbc.connection.user");
		String password = config.getProperty("jdbc.connection.password");
		
		try {
			System.out.println("Loading driver class " + driver);
			Class.forName(driver);
			
			System.out.println("Establishing connection to " + url);
			System.out.println("User: " + user);
			System.out.println("Password: " + ((password == null) ? "not provided" : password.replaceAll(".", "*")));
			this.connection = DriverManager.getConnection(url, user, password);
		} catch (Exception ex) {
			throw new VerbumDominiException(ex);
		}
	}
	
	private void throwUnsetPropertyException(String property) {
		throw new VerbumDominiException("Property '" + property + "' not set in app-configuration.properties file.");
	}
}