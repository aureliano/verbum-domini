package com.github.aureliano.verbum_domini.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import com.github.aureliano.verbum_domini.model.Chapter;

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