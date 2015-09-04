package com.github.aureliano.verbum_domini.service;

import java.util.ArrayList;
import java.util.List;

import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.bean.ChapterBean;
import com.github.aureliano.verbum_domini.core.relational.bean.AnnotationBeanImpl;
import com.github.aureliano.verbum_domini.core.relational.dao.DaoFactory;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;
import com.github.aureliano.verbum_domini.model.Annotation;
import com.github.aureliano.verbum_domini.model.Annotations;
import com.github.aureliano.verbum_domini.parser.ResourceToEntityParser;

public final class AnnotationsService {

	private AnnotationsService() {
		super();
	}
	
	public static Annotations fetchAll(Long start, Long pages) {
		Pagination<AnnotationBean> beans = DaoFactory.createDao(AnnotationBean.class)
				.list(new ServiceParams().withStart(start).withPages(pages));
		List<Annotation> annotations = new ArrayList<Annotation>();
		
		for (AnnotationBean bean : beans.getElements()) {
			Annotation resource = ResourceToEntityParser.parse(Annotation.class, bean);
			resource.setText(null);
			annotations.add(resource);
		}
		
		return new Annotations().withAnnotations(annotations).withSize(beans.getSize());
	}
	
	public static Annotations fetchAnnotationsByChapter(String id, Long start, Long pages) {
		if (!id.matches("\\d+")) {
			return null;
		}
		
		AnnotationBean filter = createFilter(id);
		if (filter.getChapter() == null) {
			return null;
		}
		
		Pagination<AnnotationBean> beans = DaoFactory.createDao(AnnotationBean.class)
				.list(filter, new ServiceParams().withStart(start).withPages(pages));
		
		List<Annotation> annotations = new ArrayList<Annotation>();
		
		for (AnnotationBean bean : beans.getElements()) {
			Annotation resource = ResourceToEntityParser.parse(Annotation.class, bean);
			resource.setText(null);
			annotations.add(resource);
		}
		
		return new Annotations().withAnnotations(annotations).withSize(beans.getSize());
	}
	
	public static Annotation fetchAnnotationById(String id) {
		if (!id.matches("\\d+")) {
			return null;
		}
		
		AnnotationBean annotation = DaoFactory.createDao(AnnotationBean.class).get(Integer.parseInt(id));
		return (annotation == null) ? null : ResourceToEntityParser.parse(Annotation.class, annotation);
	}
	
	private static AnnotationBean createFilter(String id) {
		ChapterBean chapter = DaoFactory.createDao(ChapterBean.class).get(Integer.parseInt(id));
		AnnotationBean annotation = new AnnotationBeanImpl();
		annotation.setChapter(chapter);
		
		return annotation;
	}
}