package com.github.aureliano.verbum_domini.resource.impl;

import com.github.aureliano.verbum_domini.resource.Chapters;
import com.github.aureliano.verbum_domini.service.ChaptersService;
import com.github.aureliano.verbum_domini.service.VersesService;

public class ChaptersResourceImpl implements Chapters {

	public ChaptersResourceImpl() {
		super();
	}

	@Override
	public GetChaptersResponse getChapters(Long start, Long pages) throws Exception {
		return GetChaptersResponse.withJsonOK(ChaptersService.fetchAll(start, pages));
	}

	@Override
	public GetChaptersByChapterIdResponse getChaptersByChapterId(String chapterId) throws Exception {
		return GetChaptersByChapterIdResponse.withJsonOK(ChaptersService.fetchChapterById(chapterId));
	}

	@Override
	public GetChaptersByChapterIdVersesResponse getChaptersByChapterIdVerses(String chapterId, Long start, Long pages) throws Exception {
		return GetChaptersByChapterIdVersesResponse.withJsonOK(VersesService.fetchVersesByChapter(chapterId, start, pages));
	}

	@Override
	public GetChaptersByChapterIdVersesByVerseIdResponse getChaptersByChapterIdVersesByVerseId(String verseId, String chapterId)
			throws Exception {
		return GetChaptersByChapterIdVersesByVerseIdResponse.withJsonOK(VersesService.fetchVerseById(verseId));
	}
}