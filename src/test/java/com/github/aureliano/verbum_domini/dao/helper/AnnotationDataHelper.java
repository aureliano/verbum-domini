package com.github.aureliano.verbum_domini.dao.helper;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.github.aureliano.verbum_domini.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.bean.ChapterBean;
import com.github.aureliano.verbum_domini.orm.PersistenceManager;

public final class AnnotationDataHelper {

	private AnnotationDataHelper() {
		super();
	}
	
	public static void createAnnotations() {
		Session session = PersistenceManager.instance().openSession();
		Transaction transaction = session.beginTransaction();
		int annotationId = 0;
		
		for (int i = 0; i < 50; i++) {
			ChapterBean chapter = (ChapterBean) session.load(ChapterBean.class, (i + 1));

			for (byte j = 1; j <= 5; j++) {
				AnnotationBean annotation = prepareAnnotation(++annotationId, String.valueOf(j), chapter);
				session.saveOrUpdate(annotation);
			}
		}
		
		transaction.commit();
	}
	
	private static AnnotationBean prepareAnnotation(Integer id, String number, ChapterBean chapter) {
		AnnotationBean verse = new AnnotationBean();
		
		verse.setId(id);
		verse.setNumber(number);
		verse.setText("Something " + number);
		verse.setChapter(chapter);
		
		return verse;
	}
}