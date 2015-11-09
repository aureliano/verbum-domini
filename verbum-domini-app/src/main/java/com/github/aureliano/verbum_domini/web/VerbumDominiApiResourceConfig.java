package com.github.aureliano.verbum_domini.web;

import org.glassfish.jersey.server.ResourceConfig;

import com.github.aureliano.verbum_domini.resource.BiblesResource;
import com.github.aureliano.verbum_domini.resource.BooksResource;
import com.github.aureliano.verbum_domini.resource.ChaptersResource;
import com.github.aureliano.verbum_domini.resource.VersesResource;

public class VerbumDominiApiResourceConfig extends ResourceConfig {

	public VerbumDominiApiResourceConfig() {
		packages("com.github.aureliano.verbum_domini.resource");
		
		register(CharsetResponseFilter.class);
		register(BiblesResource.class);
		register(BooksResource.class);
		register(ChaptersResource.class);
		register(VersesResource.class);
	}
}