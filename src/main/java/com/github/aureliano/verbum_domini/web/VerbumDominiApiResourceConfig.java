package com.github.aureliano.verbum_domini.web;

import org.glassfish.jersey.server.ResourceConfig;

import com.github.aureliano.verbum_domini.resource.bible.BiblesResource;
import com.github.aureliano.verbum_domini.resource.bible.BooksResource;
import com.github.aureliano.verbum_domini.resource.bible.ChaptersResource;
import com.github.aureliano.verbum_domini.resource.impl.AnnotationsResourceImpl;
import com.github.aureliano.verbum_domini.resource.impl.VersesResourceImpl;

public class VerbumDominiApiResourceConfig extends ResourceConfig {

	public VerbumDominiApiResourceConfig() {
		packages("com.github.aureliano.verbum_domini.resource.impl");
		
		register(CharsetResponseFilter.class);
		register(BiblesResource.class);
		register(BooksResource.class);
		register(ChaptersResource.class);
		register(VersesResourceImpl.class);
		register(AnnotationsResourceImpl.class);
	}
}