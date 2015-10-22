package com.github.aureliano.verbum_domini.web.mb;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.validation.ValidationException;

import org.apache.log4j.Logger;
import org.apache.myfaces.custom.fileupload.UploadedFile;

import com.github.aureliano.verbum_domini.core.bean.BookBean;
import com.github.aureliano.verbum_domini.core.impl.bean.BookBeanImpl;
import com.github.aureliano.verbum_domini.helper.UrlHelper;
import com.github.aureliano.verbum_domini.helper.WebHelper;
import com.github.aureliano.verbum_domini.web.SessionKey;
import com.github.aureliano.verbum_domini.web.bc.BibleBC;
import com.github.aureliano.verbum_domini.web.bc.BookBC;

@ManagedBean(name = "bookEditMB")
@ViewScoped
public class BookEditMB {

	private static final Logger logger = Logger.getLogger(BookEditMB.class);
	
	private BookBean book;
	private UploadedFile file;
	
	public BookEditMB() {
		super();
	}
	
	public void preRender() {
		Integer id = (Integer) WebHelper.getSessionAttribute("book.id");
		if (id == null) {
			id = WebHelper.getEntityIdFromRequest("book.id");
			WebHelper.setSessionAttribute("book.id", id);
		}
		
		logger.info("Preparing to edit book with id " + id);
		this.book = (id != null) ? BookBC.fetchBook(id) : new BookBeanImpl();
		
		if (this.book.getBible() == null) {
			id = WebHelper.getEntityIdFromRequest("bible.id");
			this.book.setBible(BibleBC.fetchBible(id));
		}
	}
	
	public void importFile() {
		StringBuilder log = new StringBuilder(" >>> Importing file ")
			.append(this.file.getName()).append(" Content type: ")
			.append(this.file.getContentType())
			.append(" File size: ").append(this.file.getSize());
		logger.info(log.toString());
		
		try {
			Integer id = BookBC.importBook(this.book.getBible(), this.file.getInputStream());
			WebHelper.setSessionAttribute(SessionKey.INFO_MESSAGE.name(), "Book imported successfuly.");
			logger.info("Book with id " + id + " imported successfuly.");
			
			WebHelper.setSessionAttribute("bible.id", this.book.getBible().getId());
			String url = UrlHelper.buildWebAppUrl("app/books");
			WebHelper.sendRedirect(url);
		} catch (IOException ex) {
			WebHelper.addMessageToContext(
				new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage()));
		}
	}
	
	public void save() {
		try {
			BookBC.defineEmptyValuesAsNull(this.book);
			BookBC.save(this.book);
			
			WebHelper.setSessionAttribute(SessionKey.INFO_MESSAGE.name(), "Book saved successfuly.");
			logger.info("Book with id " + this.book.getId() + " saved successfuly.");
			
			WebHelper.removeSessionAttribute("book.id");
			WebHelper.setSessionAttribute("bible.id", this.book.getBible().getId());
			String url = UrlHelper.buildWebAppUrl("app/books");
			WebHelper.sendRedirect(url);
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

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
}