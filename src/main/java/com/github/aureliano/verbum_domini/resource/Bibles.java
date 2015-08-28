package com.github.aureliano.verbum_domini.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import com.github.aureliano.verbum_domini.model.Annotation;
import com.github.aureliano.verbum_domini.model.Annotations;
import com.github.aureliano.verbum_domini.model.Bible;
import com.github.aureliano.verbum_domini.model.Book;
import com.github.aureliano.verbum_domini.model.Books;
import com.github.aureliano.verbum_domini.model.Chapter;
import com.github.aureliano.verbum_domini.model.Chapters;
import com.github.aureliano.verbum_domini.model.Verse;
import com.github.aureliano.verbum_domini.model.Verses;

@Path("bibles")
public interface Bibles {

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
	 *	 The first page to return (> 0)
	 * @param bibleId
	 *	 
	 * @param pages
	 *	 The number of pages to return (> 0 <= 2)
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

	/**
	 * 
	 * @param start
	 *	 The first page to return (> 0)
	 * @param bibleId
	 *	 
	 * @param pages
	 *	 The number of pages to return (> 0 <= 2)
	 * @param bookId
	 *	 
	 */
	@GET
	@Path("{bibleId}/books/{bookId}/chapters")
	@Produces({
		"application/json"
	})
	Bibles.GetBiblesByBibleIdBooksByBookIdChaptersResponse getBiblesByBibleIdBooksByBookIdChapters(
		@PathParam("bookId")
		String bookId,
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
	 * @param chapterId
	 *	 
	 * @param bibleId
	 *	 
	 * @param bookId
	 *	 
	 */
	@GET
	@Path("{bibleId}/books/{bookId}/chapters/{chapterId}")
	@Produces({
		"application/json"
	})
	Bibles.GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdResponse getBiblesByBibleIdBooksByBookIdChaptersByChapterId(
		@PathParam("chapterId")
		String chapterId,
		@PathParam("bookId")
		String bookId,
		@PathParam("bibleId")
		String bibleId)
		throws Exception
	;

	/**
	 * 
	 * @param chapterId
	 *	 
	 * @param start
	 *	 The first page to return (> 0)
	 * @param bibleId
	 *	 
	 * @param pages
	 *	 The number of pages to return (> 0 <= 2)
	 * @param bookId
	 *	 
	 */
	@GET
	@Path("{bibleId}/books/{bookId}/chapters/{chapterId}/verses")
	@Produces({
		"application/json"
	})
	Bibles.GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdVersesResponse getBiblesByBibleIdBooksByBookIdChaptersByChapterIdVerses(
		@PathParam("chapterId")
		String chapterId,
		@PathParam("bookId")
		String bookId,
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
	 * @param chapterId
	 *	 
	 * @param verseId
	 *	 
	 * @param bibleId
	 *	 
	 * @param bookId
	 *	 
	 */
	@GET
	@Path("{bibleId}/books/{bookId}/chapters/{chapterId}/verses/{verseId}")
	@Produces({
		"application/json"
	})
	Bibles.GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdVersesByVerseIdResponse getBiblesByBibleIdBooksByBookIdChaptersByChapterIdVersesByVerseId(
		@PathParam("verseId")
		String verseId,
		@PathParam("chapterId")
		String chapterId,
		@PathParam("bookId")
		String bookId,
		@PathParam("bibleId")
		String bibleId)
		throws Exception
	;

	/**
	 * 
	 * @param chapterId
	 *	 
	 * @param start
	 *	 The first page to return (> 0)
	 * @param bibleId
	 *	 
	 * @param pages
	 *	 The number of pages to return (> 0 <= 2)
	 * @param bookId
	 *	 
	 */
	@GET
	@Path("{bibleId}/books/{bookId}/chapters/{chapterId}/annotations")
	@Produces({
		"application/json"
	})
	Bibles.GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdAnnotationsResponse getBiblesByBibleIdBooksByBookIdChaptersByChapterIdAnnotations(
		@PathParam("chapterId")
		String chapterId,
		@PathParam("bookId")
		String bookId,
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
	 * @param chapterId
	 *	 
	 * @param bibleId
	 *	 
	 * @param annotationId
	 *	 
	 * @param bookId
	 *	 
	 */
	@GET
	@Path("{bibleId}/books/{bookId}/chapters/{chapterId}/annotations/{annotationId}")
	@Produces({
		"application/json"
	})
	Bibles.GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdAnnotationsByAnnotationIdResponse getBiblesByBibleIdBooksByBookIdChaptersByChapterIdAnnotationsByAnnotationId(
		@PathParam("annotationId")
		String annotationId,
		@PathParam("chapterId")
		String chapterId,
		@PathParam("bookId")
		String bookId,
		@PathParam("bibleId")
		String bibleId)
		throws Exception
	;

	public class GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdAnnotationsByAnnotationIdResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdAnnotationsByAnnotationIdResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Bibles.GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdAnnotationsByAnnotationIdResponse withJsonOK(Annotation entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Bibles.GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdAnnotationsByAnnotationIdResponse(responseBuilder.build());
		}
	}

	public class GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdAnnotationsResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdAnnotationsResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Bibles.GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdAnnotationsResponse withJsonOK(Annotations entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Bibles.GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdAnnotationsResponse(responseBuilder.build());
		}
	}

	public class GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Bibles.GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdResponse withJsonOK(Chapter entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Bibles.GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdResponse(responseBuilder.build());
		}
	}

	public class GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdVersesByVerseIdResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdVersesByVerseIdResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Bibles.GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdVersesByVerseIdResponse withJsonOK(Verse entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Bibles.GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdVersesByVerseIdResponse(responseBuilder.build());
		}
	}

	public class GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdVersesResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdVersesResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Bibles.GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdVersesResponse withJsonOK(Verses entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Bibles.GetBiblesByBibleIdBooksByBookIdChaptersByChapterIdVersesResponse(responseBuilder.build());
		}
	}

	public class GetBiblesByBibleIdBooksByBookIdChaptersResponse
		extends com.github.aureliano.verbum_domini.support.ResponseWrapper
	{
		private GetBiblesByBibleIdBooksByBookIdChaptersResponse(Response delegate) {
			super(delegate);
		}

		/**
		 * OK
		 * 
		 * @param entity
		 *	 
		 */
		public static Bibles.GetBiblesByBibleIdBooksByBookIdChaptersResponse withJsonOK(Chapters entity) {
			Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
			responseBuilder.entity(entity);
			return new Bibles.GetBiblesByBibleIdBooksByBookIdChaptersResponse(responseBuilder.build());
		}
	}

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