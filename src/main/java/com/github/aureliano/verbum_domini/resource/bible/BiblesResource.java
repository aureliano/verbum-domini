package com.github.aureliano.verbum_domini.resource.bible;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
		
		Response.ResponseBuilder responseBuilder = Response
			.status(200);
		
		Bibles bibles = BiblesService.fetchAll(start, pages);
		responseBuilder.entity(bibles);
		
		return responseBuilder.build();
	}
}