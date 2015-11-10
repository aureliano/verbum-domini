package com.github.aureliano.verbum_domini.core.data;

import java.util.HashMap;
import java.util.Map;

import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;

public class DataBookAbbreviations {

	private Map<String, String[]> map;
	private Map<String, String> inverseMap;
	private boolean locked;
	
	public DataBookAbbreviations() {
		this.map = new HashMap<String, String[]>();
		this.inverseMap = new HashMap<String, String>();
		this.locked = false;
	}
	
	public DataBookAbbreviations addAbbreviation(String book, String...abbreviations) {
		if (this.locked) {
			throw new VerbumDominiException("You cannot not add abbreviations to frozen collection.");
		}
		
		this.map.put(book.toLowerCase(), abbreviations);
		for (String abbreviation : abbreviations) {
			this.inverseMap.put(abbreviation.toLowerCase(), book.toLowerCase());
		}
		
		return this;
	}
	
	public Map<String, String[]> abbreviations() {
		return this.map;
	}
	
	public String[] abbreviations(String book) {
		return this.map.get(book);
	}
	
	public String findBookByAbbreviation(String abbreviation) {
		return this.inverseMap.get(abbreviation);
	}
	
	public DataBookAbbreviations done() {
		this.locked = true;
		return this;
	}
}