package com.goeuro.bus.routes.util;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.goeuro.bus.routes.exception.InitializationException;
import com.goeuro.bus.routes.service.DirectRouteService;
import com.google.common.base.Preconditions;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import spark.ResponseTransformer;
import spark.Spark;

/**
 * Helper class to initialize the application infrastructure
 * 
 * @author rillgen
 *
 */
public class AppManager {

	/**
	 * Validates application arguments
	 * 
	 * @param args the arguments pased to the main class
	 */
	public static void validate(String[] args) {
		Preconditions.checkArgument(args.length == 1, "Please provide a bus routes file");
	}

	/**
	 * Web server initialization code
	 * 
	 */
	public static void initializeWeb() {
		// Server configuration
		Spark.port(8088);
	}

	/**
	 * Returns a Spark compliant ResponseTransformer to serialize Json Responses
	 * 
	 * @return spark json <code>ResponseTransformer</code>
	 */
	public static ResponseTransformer createJsonResponseTransformer() {
		final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.create();
		return gson::toJson;
	}

	/**
	 * Creates a <code>DirectRouteService</code> using the provided route file
	 * 
	 * @param routeFilePath
	 *            the path where the route file is located
	 * @return an initialized DirectRouteService
	 */
	public static DirectRouteService createDirectRouteService(String routeFilePath) {
		Path routeFile = Paths.get(routeFilePath);
		try (BufferedReader reader = Files.newBufferedReader(routeFile)) {
			RouteFileParser parser = new RouteFileParser(reader);
			return new DirectRouteService(parser.parse());
		} catch (Exception e) {
			throw new InitializationException("Error reading routes file", e);
		}
	}

}
