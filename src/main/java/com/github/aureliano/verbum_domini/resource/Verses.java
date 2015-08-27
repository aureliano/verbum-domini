package com.github.aureliano.verbum_domini.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import com.github.aureliano.verbum_domini.model.Verse;

@Path("verses")
public interface Verses {

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
	Verses.GetVersesResponse getVerses(
		@QueryParam("start")
		Long start,
		@QueryParam("pages")
		Long pages)
		throws Exception
	;

	/**
	 * 
	 * @param verseId
	 *	 
	 */
	@GET
	@Path("{verseId}")
	@Produces({
		"application/json"
	})
	Verses.GetVersesByVerseIdResponse getVersesByVerseId(
		@PathParam("verseId")
		String verseId)
		throws Exception
	;

	public class GetVersesByVerseIdResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetVersesByVerseIdResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Verses.GetVersesByVerseIdResponse withJsonOK(Verse entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Verses.GetVersesByVerseIdResponse(responseBuilder.build());
		}
	}

	public class GetVersesResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetVersesResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Verses.GetVersesResponse withJsonOK(com.github.aureliano.verbum_domini.model.Verses entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Verses.GetVersesResponse(responseBuilder.build());
		}
	}
}