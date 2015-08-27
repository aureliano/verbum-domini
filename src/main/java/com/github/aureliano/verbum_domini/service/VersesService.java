package com.github.aureliano.verbum_domini.service;

import java.util.ArrayList;
import java.util.List;

import com.github.aureliano.verbum_domini.bean.ChapterBean;
import com.github.aureliano.verbum_domini.bean.VerseBean;
import com.github.aureliano.verbum_domini.dao.ChapterDao;
import com.github.aureliano.verbum_domini.dao.Pagination;
import com.github.aureliano.verbum_domini.dao.VerseDao;
import com.github.aureliano.verbum_domini.model.Verse;
import com.github.aureliano.verbum_domini.model.Verses;
import com.github.aureliano.verbum_domini.web.ServiceParams;

public final class VersesService {

	private VersesService() {
		super();
	}
	
	public static Verses fetchVersesByChapter(String id, Long start, Long pages) {
		if (!id.matches("\\d+")) {
			return null;
		}
		
		VerseBean filter = createFilter(id);
		if (filter.getChapter() == null) {
			return null;
		}
		
		Pagination<VerseBean> beans = new VerseDao()
			.list(filter, new ServiceParams().withStart(start).withPages(pages));
		
		List<Verse> verses = new ArrayList<Verse>();
		
		for (VerseBean bean : beans.getElements()) {
			verses.add(bean.toResource());
		}
		
		return new Verses().withVerses(verses).withSize(beans.getSize());
	}
	
	private static VerseBean createFilter(String id) {
		ChapterBean chapter = new ChapterDao().get(Integer.parseInt(id));
		VerseBean verse = new VerseBean();
		verse.setChapter(chapter);
		
		return verse;
	}
}