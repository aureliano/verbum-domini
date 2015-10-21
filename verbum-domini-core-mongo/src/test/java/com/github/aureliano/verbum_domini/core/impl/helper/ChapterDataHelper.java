package com.github.aureliano.verbum_domini.core.impl.helper;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoHelper;
import com.mongodb.client.MongoCollection;

public final class ChapterDataHelper {

	private ChapterDataHelper() {
		super();
	}

	public static void createChapters() {
		AppConfiguration config = AppConfiguration.instance();
		PersistenceManagerImpl persistenceManager = (PersistenceManagerImpl) config.getPersistenceManager();
		MongoCollection<ChapterBeanImpl> beanWriter = persistenceManager.fetchCollection(ChapterBeanImpl.class);
		
		int chapterId = 0;
		
		for (int i = 0; i < 10; i++) {
			BookBean book = DaoHelper.findOne(BookBeanImpl.class, (i + 1));

			for (byte j = 1; j <= 5; j++) {
				ChapterBeanImpl chapter = prepareChapter(++chapterId, String.valueOf(j), book);
				beanWriter.insertOne(chapter);
			}
		}
	}
	
	private static ChapterBeanImpl prepareChapter(Integer id, String number, BookBean book) {
		ChapterBeanImpl chapter = new ChapterBeanImpl();
		
		chapter.setId(id);
		chapter.setNumber(number);
		chapter.setBook(book);
		
		return chapter;
	}
}