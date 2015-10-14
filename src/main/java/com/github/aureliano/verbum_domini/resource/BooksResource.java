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
import com.github.aureliano.verbum_domini.model.Annotations;
import com.github.aureliano.verbum_domini.model.Book;
import com.github.aureliano.verbum_domini.model.Books;
import com.github.aureliano.verbum_domini.model.Chapters;
import com.github.aureliano.verbum_domini.model.Verses;
import com.github.aureliano.verbum_domini.service.AnnotationsService;
import com.github.aureliano.verbum_domini.service.BooksService;
import com.github.aureliano.verbum_domini.service.ChaptersService;
import com.github.aureliano.verbum_domini.service.VersesService;

@Path("books")
public class BooksResource {

	private static final Logger logger = Logger.getLogger(BooksResource.class);

	public BooksResource() {
		super();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getBooks(
			@QueryParam("start") Long start,
			@QueryParam("pages") Long pages) {
		
		String url = UrlHelper.join("books");
		logger.info("Service: " + url + ", start: " + start + ", pages: " + pages);
		
		Books books = BooksService.fetchAll(start, pages);
		return Response.status(200).entity(books).build();
	}

	@Path("{bookId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getBookById(
			@PathParam("bookId") String bookId) {
		
		return fetchBookById(bookId);
	}
	
	@Path("{bookId}/chapters")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getBookChapters(
			@PathParam("bookId") String bookId,
			@QueryParam("start") Long start,
			@QueryParam("pages") Long pages) {
		
		String url = UrlHelper.join("books", bookId, "chapters");
		logger.info("Service: " + url + ", start: " + start + ", pages: " + pages);
		
		if (!BooksService.exist(bookId)) {
			logger.warn("Response 404 to URL " + url);
			return Response.status(404).build();
		}
		
		Chapters chapters = ChaptersService.fetchChaptersByBook(bookId, start, pages);
		return Response.status(200).entity(chapters).build();
	}
	
	@Path("{bookId}/chapters/{chapterId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getBookChapterById(
			@PathParam("bookId") String bookId,
			@PathParam("chapterId") String chapterId) {
		
		String url = UrlHelper.join("books", bookId, "chapters", chapterId);
		logger.info("Service: " + url);
		
		if (!BooksService.exist(bookId)) {
			logger.warn("Response 404 to URL " + url);
			return Response.status(404).build();
		}
		
		return ChaptersResource.fetchChapterById(chapterId);
	}
	
	@Path("{bookId}/chapters/{chapterId}/verses")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getVersesByChapterFromBook(
			@PathParam("bookId") String bookId,
			@PathParam("chapterId") String chapterId,
			@QueryParam("start") Long start,
			@QueryParam("pages") Long pages) {
		
		String url = UrlHelper.join("books", bookId, "chapters", chapterId, "verses");
		logger.info("Service: " + url + ", start: " + start + ", pages: " + pages);
		
		if (((!BooksService.exist(bookId)) || ((!ChaptersService.exist(chapterId))))) {
			logger.warn("Response 404 to URL " + url);
			return Response.status(404).build();
		}
		
		Verses verses = VersesService.fetchVersesByChapter(chapterId, start, pages);
		return Response.status(200).entity(verses).build();
	}
	
	@Path("{bookId}/chapters/{chapterId}/verses/{verseId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getVerseByIdAndChapterFromBook(
			@PathParam("bookId") String bookId,
			@PathParam("chapterId") String chapterId,
			@PathParam("verseId") String verseId) {
		
		String url = UrlHelper.join("books", bookId, "chapters", chapterId, "verses", verseId);
		logger.info("Service: " + url);
		
		if (((!BooksService.exist(bookId)) || ((!ChaptersService.exist(chapterId))))) {
			logger.warn("Response 404 to URL " + url);
			return Response.status(404).build();
		}
		
		return VersesResource.fetchVerseById(verseId);
	}
	
	@Path("{bookId}/chapters/{chapterId}/annotations")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAnnotationsByChapterFromBook(
			@PathParam("bookId") String bookId,
			@PathParam("chapterId") String chapterId,
			@QueryParam("start") Long start,
			@QueryParam("pages") Long pages) {
		
		String url = UrlHelper.join("books", bookId, "chapters", chapterId, "annotations");
		logger.info("Service: " + url + ", start: " + start + ", pages: " + pages);
		
		if (((!BooksService.exist(bookId)) || ((!ChaptersService.exist(chapterId))))) {
			logger.warn("Response 404 to URL " + url);
			return Response.status(404).build();
		}
		
		Annotations annotations = AnnotationsService.fetchAnnotationsByChapter(chapterId, start, pages);
		return Response.status(200).entity(annotations).build();
	}
	
	@Path("{bookId}/chapters/{chapterId}/annotations/{annotationId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAnnotationByIdAndChapterFromBook(
			@PathParam("bookId") String bookId,
			@PathParam("chapterId") String chapterId,
			@PathParam("annotationId") String annotationId) {
		
		String url = UrlHelper.join("books", bookId, "chapters", chapterId, "annotations", annotationId);
		logger.info("Service: " + url);
		
		if (((!BooksService.exist(bookId)) || ((!ChaptersService.exist(chapterId))))) {
			logger.warn("Response 404 to URL " + url);
			return Response.status(404).build();
		}
		
		return AnnotationsResource.fetchAnnotationById(annotationId);
	}
	
	public static Response fetchBookById(String bookId) {
		String url = UrlHelper.join("books", bookId);
		logger.info("Service: " + url);
		
		Book book = BooksService.fetchBookById(bookId);
		
		if (book == null) {
			logger.warn("Response 404 to URL " + url);
			return Response.status(404).build();
		}
		
		return Response.status(200).entity(book).build();
	}
}