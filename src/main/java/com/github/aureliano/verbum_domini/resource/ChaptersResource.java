package com.github.aureliano.verbum_domini.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.aureliano.verbum_domini.model.Chapter;
import com.github.aureliano.verbum_domini.model.Chapters;
import com.github.aureliano.verbum_domini.service.ChaptersService;

@Path("chapters")
public class ChaptersResource {

	public ChaptersResource() {
		super();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getChapters(
			@QueryParam("start") Long start,
			@QueryParam("pages") Long pages) {
		
		Chapters chapters = ChaptersService.fetchAll(start, pages);
		return Response.status(200).entity(chapters).build();
	}

	@Path("{chapterId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getChapterById(
			@PathParam("chapterId") String chapterId) {
		
		Chapter chapter = ChaptersService.fetchChapterById(chapterId);
		
		if (chapter == null) {
			return Response.status(404).build();
		}
		
		return Response.status(200).entity(chapter).build();
	}
}