package com.github.aureliano.verbum_domini.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import com.github.aureliano.verbum_domini.model.Bible;
import com.github.aureliano.verbum_domini.model.Book;
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
	 * @param bibleId
	 *	 
	 */
	@GET
	@Path("{bibleId}")
	@Produces({
		"application/json"
	})
	Bibles.GetBiblesByBibleIdResponse getBiblesByBibleId(
		@PathParam("bibleId")
		String bibleId)
		throws Exception
	;

	/**
	 * 
	 * @param start
	 *	 The first page to return
	 * @param bibleId
	 *	 
	 * @param pages
	 *	 The number of pages to return
	 */
	@GET
	@Path("{bibleId}/books")
	@Produces({
		"application/json"
	})
	Bibles.GetBiblesByBibleIdBooksResponse getBiblesByBibleIdBooks(
		@PathParam("bibleId")
		String bibleId,
		@QueryParam("start")
		Long start,
		@QueryParam("pages")
		Long pages)
		throws Exception
	;

	/**
	 * 
	 * @param bibleId
	 *	 
	 * @param bookId
	 *	 
	 */
	@GET
	@Path("{bibleId}/books/{bookId}")
	@Produces({
		"application/json"
	})
	Bibles.GetBiblesByBibleIdBooksByBookIdResponse getBiblesByBibleIdBooksByBookId(
		@PathParam("bookId")
		String bookId,
		@PathParam("bibleId")
		String bibleId)
		throws Exception
	;

	public class GetBiblesByBibleIdBooksByBookIdResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetBiblesByBibleIdBooksByBookIdResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Bibles.GetBiblesByBibleIdBooksByBookIdResponse withJsonOK(Book entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Bibles.GetBiblesByBibleIdBooksByBookIdResponse(responseBuilder.build());
		}
	}

	public class GetBiblesByBibleIdBooksResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetBiblesByBibleIdBooksResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Bibles.GetBiblesByBibleIdBooksResponse withJsonOK(Books entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Bibles.GetBiblesByBibleIdBooksResponse(responseBuilder.build());
		}
	}

	public class GetBiblesByBibleIdResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetBiblesByBibleIdResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Bibles.GetBiblesByBibleIdResponse withJsonOK(Bible entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Bibles.GetBiblesByBibleIdResponse(responseBuilder.build());
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
		public static Bibles.GetBiblesResponse withJsonOK(com.github.aureliano.verbum_domini.model.Bibles entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Bibles.GetBiblesResponse(responseBuilder.build());
		}
	}
}