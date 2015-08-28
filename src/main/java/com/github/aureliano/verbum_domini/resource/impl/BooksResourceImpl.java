package com.github.aureliano.verbum_domini.resource.impl;

import com.github.aureliano.verbum_domini.resource.Books;
import com.github.aureliano.verbum_domini.service.AnnotationsService;
import com.github.aureliano.verbum_domini.service.BooksService;
import com.github.aureliano.verbum_domini.service.ChaptersService;
import com.github.aureliano.verbum_domini.service.VersesService;

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

	@Override
	public GetBooksByBookIdChaptersResponse getBooksByBookIdChapters(String bookId, Long start, Long pages) throws Exception {
		return GetBooksByBookIdChaptersResponse.withJsonOK(ChaptersService.fetchChaptersByBook(bookId, start, pages));
	}

	@Override
	public GetBooksByBookIdChaptersByChapterIdResponse getBooksByBookIdChaptersByChapterId(String chapterId, String bookId)
			throws Exception {
		return GetBooksByBookIdChaptersByChapterIdResponse.withJsonOK(ChaptersService.fetchChapterById(chapterId));
	}

	@Override
	public GetBooksByBookIdChaptersByChapterIdVersesResponse getBooksByBookIdChaptersByChapterIdVerses(String chapterId, String bookId,
			Long start, Long pages) throws Exception {
		return GetBooksByBookIdChaptersByChapterIdVersesResponse.withJsonOK(VersesService.fetchVersesByChapter(chapterId, start, pages));
	}

	@Override
	public GetBooksByBookIdChaptersByChapterIdVersesByVerseIdResponse getBooksByBookIdChaptersByChapterIdVersesByVerseId(String verseId,
			String chapterId, String bookId) throws Exception {
		return GetBooksByBookIdChaptersByChapterIdVersesByVerseIdResponse.withJsonOK(VersesService.fetchVerseById(verseId));
	}

	@Override
	public GetBooksByBookIdChaptersByChapterIdAnnotationsResponse getBooksByBookIdChaptersByChapterIdAnnotations(String chapterId,
			String bookId, Long start, Long pages) throws Exception {
		return GetBooksByBookIdChaptersByChapterIdAnnotationsResponse
				.withJsonOK(AnnotationsService.fetchAnnotationsByChapter(chapterId, start, pages));
	}

	@Override
	public GetBooksByBookIdChaptersByChapterIdAnnotationsByAnnotationIdResponse getBooksByBookIdChaptersByChapterIdAnnotationsByAnnotationId(
			String annotationId, String chapterId, String bookId) throws Exception {
		return GetBooksByBookIdChaptersByChapterIdAnnotationsByAnnotationIdResponse
				.withJsonOK(AnnotationsService.fetchAnnotationById(annotationId));
	}
}