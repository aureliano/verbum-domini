package com.github.aureliano.verbum_domini.web.exception;

import java.util.Arrays;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.servlet.ServletRequest;

import org.apache.log4j.Logger;

import com.github.aureliano.verbum_domini.helper.UrlHelper;
import com.github.aureliano.verbum_domini.helper.WebHelper;
import com.github.aureliano.verbum_domini.web.SessionKey;

public class JsfExceptionHandler extends ExceptionHandlerWrapper {

	private static final Logger logger = Logger.getLogger(JsfExceptionHandler.class);
	private ExceptionHandler wrapped;
	
	public JsfExceptionHandler(ExceptionHandler exception) {
		this.wrapped = exception;
	}
    
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}
	
	@Override
	public void handle() throws FacesException {
		final Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();
		
		while (iterator.hasNext()) {
			ExceptionQueuedEvent event = iterator.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			Throwable throwable = context.getException();
			final FacesContext fc = FacesContext.getCurrentInstance();
			
			try {
				String message = throwable.getMessage();
				logger.error(message, throwable);
				WebHelper.setSessionAttribute(SessionKey.ERROR_MESSAGES.name(), Arrays.asList(message));
				
				ServletRequest request = (ServletRequest) fc.getExternalContext().getRequest();
				String url = UrlHelper.buildWebAppUrl(request, "error.xhtml");
				WebHelper.sendRedirect(url);
				fc.renderResponse();
			} finally {
				iterator.remove();
			}
		}
		
		getWrapped().handle();
	}
}