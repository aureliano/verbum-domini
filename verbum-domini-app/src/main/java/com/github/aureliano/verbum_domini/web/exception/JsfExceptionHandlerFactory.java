package com.github.aureliano.verbum_domini.web.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class JsfExceptionHandlerFactory extends ExceptionHandlerFactory {

	private ExceptionHandlerFactory parent;
	
	public JsfExceptionHandlerFactory(ExceptionHandlerFactory factory) {
		this.parent = factory;
	}
	
	@Override
	public ExceptionHandler getExceptionHandler() {
		ExceptionHandler handler = new JsfExceptionHandler(parent.getExceptionHandler());
		return handler;
	}
}