package com.github.aureliano.verbum_domini.helper;

import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.core.dao.IDao;
import com.github.aureliano.verbum_domini.core.impl.bean.VerseBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoFactory;

public final class VerseDataHelper {

	private VerseDataHelper() {
		super();
	}
	
	public static void createVerses() {
		IDao<ChapterBean> chapterDao = DaoFactory.createDao(ChapterBean.class);
		IDao<VerseBean> dao = DaoFactory.createDao(VerseBean.class);
		
		int verseId = 0;
		
		for (int i = 0; i < 50; i++) {
			ChapterBean chapter = (ChapterBean) chapterDao.load((i + 1));

			for (byte j = 1; j <= 5; j++) {
				VerseBean verse = prepareVerse(++verseId, String.valueOf(j), chapter);
				dao.save(verse);
			}
		}
	}
	
	private static VerseBean prepareVerse(Integer id, String number, ChapterBean chapter) {
		VerseBean verse = new VerseBeanImpl();
		
		verse.setId(id);
		verse.setNumber(number);
		verse.setText("Something " + number);
		verse.setChapter(chapter);
		
		return verse;
	}
}