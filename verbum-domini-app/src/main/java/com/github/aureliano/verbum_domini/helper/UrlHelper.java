package com.github.aureliano.verbum_domini.helper;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public final class UrlHelper {

	private UrlHelper() {
		super();
	}
	
	public static String join(Object...tokens) {
		return "/" + StringUtils.join(tokens, '/');
	}
	
	public static String buildWebAppUrl(ServletRequest request, String path) {
		String contextPath = ((HttpServletRequest) request).getContextPath();
		if (contextPath.startsWith("/")) {
			contextPath = contextPath.substring(1);
		}
		
		return (StringUtils.isEmpty(contextPath)) ? join(path) : join(contextPath, path);
	}
	
	public static String buildWebAppUrl(String path) {
		return buildWebAppUrl(WebHelper.getRequest(), path);
	}
}