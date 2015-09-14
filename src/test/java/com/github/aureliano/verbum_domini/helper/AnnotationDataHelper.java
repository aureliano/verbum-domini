package com.github.aureliano.verbum_domini.helper;

import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.dao.IDao;
import com.github.aureliano.verbum_domini.core.impl.bean.AnnotationBeanImpl;
import com.github.aureliano.verbum_domini.core.impl.dao.DaoFactory;

public final class AnnotationDataHelper {

	private AnnotationDataHelper() {
		super();
	}
	
	public static void createAnnotations() {
		IDao<ChapterBean> chapterDao = DaoFactory.createDao(ChapterBean.class);
		IDao<AnnotationBean> dao = DaoFactory.createDao(AnnotationBean.class);
		
		int annotationId = 0;
		
		for (int i = 0; i < 50; i++) {
			ChapterBean chapter = (ChapterBean) chapterDao.load((i + 1));

			for (byte j = 1; j <= 5; j++) {
				AnnotationBean annotation = prepareAnnotation(++annotationId, String.valueOf(j), chapter);
				dao.save(annotation);
			}
		}
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