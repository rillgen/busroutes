package com.goeuro.bus.routes.exception;

/**
 * Thrown to indicate an unrecoverable issue while starting up the application
 * 
 * @author rillgen
 *
 */
public class InitializationException extends RuntimeException {
	private static final long serialVersionUID = -3174761422969869698L;

	public InitializationException(String message, Throwable cause) {
		super(message, cause);
	}

	public InitializationException(String message) {
		super(message);
	}

	public InitializationException(Throwable cause) {
		super(cause);
	}

}
