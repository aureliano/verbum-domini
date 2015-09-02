package com.github.aureliano.verbum_domini.helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.aureliano.verbum_domini.db.ConnectionSingleton;
import com.github.aureliano.verbum_domini.exception.VerbumDominiException;

public final class SchemaHelper {

	private SchemaHelper() {
		super();
	}
	
	public static void createTable(String resourceName) {
		Connection connection = ConnectionSingleton.instance().getConnection();
		try (
			Statement statement = connection.createStatement();
		) {
			statement.executeUpdate(FileHelper.readFile(resourceName));
		} catch (SQLException ex) {
			throw new VerbumDominiException(ex);
		}
	}
}