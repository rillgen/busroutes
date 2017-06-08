package com.goeuro.bus.routes.exception;

/**
 * Thrown to indicate an error parsing the bus routes files
 * 
 * @author rillgen
 *
 */
public class RouteFileParsingException extends RuntimeException {
	private static final long serialVersionUID = -8522179026892225870L;

	public RouteFileParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	public RouteFileParsingException(String message) {
		super(message);
	}

	public RouteFileParsingException(Throwable cause) {
		super(cause);
	}

}
