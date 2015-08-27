package com.github.aureliano.verbum_domini.resource.impl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.github.aureliano.verbum_domini.resource.Bibles;
import com.github.aureliano.verbum_domini.service.BiblesService;
import com.github.aureliano.verbum_domini.service.BooksService;
import com.github.aureliano.verbum_domini.service.ChaptersService;
import com.github.aureliano.verbum_domini.service.VersesService;

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
	public GetBiblesByBibleIdBooksResponse getBiblesByBibleIdBooks(String bibleId, Long start, Long pages) throws Exception {
		return GetBiblesByBibleIdBooksResponse.withJsonOK(BooksService.fetchBooksByBible(bibleId, start, pages));
	}

	@Override
	public GetBiblesByBibleIdBooksByBookIdResponse getBiblesByBibleIdBooksByBookId(String bookId, String language) throws Exception {
		return GetBiblesByBibleIdBooksByBookIdResponse.withJsonOK(BooksService.fetchBookById(bookId));
	}

	@Override
	public GetBiblesByBibleIdBooksByBookIdChaptersResponse getBiblesByBibleIdBooksByBookIdChapters(String bookId, String bibleId,
			Long start, Long pages) throws Exception {
		return GetBiblesByBibleIdBooksByBookIdChaptersResponse.withJsonOK(ChaptersService.fetchChaptersByBook(bookId, start, pages));
	}

	@Override
	public GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdResponse getBiblesByBibleIdBooksByBookIdChaptersByChapterId(String chapterId,
			String bookId, String bibleId) throws Exception {
		return GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdResponse.withJsonOK(ChaptersService.fetchChapterById(chapterId));
	}

	@Override
	public GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdVersesResponse getBiblesByBibleIdBooksByBookIdChaptersByChapterIdVerses(
			String chapterId, String bookId, String bibleId, Long start, Long pages) throws Exception {
		return GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdVersesResponse
				.withJsonOK(VersesService.fetchVersesByChapter(chapterId, start, pages));
	}
}