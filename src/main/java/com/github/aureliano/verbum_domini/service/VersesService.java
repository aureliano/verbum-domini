package com.github.aureliano.verbum_domini.service;

import java.util.ArrayList;
import java.util.List;

import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.core.relational.bean.VerseBeanImpl;
import com.github.aureliano.verbum_domini.core.relational.dao.DaoFactory;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;
import com.github.aureliano.verbum_domini.model.Verse;
import com.github.aureliano.verbum_domini.model.Verses;

public final class VersesService {

	private VersesService() {
		super();
	}
	
	public static Verses fetchAll(Long start, Long pages) {
		Pagination<VerseBean> beans = DaoFactory.createDao(VerseBean.class)
				.list(new ServiceParams().withStart(start).withPages(pages));
		List<Verse> verses = new ArrayList<Verse>();
		
		for (VerseBean bean : beans.getElements()) {
			Verse resource = bean.toResource();
			resource.setText(null);
			verses.add(resource);
		}
		
		return new Verses().withVerses(verses).withSize(beans.getSize());
	}
	
	public static Verses fetchVersesByChapter(String id, Long start, Long pages) {
		if (!id.matches("\\d+")) {
			return null;
		}
		
		VerseBean filter = createFilter(id);
		if (filter.getChapter() == null) {
			return null;
		}
		
		Pagination<VerseBean> beans = DaoFactory.createDao(VerseBean.class)
				.list(filter, new ServiceParams().withStart(start).withPages(pages));
		
		List<Verse> verses = new ArrayList<Verse>();
		
		for (VerseBean bean : beans.getElements()) {
			verses.add(bean.toResource());
		}
		
		return new Verses().withVerses(verses).withSize(beans.getSize());
	}
	
	public static Verse fetchVerseById(String id) {
		if (!id.matches("\\d+")) {
			return null;
		}
		
		VerseBean verse = DaoFactory.createDao(VerseBean.class).get(Integer.parseInt(id));
		return (verse == null) ? null : verse.toResource();
	}
	
	private static VerseBean createFilter(String id) {
		ChapterBean chapter = DaoFactory.createDao(ChapterBean.class).get(Integer.parseInt(id));
		VerseBean verse = new VerseBeanImpl();
		verse.setChapter(chapter);
		
		return verse;
	}
}