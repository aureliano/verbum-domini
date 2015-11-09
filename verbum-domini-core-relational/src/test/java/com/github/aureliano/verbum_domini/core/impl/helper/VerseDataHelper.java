package com.github.aureliano.verbum_domini.core.impl.helper;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.AnnotationBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.VerseBeanImpl;

public final class VerseDataHelper {

	private VerseDataHelper() {
		super();
	}
	
	public static void createVerses() {
		Session session = ((PersistenceManagerImpl) AppConfiguration.instance().getPersistenceManager()).openSession();
		Transaction transaction = session.beginTransaction();
		int verseId = 0;
		
		for (int i = 0; i < 50; i++) {
			ChapterBean chapter = (ChapterBean) session.load(ChapterBeanImpl.class, (i + 1));

			for (byte j = 1; j <= 5; j++) {
				VerseBean verse = prepareVerse(++verseId, String.valueOf(j), chapter);
				session.saveOrUpdate(verse);
			}
		}
		
		transaction.commit();
	}
	
	private static VerseBean prepareVerse(Integer id, String number, ChapterBean chapter) {
		VerseBeanImpl verse = new VerseBeanImpl();
		
		verse.setId(id);
		verse.setNumber(number);
		verse.setText("Something " + number);
		verse.setChapter(chapter);
		verse.addAnnotation(prepareAnnotation(id, number));
		
		return verse;
	}
	
	private static AnnotationBeanImpl prepareAnnotation(Integer id, String number) {
		AnnotationBeanImpl annotation = new AnnotationBeanImpl();
		
		annotation.setId(id);
		annotation.setNumber(number);
		annotation.setText("Something " + number);
		
		return annotation;
	}
}