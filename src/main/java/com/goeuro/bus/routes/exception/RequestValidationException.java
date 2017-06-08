package com.goeuro.bus.routes.exception;

/**
 * Thrown to indicate a web request validation error
 * 
 * @author rillgen
 *
 */
public class RequestValidationException extends RuntimeException {
	private static final long serialVersionUID = -6133342988240754581L;

	public RequestValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestValidationException(String message) {
		super(message);
	}

	public RequestValidationException(Throwable cause) {
		super(cause);
	}

}
