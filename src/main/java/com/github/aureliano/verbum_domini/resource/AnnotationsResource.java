package com.github.aureliano.verbum_domini.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.github.aureliano.verbum_domini.helper.UrlHelper;
import com.github.aureliano.verbum_domini.model.Annotation;
import com.github.aureliano.verbum_domini.model.Annotations;
import com.github.aureliano.verbum_domini.service.AnnotationsService;

@Path("annotations")
public class AnnotationsResource {

	private static final Logger logger = Logger.getLogger(AnnotationsResource.class);

	public AnnotationsResource() {
		super();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAnnotations(
			@QueryParam("start") Long start,
			@QueryParam("pages") Long pages) {

		String url = UrlHelper.join("annotations");
		logger.info("Service: " + url + ", start: " + start + ", pages: " + pages);
		
		Annotations annotations = AnnotationsService.fetchAll(start, pages);
		return Response.status(200).entity(annotations).build();
	}

	@Path("{annotationId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAnnotationById(
			@PathParam("annotationId") String annotationId) {
		
		return fetchAnnotationById(annotationId);
	}
	
	public static Response fetchAnnotationById(String annotationId) {
		String url = UrlHelper.join("annotations", annotationId);
		logger.info("Service: " + url);
		
		Annotation annotation = AnnotationsService.fetchAnnotationById(annotationId);
		
		if (annotation == null) {
			logger.warn("Response 404 to URL " + url);
			return Response.status(404).build();
		}
		
		return Response.status(200).entity(annotation).build();
	}
}