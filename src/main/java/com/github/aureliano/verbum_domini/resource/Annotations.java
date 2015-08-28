package com.github.aureliano.verbum_domini.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import com.github.aureliano.verbum_domini.model.Annotation;

@Path("annotations")
public interface Annotations {

	/**
	 * 
	 * @param start
	 *	 The first page to return (> 0)
	 * @param pages
	 *	 The number of pages to return (> 0 <= 2)
	 */
	@GET
	@Produces({
		"application/json"
	})
	Annotations.GetAnnotationsResponse getAnnotations(
		@QueryParam("start")
		Long start,
		@QueryParam("pages")
		Long pages)
		throws Exception
	;

	/**
	 * 
	 * @param annotationId
	 *	 
	 */
	@GET
	@Path("{annotationId}")
	@Produces({
		"application/json"
	})
	Annotations.GetAnnotationsByAnnotationIdResponse getAnnotationsByAnnotationId(
		@PathParam("annotationId")
		String annotationId)
		throws Exception
	;

	public class GetAnnotationsByAnnotationIdResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetAnnotationsByAnnotationIdResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Annotations.GetAnnotationsByAnnotationIdResponse withJsonOK(Annotation entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Annotations.GetAnnotationsByAnnotationIdResponse(responseBuilder.build());
		}
	}

	public class GetAnnotationsResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetAnnotationsResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Annotations.GetAnnotationsResponse withJsonOK(com.github.aureliano.verbum_domini.model.Annotations entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Annotations.GetAnnotationsResponse(responseBuilder.build());
		}
	}
}