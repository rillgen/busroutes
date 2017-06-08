package com.goeuro.bus.routes;

import static com.goeuro.bus.routes.util.AppManager.createDirectRouteService;
import static com.goeuro.bus.routes.util.AppManager.createJsonResponseTransformer;
import static com.goeuro.bus.routes.util.AppManager.initializeWeb;
import static com.goeuro.bus.routes.util.AppManager.validate;
import static com.goeuro.bus.routes.util.SparkRequestUtils.intQueryParam;
import static spark.Spark.exception;
import static spark.Spark.get;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goeuro.bus.routes.exception.RequestValidationException;
import com.goeuro.bus.routes.model.BusRouteResponse;
import com.goeuro.bus.routes.service.DirectRouteService;

import spark.ResponseTransformer;

/**
 * Main class for Direct Bus Routes Service
 * 
 * @author rillgen
 *
 */
public class App {

	private static Logger LOGGER = LoggerFactory.getLogger(App.class);

	private static final String JSON_CONTENT_TYPE = "application/json";

	public static void main(String[] args) {
		// Preconditions
		validate(args);

		LOGGER.info("Starting up bus routes service...");

		// App infrastructure
		final DirectRouteService directRouteService = createDirectRouteService(args[0]);
		final ResponseTransformer toJSON = createJsonResponseTransformer();

		// Setup Web App
		initializeWeb();

		get("/api/direct", "application/json", (req, res) -> {
			LOGGER.info("Received direct bus route query");
			res.type(JSON_CONTENT_TYPE);
			
			int depSid = intQueryParam(req, "dep_sid");
			int arrSid = intQueryParam(req, "arr_sid");
			LOGGER.info("Checking direct connection between {} and {}",depSid, arrSid);
			
			return new BusRouteResponse(depSid, arrSid, directRouteService.hasDirectConection(depSid, arrSid));
		} , toJSON);

		exception(RequestValidationException.class, (exception, request, response) -> {
			response.status(400);
			response.body("Bad request - " + exception.getMessage());
		});

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			LOGGER.info("Shutting down service... bye!");
		}));

	}

}
