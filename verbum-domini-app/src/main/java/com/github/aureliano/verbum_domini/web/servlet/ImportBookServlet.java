package com.github.aureliano.verbum_domini.web.servlet;

import java.io.IOException;
import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import com.github.aureliano.verbum_domini.core.bean.BibleBean;
import com.github.aureliano.verbum_domini.helper.UrlHelper;
import com.github.aureliano.verbum_domini.helper.WebHelper;
import com.github.aureliano.verbum_domini.web.SessionKey;
import com.github.aureliano.verbum_domini.web.bc.BibleBC;
import com.github.aureliano.verbum_domini.web.bc.BookBC;

public class ImportBookServlet extends HttpServlet {

	private static final long serialVersionUID = 5801964570935489085L;
	private static final Logger logger = Logger.getLogger(ImportBookServlet.class);
	private static final Long FILE_MAX_SIZE = 460800L;

	public ImportBookServlet() {
		super();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Integer bibleId = this.getBibleId(req);
		if (bibleId == null) {
			throw new ServletException("Could not find bible.id attribute on request.");
		}
		
		Collection<Part> parts = req.getParts();
		this.validateData(parts);
		
		Part part = parts.iterator().next();
		this.validatePartData(part);
		
		StringBuilder log = new StringBuilder(" >>> Importing file ")
			.append(part.getName()).append(" Content type: ")
			.append(part.getContentType())
			.append(" File size: ").append(part.getSize());
		logger.info(log.toString());
		
		this.importBook(bibleId, part);
	}
	
	private Integer getBibleId(HttpServletRequest request) {
		String key = "bible.id";
		Object id = request.getAttribute(key);
		if (id == null) {
			id = request.getParameter(key);
		}
		
		return (id == null) ? null : Integer.parseInt(id.toString());
	}

	private void importBook(Integer bibleId, Part part) {
		BibleBean bible = BibleBC.fetchBible(bibleId);
		
		try {
			Integer id = BookBC.importBook(bible, part.getInputStream());
			WebHelper.setSessionAttribute(SessionKey.INFO_MESSAGE.name(), "Book imported successfuly.");
			logger.info("Book with id " + id + " imported successfuly.");
			
			WebHelper.setSessionAttribute("bible.id", bible.getId());
			String url = UrlHelper.buildWebAppUrl("app/books");
			WebHelper.sendRedirect(url);
		} catch (IOException ex) {
			WebHelper.addMessageToContext(
				new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage()));
		}
	}
	
	private void validateData(Collection<Part> parts) throws IOException {
		if (parts.isEmpty()) {
			throw new IOException("Request lacks multipart data.");
		} else if (parts.size() > 1) {
			throw new IOException("Found more than one multipart data on request. Not supported!");
		}
	}
	
	private void validatePartData(Part part) throws ServletException {
		if (!("application/json".equalsIgnoreCase(part.getContentType()) ||
				"text/json".equalsIgnoreCase(part.getContentType()))) {
			throw new ServletException("Accept only JSON file.");
		}
		
		if (FILE_MAX_SIZE < part.getSize()) {
			throw new ServletException("File must not be greater than " + (FILE_MAX_SIZE / 1024) + "KiB.");
		}
	}
}