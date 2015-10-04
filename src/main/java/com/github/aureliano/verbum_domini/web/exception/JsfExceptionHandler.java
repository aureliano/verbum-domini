package com.github.aureliano.verbum_domini.web.exception;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.log4j.Logger;

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
				logger.error(throwable.getMessage(), throwable);

				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, throwable.getMessage(), throwable.getMessage());
				fc.addMessage(null, message);
				
				fc.getExternalContext().dispatch("/error.xhtml");
				fc.renderResponse();
			} catch (IOException ex) {
				logger.fatal("Failed to forward to error page.", ex);
			} finally {
				iterator.remove();
			}
		}
		
		getWrapped().handle();
	}
}