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
import com.github.aureliano.verbum_domini.model.Verse;
import com.github.aureliano.verbum_domini.model.Verses;
import com.github.aureliano.verbum_domini.service.VersesService;

@Path("verses")
public class VersesResource {

	private static final Logger logger = Logger.getLogger(VersesResource.class);

	public VersesResource() {
		super();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getVerses(
			@QueryParam("start") Long start,
			@QueryParam("pages") Long pages) {

		String url = UrlHelper.join("verses");
		logger.info("Service: " + url + ", start: " + start + ", pages: " + pages);
		
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
		String url = UrlHelper.join("verses", verseId);
		logger.info("Service: " + url);
		
		Verse verse = VersesService.fetchVerseById(verseId);
		
		if (verse == null) {
			logger.warn("Response 404 to URL " + url);
			return Response.status(404).build();
		}
		
		return Response.status(200).entity(verse).build();
	}
}