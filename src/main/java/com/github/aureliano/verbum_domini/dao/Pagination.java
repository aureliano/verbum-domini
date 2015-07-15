package com.github.aureliano.verbum_domini.dao;

import java.util.List;

public class Pagination<T> {

	private Integer size;
	private List<T> elements;
	
	public Pagination() {
		super();
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public List<T> getElements() {
		return elements;
	}

	public void setElements(List<T> elements) {
		this.elements = elements;
	}
}