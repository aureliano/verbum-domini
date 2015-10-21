package com.github.aureliano.verbum_domini.core.exception;

public class VerbumDominiException extends RuntimeException {

	private static final long serialVersionUID = -301093296630058559L;

	public VerbumDominiException() {
		super();
	}
	
	public VerbumDominiException(String message) {
		super(message);
	}
	
	public VerbumDominiException(Throwable throwable) {
		super(throwable);
	}
	
	public VerbumDominiException(String message, Throwable throwable) {
		super(message, throwable);
	}
}