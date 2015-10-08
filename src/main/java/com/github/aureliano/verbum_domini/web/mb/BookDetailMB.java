package com.github.aureliano.verbum_domini.web.mb;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.helper.WebHelper;
import com.github.aureliano.verbum_domini.web.bc.BookBC;

@ManagedBean
@RequestScoped
public class BookDetailMB {

	private BookBean book;
	
	public BookDetailMB() {
		super();
	}
	
	@PostConstruct
	public void preRender() {
		Integer id = WebHelper.getEntityIdFromRequest("book.id");
		this.book = BookBC.fetchBook(id);
		
		System.out.println(" => BOOK: " + book.getId());
		System.out.println(" => BIBLE: " + book.getBible().getId() + " - " + book.getBible().getName());
	}
	
	public BookBean getBook() {
		return book;
	}

	public void setBook(BookBean book) {
		this.book = book;
	}
}