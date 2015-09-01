package com.github.aureliano.verbum_domini.helper;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.github.aureliano.verbum_domini.domain.bean.ChapterBean;
import com.github.aureliano.verbum_domini.domain.bean.VerseBean;
import com.github.aureliano.verbum_domini.orm.PersistenceManager;

public final class VerseDataHelper {

	private VerseDataHelper() {
		super();
	}
	
	public static void createChapters() {
		Session session = PersistenceManager.instance().openSession();
		Transaction transaction = session.beginTransaction();
		int verseId = 0;
		
		for (int i = 0; i < 50; i++) {
			ChapterBean chapter = (ChapterBean) session.load(ChapterBean.class, (i + 1));

			for (byte j = 1; j <= 5; j++) {
				VerseBean verse = prepareVerse(++verseId, String.valueOf(j), chapter);
				session.saveOrUpdate(verse);
			}
		}
		
		transaction.commit();
	}
	
	private static VerseBean prepareVerse(Integer id, String number, ChapterBean chapter) {
		VerseBean verse = new VerseBean();
		
		verse.setId(id);
		verse.setNumber(number);
		verse.setText("Something " + number);
		verse.setChapter(chapter);
		
		return verse;
	}
}