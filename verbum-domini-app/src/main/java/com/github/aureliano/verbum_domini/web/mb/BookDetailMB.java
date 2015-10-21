package com.github.aureliano.verbum_domini.web.mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.helper.WebHelper;
import com.github.aureliano.verbum_domini.web.NavigationViewKey;
import com.github.aureliano.verbum_domini.web.bc.BookBC;

@ManagedBean
@RequestScoped
public class BookDetailMB {

	private BookBean book;
	
	public BookDetailMB() {
		super();
	}
	
	public void preRender() {
		Integer id = WebHelper.getEntityIdFromRequest("book.id");
		this.book = BookBC.fetchBook(id);
	}
	
	public String edit() {
		return NavigationViewKey.BOOK_EDIT.name();
	}
	
	public BookBean getBook() {
		return book;
	}

	public void setBook(BookBean book) {
		this.book = book;
	}
}