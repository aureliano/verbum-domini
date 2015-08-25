package com.github.aureliano.verbum_domini.dao.helper;

import com.github.aureliano.verbum_domini.bean.BibleBean;
import com.github.aureliano.verbum_domini.bean.BookBean;

public final class BookDataHelper {

	private BookDataHelper() {
		super();
	}
	
	public static void createBooks() {
		
	}
	
	private static BookBean createBook(Integer id, BibleBean bible) {
		BookBean book = new BookBean();
		
		book.setId(id);
		book.setName(name)
		
		return book;
	}
}