package com.github.aureliano.verbum_domini.resource.impl;

import com.github.aureliano.verbum_domini.resource.Bibles;
import com.github.aureliano.verbum_domini.service.BiblesService;
import com.github.aureliano.verbum_domini.service.BooksService;

public class BiblesResourceImpl implements Bibles {

	public BiblesResourceImpl() {
		super();
	}

	@Override
	public GetBiblesResponse getBibles(Long start, Long pages) throws Exception {
		return GetBiblesResponse.withJsonOK(BiblesService.fetchAll(start, pages));
	}

	@Override
	public GetBiblesByLanguageResponse getBiblesByLanguage(String language) throws Exception {
		return GetBiblesByLanguageResponse.withJsonOK(BiblesService.fetchByLanguage(language));
	}

	@Override
	public GetBiblesByLanguageBooksResponse getBiblesByLanguageBooks(String language, Long start, Long pages) throws Exception {
		return GetBiblesByLanguageBooksResponse.withJsonOK(BooksService.fetchBooksByBible(language, start, pages));
	}

	@Override
	public GetBiblesByLanguageBooksByIdResponse getBiblesByLanguageBooksById(String id, String language) throws Exception {
		return GetBiblesByLanguageBooksByIdResponse.withJsonOK(BooksService.fetchBookById(id));
	}
}