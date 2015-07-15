package com.github.aureliano.verbum_domini.web;

public class ServiceParams {

	private Integer start;
	private Integer pages;
	
	public ServiceParams() {
		super();
	}
	
	public static ServiceParams createDefault() {
		return new ServiceParams().withStart(1).withPages(1);
	}

	public Integer getStart() {
		return start;
	}

	public ServiceParams withStart(Object start) {
		if (start == null) {
			this.start = 1;
		} else if (start instanceof Long) {
			this.start = ((Long) start).intValue();
		} else {
			this.start = (Integer) start;
		}
		
		if (this.start < 1) {
			this.start = 1;
		}
		
		return this;
	}

	public Integer getPages() {
		return pages;
	}

	public ServiceParams withPages(Object pages) {
		if (pages == null) {
			this.pages = 1;
		} else if (pages instanceof Long) {
			this.pages = ((Long) pages).intValue();
		} else {
			this.pages = (Integer) pages;
		}
		
		if (this.pages < 1) {
			this.pages = 1;
		}
		
		return this;
	}
}