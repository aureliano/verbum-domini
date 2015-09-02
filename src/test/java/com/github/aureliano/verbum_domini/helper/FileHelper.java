package com.github.aureliano.verbum_domini.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.github.aureliano.verbum_domini.exception.VerbumDominiException;

public final class FileHelper {

	private FileHelper() {
		super();
	}
	
	public static String readFile(String resourceName) {
		StringBuilder builder = new StringBuilder();
		
		try (
			InputStream stream = ClassLoader.getSystemResourceAsStream(resourceName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		) {		
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line).append("\n");
			}
		} catch (IOException ex) {
			throw new VerbumDominiException(ex);
		}
		
		return builder.toString();
	}
}