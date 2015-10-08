package com.github.aureliano.verbum_domini.web.mb;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.validation.ValidationException;

import org.apache.log4j.Logger;

import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;
import com.github.aureliano.verbum_domini.helper.WebHelper;
import com.github.aureliano.verbum_domini.web.SessionKey;
import com.github.aureliano.verbum_domini.web.bc.BookBC;

@ManagedBean(name = "bookEditMB")
@ViewScoped
public class BookEditMB {

	private static final Logger logger = Logger.getLogger(BookEditMB.class);
	
	private BookBean book;
	
	public BookEditMB() {
		super();
	}
	
	@PostConstruct
	public void preRender() {
		Integer id = WebHelper.getEntityIdFromRequest("book.id");
		logger.info("Preparing to edit book with id " + id);
		this.book = (id != null) ? BookBC.fetchBook(id) : new BookBeanImpl();
	}
	
	public void save() {
		try {
			BookBC.save(this.book);
			WebHelper.setSessionAttribute(SessionKey.INFO_MESSAGE.name(), "Book saved successfuly.");
			logger.info("Book with id " + this.book.getId() + " saved successfuly.");
			
			WebHelper.sendRedirect("/verbumdomini/app/books/");
		} catch (ValidationException ex) {
			logger.warn("Book bean validation has failed.");
		}
	}

	public BookBean getBook() {
		return book;
	}

	public void setBook(BookBean book) {
		this.book = book;
	}
}