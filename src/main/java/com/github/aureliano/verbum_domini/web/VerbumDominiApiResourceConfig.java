package com.github.aureliano.verbum_domini.web;

import org.glassfish.jersey.server.ResourceConfig;

import com.github.aureliano.verbum_domini.resource.impl.BiblesResourceImpl;

public class VerbumDominiApiResourceConfig extends ResourceConfig {

	public VerbumDominiApiResourceConfig() {
		packages("com.github.aureliano.verbum_domini.resource.impl");
		
		register(CharsetResponseFilter.class);
		register(BiblesResourceImpl.class);
	}
}