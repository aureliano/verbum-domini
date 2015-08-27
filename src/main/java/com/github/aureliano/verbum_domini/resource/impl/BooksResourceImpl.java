package com.github.aureliano.verbum_domini.resource.impl;

import com.github.aureliano.verbum_domini.resource.Books;
import com.github.aureliano.verbum_domini.service.BooksService;

public class BooksResourceImpl implements Books {

	public BooksResourceImpl() {
		super();
	}

	@Override
	public GetBooksResponse getBooks(Long start, Long pages) throws Exception {
		return GetBooksResponse.withJsonOK(BooksService.fetchAll(start, pages));
	}

	@Override
	public GetBooksByBookIdResponse getBooksByBookId(String bookId) throws Exception {
		return GetBooksByBookIdResponse.withJsonOK(BooksService.fetchBookById(bookId));
	}
}