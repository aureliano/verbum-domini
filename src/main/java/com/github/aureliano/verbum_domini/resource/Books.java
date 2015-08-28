package com.github.aureliano.verbum_domini.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import com.github.aureliano.verbum_domini.model.Annotation;
import com.github.aureliano.verbum_domini.model.Annotations;
import com.github.aureliano.verbum_domini.model.Book;
import com.github.aureliano.verbum_domini.model.Chapter;
import com.github.aureliano.verbum_domini.model.Chapters;
import com.github.aureliano.verbum_domini.model.Verse;
import com.github.aureliano.verbum_domini.model.Verses;

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

	/**
	 * 
	 * @param start
	 *	 The first page to return (> 0)
	 * @param pages
	 *	 The number of pages to return (> 0 <= 2)
	 * @param bookId
	 *	 
	 */
	@GET
	@Path("{bookId}/chapters")
	@Produces({
		"application/json"
	})
	Books.GetBooksByBookIdChaptersResponse getBooksByBookIdChapters(
		@PathParam("bookId")
		String bookId,
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
	 * @param bookId
	 *	 
	 */
	@GET
	@Path("{bookId}/chapters/{chapterId}")
	@Produces({
		"application/json"
	})
	Books.GetBooksByBookIdChaptersByChapterIdResponse getBooksByBookIdChaptersByChapterId(
		@PathParam("chapterId")
		String chapterId,
		@PathParam("bookId")
		String bookId)
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
	 * @param bookId
	 *	 
	 */
	@GET
	@Path("{bookId}/chapters/{chapterId}/verses")
	@Produces({
		"application/json"
	})
	Books.GetBooksByBookIdChaptersByChapterIdVersesResponse getBooksByBookIdChaptersByChapterIdVerses(
		@PathParam("chapterId")
		String chapterId,
		@PathParam("bookId")
		String bookId,
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
	 * @param bookId
	 *	 
	 */
	@GET
	@Path("{bookId}/chapters/{chapterId}/verses/{verseId}")
	@Produces({
		"application/json"
	})
	Books.GetBooksByBookIdChaptersByChapterIdVersesByVerseIdResponse getBooksByBookIdChaptersByChapterIdVersesByVerseId(
		@PathParam("verseId")
		String verseId,
		@PathParam("chapterId")
		String chapterId,
		@PathParam("bookId")
		String bookId)
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
	 * @param bookId
	 *	 
	 */
	@GET
	@Path("{bookId}/chapters/{chapterId}/annotations")
	@Produces({
		"application/json"
	})
	Books.GetBooksByBookIdChaptersByChapterIdAnnotationsResponse getBooksByBookIdChaptersByChapterIdAnnotations(
		@PathParam("chapterId")
		String chapterId,
		@PathParam("bookId")
		String bookId,
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
	 * @param bookId
	 *	 
	 */
	@GET
	@Path("{bookId}/chapters/{chapterId}/annotations/{annotationId}")
	@Produces({
		"application/json"
	})
	Books.GetBooksByBookIdChaptersByChapterIdAnnotationsByAnnotationIdResponse getBooksByBookIdChaptersByChapterIdAnnotationsByAnnotationId(
		@PathParam("annotationId")
		String annotationId,
		@PathParam("chapterId")
		String chapterId,
		@PathParam("bookId")
		String bookId)
		throws Exception
	;

	public class GetBooksByBookIdChaptersByChapterIdAnnotationsByAnnotationIdResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetBooksByBookIdChaptersByChapterIdAnnotationsByAnnotationIdResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Books.GetBooksByBookIdChaptersByChapterIdAnnotationsByAnnotationIdResponse withJsonOK(Annotation entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Books.GetBooksByBookIdChaptersByChapterIdAnnotationsByAnnotationIdResponse(responseBuilder.build());
		}
	}

	public class GetBooksByBookIdChaptersByChapterIdAnnotationsResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetBooksByBookIdChaptersByChapterIdAnnotationsResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Books.GetBooksByBookIdChaptersByChapterIdAnnotationsResponse withJsonOK(Annotations entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Books.GetBooksByBookIdChaptersByChapterIdAnnotationsResponse(responseBuilder.build());
		}
	}

	public class GetBooksByBookIdChaptersByChapterIdResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetBooksByBookIdChaptersByChapterIdResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Books.GetBooksByBookIdChaptersByChapterIdResponse withJsonOK(Chapter entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Books.GetBooksByBookIdChaptersByChapterIdResponse(responseBuilder.build());
		}
	}

	public class GetBooksByBookIdChaptersByChapterIdVersesByVerseIdResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetBooksByBookIdChaptersByChapterIdVersesByVerseIdResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Books.GetBooksByBookIdChaptersByChapterIdVersesByVerseIdResponse withJsonOK(Verse entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Books.GetBooksByBookIdChaptersByChapterIdVersesByVerseIdResponse(responseBuilder.build());
		}
	}

	public class GetBooksByBookIdChaptersByChapterIdVersesResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetBooksByBookIdChaptersByChapterIdVersesResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Books.GetBooksByBookIdChaptersByChapterIdVersesResponse withJsonOK(Verses entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Books.GetBooksByBookIdChaptersByChapterIdVersesResponse(responseBuilder.build());
		}
	}

	public class GetBooksByBookIdChaptersResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetBooksByBookIdChaptersResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Books.GetBooksByBookIdChaptersResponse withJsonOK(Chapters entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Books.GetBooksByBookIdChaptersResponse(responseBuilder.build());
		}
	}

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