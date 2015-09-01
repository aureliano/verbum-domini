package com.github.aureliano.verbum_domini.service;

import java.util.ArrayList;
import java.util.List;

import com.github.aureliano.verbum_domini.domain.bean.BookBean;
import com.github.aureliano.verbum_domini.domain.bean.ChapterBean;
import com.github.aureliano.verbum_domini.domain.dao.BookDao;
import com.github.aureliano.verbum_domini.domain.dao.ChapterDao;
import com.github.aureliano.verbum_domini.domain.dao.Pagination;
import com.github.aureliano.verbum_domini.model.Chapter;
import com.github.aureliano.verbum_domini.model.Chapters;
import com.github.aureliano.verbum_domini.web.ServiceParams;

public final class ChaptersService {

	private ChaptersService() {
		super();
	}
	
	public static Chapters fetchAll(Long start, Long pages) {
		Pagination<ChapterBean> beans = new ChapterDao().list(new ServiceParams().withStart(start).withPages(pages));
		List<Chapter> chapters = new ArrayList<Chapter>();
		
		for (ChapterBean bean : beans.getElements()) {
			chapters.add(bean.toResource());
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
		
		Pagination<ChapterBean> beans = new ChapterDao()
			.list(filter, new ServiceParams().withStart(start).withPages(pages));
		
		List<Chapter> chapters = new ArrayList<Chapter>();
		
		for (ChapterBean bean : beans.getElements()) {
			chapters.add(bean.toResource());
		}
		
		return new Chapters().withChapters(chapters).withSize(beans.getSize());
	}
	
	public static Chapter fetchChapterById(String id) {
		if (!id.matches("\\d+")) {
			return null;
		}
		
		ChapterBean chapter = new ChapterDao().get(Integer.parseInt(id));
		return (chapter == null) ? null : chapter.toResource();
	}
	
	private static ChapterBean createFilter(String id) {
		BookBean book = new BookDao().get(Integer.parseInt(id));
		ChapterBean chapter = new ChapterBean();
		chapter.setBook(book);
		
		return chapter;
	}
}