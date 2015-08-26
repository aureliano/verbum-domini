package com.github.aureliano.verbum_domini.service;

import java.util.ArrayList;
import java.util.List;

import com.github.aureliano.verbum_domini.bean.BibleBean;
import com.github.aureliano.verbum_domini.bean.BookBean;
import com.github.aureliano.verbum_domini.dao.BibleDao;
import com.github.aureliano.verbum_domini.dao.BookDao;
import com.github.aureliano.verbum_domini.dao.Pagination;
import com.github.aureliano.verbum_domini.model.Book;
import com.github.aureliano.verbum_domini.model.Books;
import com.github.aureliano.verbum_domini.web.ServiceParams;

public final class BooksService {

	private BooksService() {
		super();
	}
	
	public static Books fetchBooksByBible(String id, Long start, Long pages) {
		if (!id.matches("\\d+")) {
			return null;
		}
		
		BookBean filter = createFilter(id);
		if (filter.getBible() == null) {
			return null;
		}
		
		Pagination<BookBean> beans = new BookDao()
			.list(filter, new ServiceParams().withStart(start).withPages(pages));
		
		List<Book> books = new ArrayList<Book>();
		
		for (BookBean bean : beans.getElements()) {
			books.add(bean.toResource());
		}
		
		return new Books().withBooks(books).withSize(beans.getSize());
	}
	
	public static Book fetchBookById(String id) {
		if (!id.matches("\\d+")) {
			return null;
		}
		
		BookBean book = new BookDao().get(Integer.parseInt(id));
		return (book == null) ? null : book.toResource();
	}
	
	private static BookBean createFilter(String id) {
		BibleBean bible = new BibleDao().get(Integer.parseInt(id));
		BookBean book = new BookBean();
		book.setBible(bible);
		
		return book;
	}
}