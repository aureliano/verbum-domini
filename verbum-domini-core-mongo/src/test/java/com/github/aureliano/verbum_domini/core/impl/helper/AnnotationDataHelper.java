package com.github.aureliano.verbum_domini.core.impl.helper;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.impl.PersistenceManagerImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.AnnotationBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.bean.ChapterBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoHelper;
import com.mongodb.client.MongoCollection;

public final class AnnotationDataHelper {

	private AnnotationDataHelper() {
		super();
	}

	public static void createAnnotations() {
		AppConfiguration config = AppConfiguration.instance();
		PersistenceManagerImpl persistenceManager = (PersistenceManagerImpl) config.getPersistenceManager();
		MongoCollection<AnnotationBeanImpl> beanWriter = persistenceManager.fetchCollection(AnnotationBeanImpl.class);
		
		int annotationId = 0;
		
		for (int i = 0; i < 50; i++) {
			ChapterBean chapter = DaoHelper.findOne(ChapterBeanImpl.class, (i + 1));

			for (byte j = 1; j <= 5; j++) {
				AnnotationBeanImpl annotation = prepareAnnotation(++annotationId, String.valueOf(j), chapter);
				beanWriter.insertOne(annotation);
			}
		}
	}
	
	private static AnnotationBeanImpl prepareAnnotation(Integer id, String number, ChapterBean chapter) {
		AnnotationBeanImpl annotation = new AnnotationBeanImpl();
		
		annotation.setId(id);
		annotation.setNumber(number);
		annotation.setText("Something " + number);
		annotation.setChapter(chapter);
		
		return annotation;
	}
}