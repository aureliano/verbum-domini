package com.github.aureliano.verbum_domini.service;

import java.util.ArrayList;
import java.util.List;

import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.core.impl.bean.VerseBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoFactory;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;
import com.github.aureliano.verbum_domini.model.Verse;
import com.github.aureliano.verbum_domini.model.Verses;
import com.github.aureliano.verbum_domini.parser.ResourceToEntityParser;

public final class VersesService {

	private VersesService() {
		super();
	}
	
	public static Verses fetchAll(Long start, Long pages) {
		Pagination<VerseBean> beans = DaoFactory.createDao(VerseBean.class)
				.list(new ServiceParams().withStart(start).withPages(pages));
		List<Verse> verses = new ArrayList<Verse>();
		
		for (VerseBean bean : beans.getElements()) {
			Verse resource = ResourceToEntityParser.parse(Verse.class, bean);
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
			verses.add(ResourceToEntityParser.parse(Verse.class, bean));
		}
		
		return new Verses().withVerses(verses).withSize(beans.getSize());
	}
	
	public static Verse fetchVerseById(String id) {
		if (!id.matches("\\d+")) {
			return null;
		}
		
		VerseBean verse = DaoFactory.createDao(VerseBean.class).get(Integer.parseInt(id));
		return (verse == null) ? null : ResourceToEntityParser.parse(Verse.class, verse);
	}
	
	private static VerseBean createFilter(String id) {
		ChapterBean chapter = DaoFactory.createDao(ChapterBean.class).get(Integer.parseInt(id));
		VerseBean verse = new VerseBeanImpl();
		verse.setChapter(chapter);
		
		return verse;
	}
}