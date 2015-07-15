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
		if ((size != null) && (size < 0)) {
			throw new IllegalArgumentException("Pagination size must be grater than -1");
		}
		this.size = size;
	}

	public List<T> getElements() {
		return elements;
	}

	public void setElements(List<T> elements) {
		this.elements = elements;
	}
	
	public boolean isEmpty() {
		return ((this.size == null) ? true : (this.size == 0));
	}
}