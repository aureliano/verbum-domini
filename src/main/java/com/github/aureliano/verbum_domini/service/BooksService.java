package com.github.aureliano.verbum_domini.service;

import java.util.ArrayList;
import java.util.List;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.relational.bean.BookBeanImpl;
import com.github.aureliano.verbum_domini.core.relational.dao.DaoFactory;
import com.github.aureliano.verbum_domini.core.web.Pagination;
import com.github.aureliano.verbum_domini.core.web.ServiceParams;
import com.github.aureliano.verbum_domini.model.Book;
import com.github.aureliano.verbum_domini.model.Books;

public final class BooksService {

	private BooksService() {
		super();
	}
	
	public static Books fetchAll(Long start, Long pages) {
		Pagination<BookBean> beans = DaoFactory.createDao(BookBean.class)
				.list(new ServiceParams().withStart(start).withPages(pages));
		List<Book> books = new ArrayList<Book>();
		
		for (BookBean bean : beans.getElements()) {
			books.add(bean.toResource());
		}
		
		return new Books().withBooks(books).withSize(beans.getSize());
	}
	
	public static Books fetchBooksByBible(String id, Long start, Long pages) {
		if (!id.matches("\\d+")) {
			return null;
		}
		
		BookBean filter = createFilter(id);
		if (filter.getBible() == null) {
			return null;
		}
		
		Pagination<BookBean> beans = DaoFactory.createDao(BookBean.class)
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
		
		BookBean book = DaoFactory.createDao(BookBean.class).get(Integer.parseInt(id));
		return (book == null) ? null : book.toResource();
	}
	
	private static BookBean createFilter(String id) {
		BibleBean bible = DaoFactory.createDao(BibleBean.class).get(Integer.parseInt(id));
		BookBean book = new BookBeanImpl();
		book.setBible(bible);
		
		return book;
	}
}