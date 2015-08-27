package com.github.aureliano.verbum_domini.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import com.github.aureliano.verbum_domini.model.Book;

@Path("books")
public interface Books {

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
	Books.GetBooksResponse getBooks(
		@QueryParam("start")
		Long start,
		@QueryParam("pages")
		Long pages)
		throws Exception
	;

	/**
	 * 
	 * @param bookId
	 *	 
	 */
	@GET
	@Path("{bookId}")
	@Produces({
		"application/json"
	})
	Books.GetBooksByBookIdResponse getBooksByBookId(
		@PathParam("bookId")
		String bookId)
		throws Exception
	;

	public class GetBooksByBookIdResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetBooksByBookIdResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Books.GetBooksByBookIdResponse withJsonOK(Book entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Books.GetBooksByBookIdResponse(responseBuilder.build());
		}
	}

	public class GetBooksResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetBooksResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Books.GetBooksResponse withJsonOK(com.github.aureliano.verbum_domini.model.Books entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Books.GetBooksResponse(responseBuilder.build());
		}
	}
}