package com.github.aureliano.verbum_domini.helper;

import org.apache.commons.lang.StringUtils;

public final class UrlHelper {

	private UrlHelper() {
		super();
	}
	
	public static String join(Object...tokens) {
		return "/" + StringUtils.join(tokens, '/');
	}
}