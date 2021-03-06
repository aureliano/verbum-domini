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
import com.github.aureliano.verbum_domini.model.Bible;
import com.github.aureliano.verbum_domini.model.Bibles;
import com.github.aureliano.verbum_domini.model.Books;
import com.github.aureliano.verbum_domini.model.Chapters;
import com.github.aureliano.verbum_domini.model.Verses;
import com.github.aureliano.verbum_domini.service.BiblesService;
import com.github.aureliano.verbum_domini.service.BooksService;
import com.github.aureliano.verbum_domini.service.ChaptersService;
import com.github.aureliano.verbum_domini.service.VersesService;

@Path("bibles")
public class BiblesResource {

	private static final Logger logger = Logger.getLogger(BiblesResource.class);
	
	public BiblesResource() {
		super();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getBibles(
			@QueryParam("start") Long start,
			@QueryParam("pages") Long pages) {
		
		String url = UrlHelper.join("bibles");
		logger.info("Service: " + url + ", start: " + start + ", pages: " + pages);
		
		Bibles bibles = BiblesService.fetchAll(start, pages);
		return Response.status(200).entity(bibles).build();
	}
	
	@Path("{bibleId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getBibleById(
			@PathParam("bibleId") String bibleId) {
		
		String url = UrlHelper.join("bibles", bibleId);
		logger.info("Service: " + url);
		
		Bible bible = BiblesService.fetchById(bibleId);
		
		if (bible == null) {
			logger.warn("Response 404 to URL " + url);
			return Response.status(404).build();
		}
		
		return Response.status(200).entity(bible).build();
	}
	
	@GET
	@Path("{bibleId}/books")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getBibleBooks(
			@PathParam("bibleId") String bibleId,
			@QueryParam("start") Long start,
			@QueryParam("pages") Long pages) {
		
		String url = UrlHelper.join("bibles", bibleId, "books");
		logger.info("Service: " + url + ", start: " + start + ", pages: " + pages);
		
		if (!BiblesService.exist(bibleId)) {
			logger.warn("Response 404 to URL " + url);
			return Response.status(404).build();
		}
		
		Books books = BooksService.fetchBooksByBible(bibleId, start, pages);
		return Response.status(200).entity(books).build();
	}
	
	@GET
	@Path("{bibleId}/books/{bookId}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getBibleBooksById(
			@PathParam("bibleId") String bibleId,
			@PathParam("bookId") String bookId) {
		
		String url = UrlHelper.join("bibles", bibleId, "books", bookId);
		logger.info("Service: " + url);
		
		if (!BiblesService.exist(bibleId)) {
			logger.warn("Response 404 to URL " + url);
			return Response.status(404).build();
		}
		
		return BooksResource.fetchBookById(bookId);
	}
	
	@Path("{bibleId}/books/{bookId}/chapters")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getChaptersByBookFromBible(
			@PathParam("bibleId") String bibleId,
			@PathParam("bookId") String bookId,
			@QueryParam("start") Long start,
			@QueryParam("pages") Long pages) {
		
		String url = UrlHelper.join("bibles", bibleId, "books", bookId, "chapters");
		logger.info("Service: " + url + ", start: " + start + ", pages: " + pages);
		
		if (((!BiblesService.exist(bibleId)) || (!BooksService.exist(bookId)))) {
			logger.warn("Response 404 to URL " + url);
			return Response.status(404).build();
		}
		
		Chapters chapters = ChaptersService.fetchChaptersByBook(bookId, start, pages);
		return Response.status(200).entity(chapters).build();
	}
	
	@Path("{bibleId}/books/{bookId}/chapters/{chapterId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getChapterByIdAndBookFromBible(
			@PathParam("bibleId") String bibleId,
			@PathParam("bookId") String bookId,
			@PathParam("chapterId") String chapterId) {
		
		String url = UrlHelper.join("bibles", bibleId, "books", bookId, "chapters", chapterId);
		logger.info("Service: " + url);
		
		if (((!BiblesService.exist(bibleId)) || (!BooksService.exist(bookId)))) {
			logger.warn("Response 404 to URL " + url);
			return Response.status(404).build();
		}
		
		return ChaptersResource.fetchChapterById(chapterId);
	}
	
	@Path("{bibleId}/books/{bookId}/chapters/{chapterId}/verses")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getVersesByChapterAndBookFromBible(
			@PathParam("bibleId") String bibleId,
			@PathParam("bookId") String bookId,
			@PathParam("chapterId") String chapterId,
			@QueryParam("start") Long start,
			@QueryParam("pages") Long pages) {
		
		String url = UrlHelper.join("bibles", bibleId, "books", bookId, "chapters", chapterId, "verses");
		logger.info("Service: " + url + ", start: " + start + ", pages: " + pages);
		
		if (((!BiblesService.exist(bibleId)) || (!BooksService.exist(bookId))
				|| (!ChaptersService.exist(chapterId)))) {
			logger.warn("Response 404 to URL " + url);
			return Response.status(404).build();
		}
		
		Verses verses = VersesService.fetchVersesByChapter(chapterId, start, pages);
		return Response.status(200).entity(verses).build();
	}
	
	@Path("{bibleId}/books/{bookId}/chapters/{chapterId}/verses/{verseId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getVerseByIdAndChapterFromBook(
			@PathParam("bibleId") String bibleId,
			@PathParam("bookId") String bookId,
			@PathParam("chapterId") String chapterId,
			@PathParam("verseId") String verseId) {
		
		String url = UrlHelper.join("bibles", bibleId, "books", bookId,
				"chapters", chapterId, "verses", verseId);
		logger.info("Service: " + url);
		
		if (((!BiblesService.exist(bibleId)) || (!BooksService.exist(bookId))
				|| (!ChaptersService.exist(chapterId)))) {
			logger.warn("Response 404 to URL " + url);
			return Response.status(404).build();
		}
		
		return VersesResource.fetchVerseById(verseId);
	}
}