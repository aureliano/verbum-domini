package com.github.aureliano.verbum_domini.core.impl.helper;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.VerseBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoHelper;
import com.mongodb.client.MongoCollection;

public final class VerseDataHelper {

	private VerseDataHelper() {
		super();
	}

	public static void createVerses() {
		AppConfiguration config = AppConfiguration.instance();
		PersistenceManagerImpl persistenceManager = (PersistenceManagerImpl) config.getPersistenceManager();
		MongoCollection<VerseBeanImpl> beanWriter = persistenceManager.fetchCollection(VerseBeanImpl.class);
		
		int verseId = 0;
		
		for (int i = 0; i < 50; i++) {
			ChapterBean chapter = DaoHelper.findOne(ChapterBeanImpl.class, (i + 1));

			for (byte j = 1; j <= 5; j++) {
				VerseBeanImpl verse = prepareVerse(++verseId, String.valueOf(j), chapter);
				beanWriter.insertOne(verse);
			}
		}
	}
	
	private static VerseBeanImpl prepareVerse(Integer id, String number, ChapterBean chapter) {
		VerseBeanImpl verse = new VerseBeanImpl();
		
		verse.setId(id);
		verse.setNumber(number);
		verse.setText("Something " + number);
		verse.setChapter(chapter);
		
		return verse;
	}
}