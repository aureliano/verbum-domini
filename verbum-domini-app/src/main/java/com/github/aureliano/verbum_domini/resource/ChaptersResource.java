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
import com.github.aureliano.verbum_domini.model.Chapter;
import com.github.aureliano.verbum_domini.model.Chapters;
import com.github.aureliano.verbum_domini.model.Verses;
import com.github.aureliano.verbum_domini.service.ChaptersService;
import com.github.aureliano.verbum_domini.service.VersesService;

@Path("chapters")
public class ChaptersResource {

	private static final Logger logger = Logger.getLogger(ChaptersResource.class);

	public ChaptersResource() {
		super();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getChapters(
			@QueryParam("start") Long start,
			@QueryParam("pages") Long pages) {

		String url = UrlHelper.join("chapters");
		logger.info("Service: " + url + ", start: " + start + ", pages: " + pages);
		
		Chapters chapters = ChaptersService.fetchAll(start, pages);
		return Response.status(200).entity(chapters).build();
	}

	@Path("{chapterId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getChapterById(
			@PathParam("chapterId") String chapterId) {
		
		return fetchChapterById(chapterId);
	}

	@Path("{chapterId}/verses")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getChapterVerses(
			@PathParam("chapterId") String chapterId,
			@QueryParam("start") Long start,
			@QueryParam("pages") Long pages) {

		String url = UrlHelper.join("chapters", chapterId, "verses");
		logger.info("Service: " + url + ", start: " + start + ", pages: " + pages);
		
		if (!ChaptersService.exist(chapterId)) {
			logger.warn("Response 404 to URL " + url);
			return Response.status(404).build();
		}
		
		Verses verses = VersesService.fetchVersesByChapter(chapterId, start, pages);
		return Response.status(200).entity(verses).build();
	}
	
	@Path("{chapterId}/verses/{verseId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getChapterVerseById(
			@PathParam("chapterId") String chapterId,
			@PathParam("verseId") String verseId) {

		String url = UrlHelper.join("chapters", chapterId, "verses", verseId);
		logger.info("Service: " + url);
		
		if (!ChaptersService.exist(chapterId)) {
			logger.warn("Response 404 to URL " + url);
			return Response.status(404).build();
		}
		
		return VersesResource.fetchVerseById(verseId);
	}
	
	public static Response fetchChapterById(String chapterId) {
		String url = UrlHelper.join("chapters", chapterId);
		logger.info("Service: " + url);
		
		Chapter chapter = ChaptersService.fetchChapterById(chapterId);
		
		if (chapter == null) {
			logger.warn("Response 404 to URL " + url);
			return Response.status(404).build();
		}
		
		return Response.status(200).entity(chapter).build();
	}
}