package com.github.aureliano.verbum_domini.core.impl.helper;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.AnnotationBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;

public final class AnnotationDataHelper {

	private AnnotationDataHelper() {
		super();
	}
	
	public static void createAnnotations() {
		Session session = ((PersistenceManagerImpl) AppConfiguration.instance().getPersistenceManager()).openSession();
		Transaction transaction = session.beginTransaction();
		int annotationId = 0;
		
		for (int i = 0; i < 50; i++) {
			ChapterBean chapter = (ChapterBean) session.load(ChapterBeanImpl.class, (i + 1));

			for (byte j = 1; j <= 5; j++) {
				AnnotationBean annotation = prepareAnnotation(++annotationId, String.valueOf(j), chapter);
				session.saveOrUpdate(annotation);
			}
		}
		
		transaction.commit();
	}
	
	private static AnnotationBean prepareAnnotation(Integer id, String number, ChapterBean chapter) {
		AnnotationBean verse = new AnnotationBeanImpl();
		
		verse.setId(id);
		verse.setNumber(number);
		verse.setText("Something " + number);
		verse.setChapter(chapter);
		
		return verse;
	}
}