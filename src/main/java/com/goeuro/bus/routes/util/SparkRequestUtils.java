package com.goeuro.bus.routes.util;

import java.util.Optional;

import com.goeuro.bus.routes.exception.RequestValidationException;

import spark.Request;

/**
 * Utility class for request validation
 * 
 * @author rillgen
 *
 */
public class SparkRequestUtils {

	/**
	 * Validate a required integer query param
	 * 
	 * @param req Spark's web request abstraction
	 * @param param The parameter to read an validate
	 * @return a validated integer input parameter
	 * @throws RequestValidationException if the provided parameter is not valid
	 */
	public static int intQueryParam(Request req, String param) {
		try {
			return Optional.ofNullable(req.queryParams(param)).map(Integer::parseInt).orElseThrow(
					() -> new RequestValidationException("Missing required query param: \"" + param + "\""));
		} catch (NumberFormatException nfe) {
			throw new RequestValidationException("Query param \"" + param + "\" must be an integer");
		}
	}
}
