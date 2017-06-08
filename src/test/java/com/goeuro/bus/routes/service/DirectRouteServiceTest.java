package com.goeuro.bus.routes.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class DirectRouteServiceTest {
	
	private static final Map<Integer, Set<Integer>> TEST_DATASET = ImmutableMap.<Integer, Set<Integer>>builder().put(1, Sets.newHashSet(1,2,3))
			.put(2, Sets.newHashSet(1,2))
			.put(3, Sets.newHashSet(3))
			.put(4, Sets.newHashSet(5,6))
			.put(5, Sets.newHashSet(5))
			.put(6, Sets.newHashSet(6)).build();
	
	
	@Test
	public void testWithExistingStations() {
		//Given
		final DirectRouteService directRouteService = new DirectRouteService(TEST_DATASET);
		
		//Then
		assertTrue(directRouteService.hasDirectConection(1, 3));
		assertTrue(directRouteService.hasDirectConection(1, 2));
		assertTrue(directRouteService.hasDirectConection(6, 4));
		assertFalse(directRouteService.hasDirectConection(1, 5));
		assertFalse(directRouteService.hasDirectConection(6, 5));
		
	}
	
	@Test
	public void testWithNonExistingStations() {
		//Given
		final DirectRouteService directRouteService = new DirectRouteService(TEST_DATASET);
		
		//Then
		assertFalse(directRouteService.hasDirectConection(100, 5));
		assertFalse(directRouteService.hasDirectConection(5, 100));
		assertFalse(directRouteService.hasDirectConection(102, 100));		
	}
	
	@Test
	public void testWithEmptyDataset() {
		//Given
		final DirectRouteService directRouteService = new DirectRouteService(Maps.newHashMap());
		
		//Then
		assertFalse(directRouteService.hasDirectConection(1, 3));		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testWithPrecondition() {
		//Given
		new DirectRouteService(null);
		
		//Fail
	}
	

}
