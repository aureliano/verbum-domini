package com.github.aureliano.verbum_domini.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.aureliano.verbum_domini.model.Annotations;
import com.github.aureliano.verbum_domini.model.Chapter;
import com.github.aureliano.verbum_domini.model.Chapters;
import com.github.aureliano.verbum_domini.model.Verses;
import com.github.aureliano.verbum_domini.service.AnnotationsService;
import com.github.aureliano.verbum_domini.service.ChaptersService;
import com.github.aureliano.verbum_domini.service.VersesService;

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
	
	@Path("{chapterId}/verses")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getChapterVerses(
			@PathParam("chapterId") String chapterId,
			@QueryParam("start") Long start,
			@QueryParam("pages") Long pages) {
		
		Chapter chapter = ChaptersService.fetchChapterById(chapterId);
		
		if (chapter == null) {
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
		
		Chapter chapter = ChaptersService.fetchChapterById(chapterId);
		
		if (chapter == null) {
			return Response.status(404).build();
		}
		
		return VersesResource.fetchVerseById(verseId);
	}
	
	@Path("{chapterId}/annotations")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getChapterAnnotations(
			@PathParam("chapterId") String chapterId,
			@QueryParam("start") Long start,
			@QueryParam("pages") Long pages) {
		
		Chapter chapter = ChaptersService.fetchChapterById(chapterId);
		
		if (chapter == null) {
			return Response.status(404).build();
		}
		
		Annotations annotations = AnnotationsService.fetchAnnotationsByChapter(chapterId, start, pages);
		return Response.status(200).entity(annotations).build();
	}
	
	@Path("{chapterId}/annotations/{annotationId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getChapterAnnotationById(
			@PathParam("chapterId") String chapterId,
			@PathParam("annotationId") String annotationId) {
		
		Chapter chapter = ChaptersService.fetchChapterById(chapterId);
		
		if (chapter == null) {
			return Response.status(404).build();
		}
		
		return AnnotationsResource.fetchAnnotationById(annotationId);
	}
}