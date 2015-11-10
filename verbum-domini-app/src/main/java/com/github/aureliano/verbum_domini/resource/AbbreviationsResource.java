package com.github.aureliano.verbum_domini.resource;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.github.aureliano.verbum_domini.core.AppConfiguration;
import com.github.aureliano.verbum_domini.core.data.DataBookAbbreviations;
import com.github.aureliano.verbum_domini.helper.UrlHelper;
import com.github.aureliano.verbum_domini.model.Abbreviation;
import com.github.aureliano.verbum_domini.model.Abbreviations;

@Path("abbreviations")
public class AbbreviationsResource {
	
	private static final Logger logger = Logger.getLogger(AbbreviationsResource.class);

	public AbbreviationsResource() {
		super();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAbbreviations() {

		String url = UrlHelper.join("abbreviations");
		logger.info("Service: " + url);
		
		DataBookAbbreviations dataAbbreviations = AppConfiguration.instance().getDataBookAbbreviations();
		Map<String, String[]> abbreviations = dataAbbreviations.abbreviations();
		
		Abbreviations resourceModel = new Abbreviations().withSize(abbreviations.size());
		for (String book : abbreviations.keySet()) {
			Abbreviation abb = new Abbreviation().withBook(book).withAbbreviations(abbreviations.get(book));
			resourceModel.getAbbreviations().add(abb);
		}
		
		return Response.status(200).entity(resourceModel).build();
	}
}