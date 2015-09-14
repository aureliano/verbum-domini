package com.github.aureliano.verbum_domini.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.aureliano.verbum_domini.model.Bible;
import com.github.aureliano.verbum_domini.model.Bibles;
import com.github.aureliano.verbum_domini.service.BiblesService;

@Path("bibles")
public class BiblesResource {

	public BiblesResource() {
		super();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getBibles(
			@QueryParam("start") Long start,
			@QueryParam("pages") Long pages) {
		
		Bibles bibles = BiblesService.fetchAll(start, pages);
		return Response.status(200).entity(bibles).build();
	}
	
	@Path("{bibleId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getBibleById(
			@PathParam("bibleId") String bibleId) {
		
		Bible bible = BiblesService.fetchById(bibleId);
		
		if (bible == null) {
			return Response.status(404).build();
		}
		
		return Response.status(200).entity(bible).build();
	}
}