package com.github.aureliano.verbum_domini.helper;

import java.io.InputStream;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;

public final class JsonMapperHelper {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	private JsonMapperHelper() {
		super();
	}
	
	public static <T> T map(Class<T> type, InputStream inputStream) {
		try {
			return MAPPER.readValue(inputStream, type);
		} catch (Exception ex) {
			throw new VerbumDominiException(ex);
		}
	}
	
	public static <T> T getMap(Class<T> type, Map<?, ?> map, Object key) {
		return (T) map.get(key);
	}
}