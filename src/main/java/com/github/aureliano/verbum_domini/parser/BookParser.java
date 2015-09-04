package com.github.aureliano.verbum_domini.parser;

import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.model.Book;

public class BookParser implements IResourceParser<BookBean> {

	public BookParser() {
		super();
	}
	
	@Override
	public Book toResource(BookBean bean) {
		Integer bibleId = (bean.getBible() != null) ? bean.getBible().getId() : null;
		
		return new Book()
			.withBookId(bean.getId())
			.withBibleId(bibleId)
			.withName(bean.getName());
	}
}