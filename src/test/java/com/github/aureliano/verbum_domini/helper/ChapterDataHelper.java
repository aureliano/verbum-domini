package com.github.aureliano.verbum_domini.helper;

import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.dao.IDao;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoFactory;

public final class ChapterDataHelper {

	private ChapterDataHelper() {
		super();
	}
	
	public static void createChapters() {
		IDao<BookBean> bookDao = DaoFactory.createDao(BookBean.class);
		IDao<ChapterBean> dao = DaoFactory.createDao(ChapterBean.class);
		
		int chapterId = 0;
		
		for (int i = 0; i < 10; i++) {
			BookBean book = (BookBean) bookDao.load((i + 1));

			for (byte j = 1; j <= 5; j++) {
				ChapterBean chapter = prepareChapter(++chapterId, String.valueOf(j), book);
				dao.save(chapter);
			}
		}
	}
	
	private static ChapterBean prepareChapter(Integer id, String number, BookBean book) {
		ChapterBean chapter = new ChapterBeanImpl();
		
		chapter.setId(id);
		chapter.setNumber(number);
		chapter.setBook(book);
		
		return chapter;
	}
}