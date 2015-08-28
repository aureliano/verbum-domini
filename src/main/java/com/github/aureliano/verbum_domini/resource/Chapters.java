package com.github.aureliano.verbum_domini.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import com.github.aureliano.verbum_domini.model.Annotation;
import com.github.aureliano.verbum_domini.model.Annotations;
import com.github.aureliano.verbum_domini.model.Chapter;
import com.github.aureliano.verbum_domini.model.Verse;
import com.github.aureliano.verbum_domini.model.Verses;

@Path("chapters")
public interface Chapters {

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
	Chapters.GetChaptersResponse getChapters(
		@QueryParam("start")
		Long start,
		@QueryParam("pages")
		Long pages)
		throws Exception
	;

	/**
	 * 
	 * @param chapterId
	 *	 
	 */
	@GET
	@Path("{chapterId}")
	@Produces({
		"application/json"
	})
	Chapters.GetChaptersByChapterIdResponse getChaptersByChapterId(
		@PathParam("chapterId")
		String chapterId)
		throws Exception
	;

	/**
	 * 
	 * @param chapterId
	 *	 
	 * @param start
	 *	 The first page to return (> 0)
	 * @param pages
	 *	 The number of pages to return (> 0 <= 2)
	 */
	@GET
	@Path("{chapterId}/verses")
	@Produces({
		"application/json"
	})
	Chapters.GetChaptersByChapterIdVersesResponse getChaptersByChapterIdVerses(
		@PathParam("chapterId")
		String chapterId,
		@QueryParam("start")
		Long start,
		@QueryParam("pages")
		Long pages)
		throws Exception
	;

	/**
	 * 
	 * @param chapterId
	 *	 
	 * @param verseId
	 *	 
	 */
	@GET
	@Path("{chapterId}/verses/{verseId}")
	@Produces({
		"application/json"
	})
	Chapters.GetChaptersByChapterIdVersesByVerseIdResponse getChaptersByChapterIdVersesByVerseId(
		@PathParam("verseId")
		String verseId,
		@PathParam("chapterId")
		String chapterId)
		throws Exception
	;

	/**
	 * 
	 * @param chapterId
	 *	 
	 * @param start
	 *	 The first page to return (> 0)
	 * @param pages
	 *	 The number of pages to return (> 0 <= 2)
	 */
	@GET
	@Path("{chapterId}/annotations")
	@Produces({
		"application/json"
	})
	Chapters.GetChaptersByChapterIdAnnotationsResponse getChaptersByChapterIdAnnotations(
		@PathParam("chapterId")
		String chapterId,
		@QueryParam("start")
		Long start,
		@QueryParam("pages")
		Long pages)
		throws Exception
	;

	/**
	 * 
	 * @param chapterId
	 *	 
	 * @param annotationId
	 *	 
	 */
	@GET
	@Path("{chapterId}/annotations/{annotationId}")
	@Produces({
		"application/json"
	})
	Chapters.GetChaptersByChapterIdAnnotationsByAnnotationIdResponse getChaptersByChapterIdAnnotationsByAnnotationId(
		@PathParam("annotationId")
		String annotationId,
		@PathParam("chapterId")
		String chapterId)
		throws Exception
	;

	public class GetChaptersByChapterIdAnnotationsByAnnotationIdResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetChaptersByChapterIdAnnotationsByAnnotationIdResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Chapters.GetChaptersByChapterIdAnnotationsByAnnotationIdResponse withJsonOK(Annotation entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Chapters.GetChaptersByChapterIdAnnotationsByAnnotationIdResponse(responseBuilder.build());
		}
	}

	public class GetChaptersByChapterIdAnnotationsResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetChaptersByChapterIdAnnotationsResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Chapters.GetChaptersByChapterIdAnnotationsResponse withJsonOK(Annotations entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Chapters.GetChaptersByChapterIdAnnotationsResponse(responseBuilder.build());
		}
	}

	public class GetChaptersByChapterIdResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetChaptersByChapterIdResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Chapters.GetChaptersByChapterIdResponse withJsonOK(Chapter entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Chapters.GetChaptersByChapterIdResponse(responseBuilder.build());
		}
	}

	public class GetChaptersByChapterIdVersesByVerseIdResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetChaptersByChapterIdVersesByVerseIdResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Chapters.GetChaptersByChapterIdVersesByVerseIdResponse withJsonOK(Verse entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Chapters.GetChaptersByChapterIdVersesByVerseIdResponse(responseBuilder.build());
		}
	}

	public class GetChaptersByChapterIdVersesResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetChaptersByChapterIdVersesResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Chapters.GetChaptersByChapterIdVersesResponse withJsonOK(Verses entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Chapters.GetChaptersByChapterIdVersesResponse(responseBuilder.build());
		}
	}

	public class GetChaptersResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetChaptersResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Chapters.GetChaptersResponse withJsonOK(com.github.aureliano.verbum_domini.model.Chapters entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Chapters.GetChaptersResponse(responseBuilder.build());
		}
	}
}