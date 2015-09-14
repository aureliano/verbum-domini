package com.github.aureliano.verbum_domini.helper;

import com.github.aureliano.verbum_domini.core.AppConfiguration;

public final class AppHelper {

	private static final String API_REST_URL;
	
	static {
		StringBuilder builder = new StringBuilder("http://");
		AppConfiguration conf = AppConfiguration.instance();
		
		API_REST_URL = builder
			.append(conf.getProperty("jetty.host"))
			.append(":")
			.append(conf.getProperty("jetty.port"))
			.append("/")
			.append(conf.getProperty("jetty.context"))
			.append("/apirest")
			.toString();
	}
	
	private AppHelper() {
		super();
	}
	
	public static String getApiRestUrl() {
		return API_REST_URL;
	}
	
	public static String getServiceUrl(String path) {
		return getApiRestUrl() + "/" + path.replaceFirst("^/", "");
	}
}