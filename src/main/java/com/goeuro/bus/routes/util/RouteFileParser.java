package com.goeuro.bus.routes.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goeuro.bus.routes.exception.RouteFileParsingException;

/**
 * Contains the parsing logic for the bus routes file
 * 
 * @author rillgen
 *
 */
public class RouteFileParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(RouteFileParser.class);

	final BufferedReader reader;

	public RouteFileParser(BufferedReader reader) {
		this.reader = reader;
	}

	/**
	 * Parse file from the supplied reader into a Map that relates the station
	 * ids to bus routes
	 * 
	 * @return the resulting map from the parse process
	 * @throws RouteFileParsingException
	 *             if there is an issue parsing the file
	 */
	public Map<Integer, Set<Integer>> parse() {
		int expectedEntries;
		try {
			expectedEntries = Integer.parseInt(reader.readLine());
		} catch (NumberFormatException nfe) {
			throw new RouteFileParsingException("Could not parse routes file: Expected number at first line", nfe);
		} catch (IOException ioe) {
			throw new RouteFileParsingException("Could not parse routes file: Could not read input", ioe);
		}

		LOGGER.info("Parsing " + expectedEntries + " bus routes");

		final Map<Integer, Set<Integer>> stationToRoutesMap = new HashMap<>();

		final AtomicInteger recordCount = new AtomicInteger(0);

		reader.lines().forEach(line -> {
			int currentLine = recordCount.incrementAndGet() + 1;

			Integer[] values;

			try {
				values = Stream.of(line.split(" ")).map(Integer::parseInt).collect(Collectors.toList())
						.toArray(new Integer[0]);
			} catch (NumberFormatException nfe) {
				throw new RouteFileParsingException("Could not parse routes file at line " + currentLine
						+ ": data line \"" + line + "\" not compliant with format", nfe);
			}

			if (values.length < 3) {
				throw new RouteFileParsingException("Could not parse routes file at line " + currentLine
						+ ": Expected at least 3 integers, but found " + values.length);
			}

			final int routeId = values[0];

			for (int idx = 1; idx < values.length; idx++) {
				int stationId = values[idx];
				if (!stationToRoutesMap.containsKey(stationId)) {
					stationToRoutesMap.put(stationId, new HashSet<>());
				}
				stationToRoutesMap.get(stationId).add(routeId);
			}
		});

		if (recordCount.get() < expectedEntries) {
			throw new RouteFileParsingException("Error reading input file: Expected " + expectedEntries
					+ " bus routes, but found " + recordCount.get());
		}

		return stationToRoutesMap;
	}

}
