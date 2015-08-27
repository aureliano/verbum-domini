package com.github.aureliano.verbum_domini.resource.impl;

import com.github.aureliano.verbum_domini.resource.Verses;
import com.github.aureliano.verbum_domini.service.VersesService;

public class VersesResourceImpl implements Verses {

	public VersesResourceImpl() {
		super();
	}

	@Override
	public GetVersesResponse getVerses(Long start, Long pages) throws Exception {
		return GetVersesResponse.withJsonOK(VersesService.fetchAll(start, pages));
	}

	@Override
	public GetVersesByVerseIdResponse getVersesByVerseId(String verseId) throws Exception {
		return GetVersesByVerseIdResponse.withJsonOK(VersesService.fetchVerseById(verseId));
	}
}