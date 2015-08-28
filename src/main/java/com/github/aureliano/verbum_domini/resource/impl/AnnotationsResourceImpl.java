package com.github.aureliano.verbum_domini.resource.impl;

import com.github.aureliano.verbum_domini.resource.Annotations;
import com.github.aureliano.verbum_domini.service.AnnotationsService;

public class AnnotationsResourceImpl implements Annotations {

	public AnnotationsResourceImpl() {
		super();
	}

	@Override
	public GetAnnotationsResponse getAnnotations(Long start, Long pages) throws Exception {
		return GetAnnotationsResponse.withJsonOK(AnnotationsService.fetchAll(start, pages));
	}

	@Override
	public GetAnnotationsByAnnotationIdResponse getAnnotationsByAnnotationId(String annotationId) throws Exception {
		return GetAnnotationsByAnnotationIdResponse.withJsonOK(AnnotationsService.fetchAnnotationById(annotationId));
	}
}