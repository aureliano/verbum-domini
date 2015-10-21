package com.github.aureliano.verbum_domini.web.converter;

import java.util.Locale;
import java.util.TimeZone;

import javax.faces.convert.DateTimeConverter;

public class DefaultDateTimeConverter extends DateTimeConverter {

	public DefaultDateTimeConverter() {
		super.setTimeZone(TimeZone.getDefault());
		super.setLocale(Locale.getDefault());
		super.setPattern("dd/MM/yyyy HH:mm:ss");
		super.setType("both");
	}
}