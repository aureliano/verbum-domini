package com.github.aureliano.verbum_domini.parser;

import com.github.aureliano.verbum_domini.core.bean.AnnotationBean;
import com.github.aureliano.verbum_domini.model.Annotation;

public class AnnotationParser implements IResourceParser<AnnotationBean> {

	public AnnotationParser() {
		super();
	}
	
	@Override
	public Annotation toResource(AnnotationBean bean) {
		return new Annotation()
			.withAnnotationId(bean.getId())
			.withNumber(bean.getNumber())
			.withText(bean.getText());
	}
}