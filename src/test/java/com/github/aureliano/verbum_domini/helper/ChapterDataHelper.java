package com.github.aureliano.verbum_domini.helper;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.github.aureliano.verbum_domini.domain.bean.BookBean;
import com.github.aureliano.verbum_domini.domain.bean.ChapterBean;
import com.github.aureliano.verbum_domini.orm.PersistenceManager;

public final class ChapterDataHelper {

	private ChapterDataHelper() {
		super();
	}
	
	public static void createChapters() {
		Session session = PersistenceManager.instance().openSession();
		Transaction transaction = session.beginTransaction();
		int chapterId = 0;
		
		for (int i = 0; i < 10; i++) {
			BookBean book = (BookBean) session.load(BookBean.class, (i + 1));

			for (byte j = 1; j <= 5; j++) {
				ChapterBean chapter = prepareChapter(++chapterId, String.valueOf(j), book);
				session.saveOrUpdate(chapter);
			}
		}
		
		transaction.commit();
	}
	
	private static ChapterBean prepareChapter(Integer id, String number, BookBean book) {
		ChapterBean chapter = new ChapterBean();
		
		chapter.setId(id);
		chapter.setNumber(number);
		chapter.setBook(book);
		
		return chapter;
	}
}