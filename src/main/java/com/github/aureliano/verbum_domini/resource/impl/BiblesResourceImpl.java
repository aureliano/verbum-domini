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
	public GetBiblesByBibleIdResponse getBiblesByBibleId(String bibleId) throws Exception {
		return GetBiblesByBibleIdResponse.withJsonOK(BiblesService.fetchById(bibleId));
	}

	@Override
	public GetBiblesByBibleIdBooksResponse getBiblesByBibleIdBooks(String id, Long start, Long pages) throws Exception {
		return GetBiblesByBibleIdBooksResponse.withJsonOK(BooksService.fetchBooksByBible(id, start, pages));
	}

	@Override
	public GetBiblesByBibleIdBooksByBookIdResponse getBiblesByBibleIdBooksByBookId(String id, String language) throws Exception {
		return GetBiblesByBibleIdBooksByBookIdResponse.withJsonOK(BooksService.fetchBookById(id));
	}
}