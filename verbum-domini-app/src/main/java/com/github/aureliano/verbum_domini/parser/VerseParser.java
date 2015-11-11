package com.github.aureliano.verbum_domini.parser;

import java.util.ArrayList;
import java.util.List;

import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.core.bean.VerseBean;
import com.github.aureliano.verbum_domini.model.Annotation;
import com.github.aureliano.verbum_domini.model.Verse;

public class VerseParser implements IResourceParser<VerseBean> {

	public VerseParser() {
		super();
	}
	
	@Override
	public Verse toResource(VerseBean bean) {
		Integer chapterId = (bean.getChapter() != null) ? bean.getChapter().getId() : null;
		
		List<Annotation> annotations = null;
		if ((bean.getAnnotations() != null) && (!bean.getAnnotations().isEmpty())) {
			annotations = new ArrayList<Annotation>();
			AnnotationParser parser = new AnnotationParser();
			
			for (AnnotationBean annotation : bean.getAnnotations()) {
				annotations.add(parser.toResource(annotation));
			}
		}
		
		return new Verse()
			.withVerseId(bean.getId())
			.withChapterId(chapterId)
			.withNumber(bean.getNumber())
			.withText(bean.getText())
			.withAnnotations(annotations);
	}
}