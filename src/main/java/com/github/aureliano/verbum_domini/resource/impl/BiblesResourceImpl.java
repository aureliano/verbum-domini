package com.github.aureliano.verbum_domini.resource.impl;

import com.github.aureliano.verbum_domini.resource.Bibles;

public class BiblesResourceImpl implements Bibles {

	public BiblesResourceImpl() {
		super();
	}

	@Override
	public GetBiblesResponse getBibles(Long start, Long pages) throws Exception {
		return null;
	}

	@Override
	public GetBiblesByLanguageResponse getBiblesByLanguage(String language) throws Exception {
		return null;
	}
}