package com.github.aureliano.verbum_domini.resource.impl;

import com.github.aureliano.verbum_domini.resource.Chapters;
import com.github.aureliano.verbum_domini.service.ChaptersService;

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
}