package com.github.aureliano.verbum_domini.web;

public class PageRange {

	private Integer first;
	private Integer last;
	
	public PageRange() {
		super();
	}
	
	public PageRange(Integer first, Integer last) {
		this.first = first;
		this.last = last;
	}

	public Integer getFirst() {
		return first;
	}

	public PageRange withFirst(Integer first) {
		this.first = first;
		return this;
	}

	public Integer getLast() {
		return last;
	}

	public PageRange withLast(Integer last) {
		this.last = last;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((last == null) ? 0 : last.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageRange other = (PageRange) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (last == null) {
			if (other.last != null)
				return false;
		} else if (!last.equals(other.last))
			return false;
		return true;
	}
}