package com.github.aureliano.verbum_domini.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import com.github.aureliano.verbum_domini.model.Bible;
import com.github.aureliano.verbum_domini.model.Books;

@Path("bibles")
public interface Bibles {

	/**
	 * 
	 * @param start
	 *	 The first page to return
	 * @param pages
	 *	 The number of pages to return
	 */
	@GET
	@Produces({
		"application/json"
	})
	Bibles.GetBiblesResponse getBibles(
		@QueryParam("start")
		Long start,
		@QueryParam("pages")
		Long pages)
		throws Exception
	;

	/**
	 * 
	 * @param language
	 *	 
	 */
	@GET
	@Path("{language}")
	@Produces({
		"application/json"
	})
	Bibles.GetBiblesByLanguageResponse getBiblesByLanguage(
		@PathParam("language")
		String language)
		throws Exception
	;

	/**
	 * 
	 * @param start
	 *	 The first page to return
	 * @param pages
	 *	 The number of pages to return
	 * @param language
	 *	 
	 */
	@GET
	@Path("{language}/books")
	@Produces({
		"application/json"
	})
	Bibles.GetBiblesByLanguageBooksResponse getBiblesByLanguageBooks(
		@PathParam("language")
		String language,
		@QueryParam("start")
		Long start,
		@QueryParam("pages")
		Long pages)
		throws Exception
	;

	public class GetBiblesByLanguageBooksResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetBiblesByLanguageBooksResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Bibles.GetBiblesByLanguageBooksResponse jsonOK(Books entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Bibles.GetBiblesByLanguageBooksResponse(responseBuilder.build());
		}

	}

	public class GetBiblesByLanguageResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetBiblesByLanguageResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Bibles.GetBiblesByLanguageResponse jsonOK(Bible entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Bibles.GetBiblesByLanguageResponse(responseBuilder.build());
		}

	}

	public class GetBiblesResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetBiblesResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Bibles.GetBiblesResponse jsonOK(com.github.aureliano.verbum_domini.model.Bibles entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Bibles.GetBiblesResponse(responseBuilder.build());
		}
	}
}