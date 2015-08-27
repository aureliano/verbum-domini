package com.github.aureliano.verbum_domini.web;

import org.glassfish.jersey.server.ResourceConfig;

import com.github.aureliano.verbum_domini.resource.impl.BiblesResourceImpl;
import com.github.aureliano.verbum_domini.resource.impl.BooksResourceImpl;
import com.github.aureliano.verbum_domini.resource.impl.ChaptersResourceImpl;
import com.github.aureliano.verbum_domini.resource.impl.VersesResourceImpl;

public class VerbumDominiApiResourceConfig extends ResourceConfig {

	public VerbumDominiApiResourceConfig() {
		packages("com.github.aureliano.verbum_domini.resource.impl");
		
		register(CharsetResponseFilter.class);
		register(BiblesResourceImpl.class);
		register(BooksResourceImpl.class);
		register(ChaptersResourceImpl.class);
		register(VersesResourceImpl.class);
	}
}