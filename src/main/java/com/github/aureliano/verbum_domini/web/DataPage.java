package com.github.aureliano.verbum_domini.web;

import java.util.List;

public class DataPage {

	private static final Integer DEFAULT_PAGE_SIZE = 10;
	
	private Integer pageSize;
	private Integer pageIndex;
	private Integer pages;
	private PageRange pageRange;
	
	private List<Object> data;
	private Integer total;
	
	private boolean configured;
	
	public DataPage() {
		this.pageSize = DEFAULT_PAGE_SIZE;
		this.pageIndex = 1;
		
		this.configured = false;
	}
	
	public boolean isHaveNextPage() {
		this.synchronizeAttributes();
		return this.pageIndex < this.pages;
	}
	
	public boolean isHavePreviousPage() {
		this.synchronizeAttributes();
		return this.pageIndex > 1;
	}
	
	public boolean isHaveNextPageRange() {
		this.synchronizeAttributes();
		
		this.pageRange = this.buildCurrentPageRange();
		return this.pageRange.getLast() < this.pages;
	}
	
	public boolean isHavePreviousPageRange() {
		this.synchronizeAttributes();
		
		this.pageRange = this.buildCurrentPageRange();
		return this.pageRange.getFirst() > this.pageSize;
	}
	
	public boolean isHavePageIndex(int index) {
		this.synchronizeAttributes();
		return ((this.pages >= index) && (index > 0));
	}
	
	public Integer getNextPage() {
		this.synchronizeAttributes();
		if (this.isHaveNextPage()) {
			return this.pageIndex + 1;
		}
		
		return this.pageIndex;
	}
	
	public Integer getPreviousPage() {
		this.synchronizeAttributes();
		if (this.isHavePreviousPage()) {
			return this.pageIndex - 1;
		}
		
		return this.pageIndex;
	}
	
	public Integer getPreviousPageBlock() {
		if (this.isHavePreviousPageRange()) {
			return this.pageIndex - this.pageSize;
		} else {
			return this.pageIndex;
		}
	}
	
	public Integer getNextPageBlock() {
		if (this.isHaveNextPageRange()) {
			int page = this.pageIndex + this.pageSize;
			while (!this.isHavePageIndex(page)) {
				page--;
			}
			
			return page;
		} else {
			return this.pageIndex;
		}
	}
	
	public void synchronizeAttributes() {
		if (this.configured) {
			return;
		}
		
		this.pages = this.total / this.pageSize;
		this.pages = ((this.total % this.pageSize == 0) ? this.pages : (this.pages + 1));
		
		if ((this.pageIndex == null) || (this.pageIndex <= 0)) {
			this.pageIndex = 1;
		} else if (this.pageIndex > this.pages) {
			this.pageIndex = this.pages;
		}
		
		this.pageRange = this.buildCurrentPageRange();
	}
	
	private PageRange buildCurrentPageRange() {
		int min = this.pageIndex / this.pageSize;
		if ((this.pageIndex % this.pageSize) > 0) {
			min = (min == 0) ? 1 : (min * this.pageSize + 1);
		} else {
			min = min * this.pageSize - (this.pageSize - 1);
		}
		
		int max = (min + this.pageSize - 1);
		if (max > this.pages) {
			max = (max - (max - (this.pages)));
		}
		
		if (max < 1) {
			max = 1;
		}
		
		return new PageRange(min, max);
	}

	public Integer getPageSize() {
		return this.pageSize;
	}
	
	public DataPage withPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public Integer getPageIndex() {
		return this.pageIndex;
	}

	public DataPage withPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
		return this;
	}

	public Integer getPages() {
		this.synchronizeAttributes();
		return this.pages;
	}

	public PageRange getPageRange() {
		this.synchronizeAttributes();
		return this.pageRange;
	}
	
	public List<Object> getData() {
		return this.data;
	}
	
	public DataPage withData(List<Object> data) {
		this.data = data;
		return this;
	}
	
	public Integer getTotal() {
		this.synchronizeAttributes();
		return this.total;
	}
	
	public DataPage withTotal(Integer total) {
		this.total = total;
		return this;
	}
}