package com.github.aureliano.verbum_domini.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexHelper {

	private RegexHelper() {
		super();
	}
	
	public static String scan(String regex, String text) {
		Matcher matcher = Pattern.compile(regex).matcher(text);
		if (!matcher.find()) {
			return null;
		}
		
		return matcher.group();
	}
}