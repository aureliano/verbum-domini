package com.github.aureliano.verbum_domini.service;

import java.util.ArrayList;
import java.util.List;

import com.github.aureliano.verbum_domini.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.bean.ChapterBean;
import com.github.aureliano.verbum_domini.dao.AnnotationDao;
import com.github.aureliano.verbum_domini.dao.ChapterDao;
import com.github.aureliano.verbum_domini.dao.Pagination;
import com.github.aureliano.verbum_domini.model.Annotation;
import com.github.aureliano.verbum_domini.model.Annotations;
import com.github.aureliano.verbum_domini.web.ServiceParams;

public final class AnnotationsService {

	private AnnotationsService() {
		super();
	}
	
	public static Annotations fetchAll(Long start, Long pages) {
		Pagination<AnnotationBean> beans = new AnnotationDao().list(new ServiceParams().withStart(start).withPages(pages));
		List<Annotation> annotations = new ArrayList<Annotation>();
		
		for (AnnotationBean bean : beans.getElements()) {
			Annotation resource = bean.toResource();
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
		
		Pagination<AnnotationBean> beans = new AnnotationDao()
			.list(filter, new ServiceParams().withStart(start).withPages(pages));
		
		List<Annotation> annotations = new ArrayList<Annotation>();
		
		for (AnnotationBean bean : beans.getElements()) {
			Annotation resource = bean.toResource();
			resource.setText(null);
			annotations.add(resource);
		}
		
		return new Annotations().withAnnotations(annotations).withSize(beans.getSize());
	}
	
	public static Annotation fetchAnnotationById(String id) {
		if (!id.matches("\\d+")) {
			return null;
		}
		
		AnnotationBean verse = new AnnotationDao().get(Integer.parseInt(id));
		return (verse == null) ? null : verse.toResource();
	}
	
	private static AnnotationBean createFilter(String id) {
		ChapterBean chapter = new ChapterDao().get(Integer.parseInt(id));
		AnnotationBean annotation = new AnnotationBean();
		annotation.setChapter(chapter);
		
		return annotation;
	}
}