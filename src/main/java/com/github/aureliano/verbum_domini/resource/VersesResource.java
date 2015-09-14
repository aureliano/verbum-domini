package com.github.aureliano.verbum_domini.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.aureliano.verbum_domini.model.Verse;
import com.github.aureliano.verbum_domini.model.Verses;
import com.github.aureliano.verbum_domini.service.VersesService;

@Path("verses")
public class VersesResource {

	public VersesResource() {
		super();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getVerses(
			@QueryParam("start") Long start,
			@QueryParam("pages") Long pages) {
		
		Verses verses = VersesService.fetchAll(start, pages);
		return Response.status(200).entity(verses).build();
	}

	@Path("{verseId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getVerseById(
			@PathParam("verseId") String verseId) {
		
		return fetchVerseById(verseId);
	}
	
	public static Response fetchVerseById(String verseId) {
		Verse verse = VersesService.fetchVerseById(verseId);
		
		if (verse == null) {
			return Response.status(404).build();
		}
		
		return Response.status(200).entity(verse).build();
	}
}