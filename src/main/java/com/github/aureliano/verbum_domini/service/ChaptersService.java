package com.github.aureliano.verbum_domini.service;

import java.util.ArrayList;
import java.util.List;

import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.relational.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.relational.dao.DaoFactory;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;
import com.github.aureliano.verbum_domini.model.Chapter;
import com.github.aureliano.verbum_domini.model.Chapters;
import com.github.aureliano.verbum_domini.parser.ResourceToEntityParser;

public final class ChaptersService {

	private ChaptersService() {
		super();
	}
	
	public static Chapters fetchAll(Long start, Long pages) {
		Pagination<ChapterBean> beans = DaoFactory.createDao(ChapterBean.class)
				.list(new ServiceParams().withStart(start).withPages(pages));
		List<Chapter> chapters = new ArrayList<Chapter>();
		
		for (ChapterBean bean : beans.getElements()) {
			chapters.add(ResourceToEntityParser.parse(Chapter.class, bean));
		}
		
		return new Chapters().withChapters(chapters).withSize(beans.getSize());
	}
	
	public static Chapters fetchChaptersByBook(String id, Long start, Long pages) {
		if (!id.matches("\\d+")) {
			return null;
		}
		
		ChapterBean filter = createFilter(id);
		if (filter.getBook() == null) {
			return null;
		}
		
		Pagination<ChapterBean> beans = DaoFactory.createDao(ChapterBean.class)
				.list(filter, new ServiceParams().withStart(start).withPages(pages));
		
		List<Chapter> chapters = new ArrayList<Chapter>();
		
		for (ChapterBean bean : beans.getElements()) {
			chapters.add(ResourceToEntityParser.parse(Chapter.class, bean));
		}
		
		return new Chapters().withChapters(chapters).withSize(beans.getSize());
	}
	
	public static Chapter fetchChapterById(String id) {
		if (!id.matches("\\d+")) {
			return null;
		}
		
		ChapterBean chapter = DaoFactory.createDao(ChapterBean.class).get(Integer.parseInt(id));
		return (chapter == null) ? null : ResourceToEntityParser.parse(Chapter.class, chapter);
	}
	
	private static ChapterBean createFilter(String id) {
		BookBean book = DaoFactory.createDao(BookBean.class).get(Integer.parseInt(id));
		ChapterBean chapter = new ChapterBeanImpl();
		chapter.setBook(book);
		
		return chapter;
	}
}