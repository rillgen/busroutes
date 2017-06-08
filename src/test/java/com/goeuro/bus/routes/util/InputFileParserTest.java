package com.goeuro.bus.routes.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.goeuro.bus.routes.exception.RouteFileParsingException;
import com.google.common.collect.Sets;

public class InputFileParserTest {

	
//	3
//	0 0 1 2 3 4
//	1 3 1 6 5
//	2 0 6 4
	@Test
	public void testCompliantFile() throws Exception {
		//given ac ompliant file
		final RouteFileParser parser = new RouteFileParser(readerof("input1.txt"));
		
		//when
		Map<Integer, Set<Integer>> stationsToRoutes = parser.parse();
		
		//then
		assertEquals(stationsToRoutes.get(0), Sets.newHashSet(0,2));
		assertEquals(stationsToRoutes.get(1), Sets.newHashSet(0,1));
		assertEquals(stationsToRoutes.get(2), Sets.newHashSet(0));
		assertEquals(stationsToRoutes.get(3), Sets.newHashSet(0,1));
		assertEquals(stationsToRoutes.get(4), Sets.newHashSet(0,2));
		assertEquals(stationsToRoutes.get(5), Sets.newHashSet(1));
		assertEquals(stationsToRoutes.get(6), Sets.newHashSet(1,2));
		assertFalse(stationsToRoutes.containsKey(7));
	}

	@Test(expected=RouteFileParsingException.class)
	public void testGibberishFileError() throws Exception {
		//given a file with gibberish text
		final RouteFileParser parser = new RouteFileParser(readerof("invalidinput1.txt"));
		//when
		parser.parse();
		//fail
	}
	
	@Test(expected=RouteFileParsingException.class)
	public void testInvalidDataLineError() throws Exception {
		//given a file with invalid route data
		final RouteFileParser parser = new RouteFileParser(readerof("invalidinput2.txt"));
		//when
		parser.parse();
		//fail
	}
	
	@Test(expected=RouteFileParsingException.class)
	public void testIncompleteFileError() throws Exception {
		//given a bus routes file with fewer data lines than expected
		final RouteFileParser parser = new RouteFileParser(readerof("invalidinput3.txt"));
		//when
		parser.parse();
		//fail
	}
	
	@Test(expected=RouteFileParsingException.class)
	public void testIncompleteBusRouteError() throws Exception {
		//given a bus routes file with a data line with less than 3 elements
		final RouteFileParser parser = new RouteFileParser(readerof("invalidinput4.txt"));
		//when
		parser.parse();
		//fail
	}
	
	@Test(expected=RouteFileParsingException.class)
	public void testEmptyFile() throws Exception {
		//given an empty file
		final RouteFileParser parser = new RouteFileParser(readerof("emptyfile.txt"));
		//when
		parser.parse();
		//fail
	}

	private static BufferedReader readerof(String file) throws UnsupportedEncodingException {
		return new BufferedReader(new InputStreamReader(InputFileParserTest.class.getResourceAsStream(file), "UTF-8"));
	}

}
