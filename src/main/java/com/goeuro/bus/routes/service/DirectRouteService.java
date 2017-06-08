package com.goeuro.bus.routes.service;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * Given a map from station id to a set of bus routes determines if there is a
 * direct connection between 2 stations
 * 
 * @author rillgen
 *
 */
public class DirectRouteService {

	final Map<Integer, Set<Integer>> stationToRoutesMap;

	public DirectRouteService(Map<Integer, Set<Integer>> stationToRoutesMap) {
		Preconditions.checkArgument(stationToRoutesMap != null, "stationToRoutesMap can not be null");
		this.stationToRoutesMap = stationToRoutesMap;
	}

	/**
	 * Given 2 station ids indicate if there is a direct connection
	 * 
	 * If any or both of the stations are not on the provided data, returns false.  
	 * 
	 * @param depSid departure station id
	 * @param arrSid arrival station id
	 * @return boolean indicating if there is a direct connection
	 */
	public boolean hasDirectConection(int depSid, int arrSid) {
		if (!stationToRoutesMap.containsKey(depSid) || !stationToRoutesMap.containsKey(arrSid)) {
			return false;
		}

		// Optimization: Iterate over the smallest set of data
		final Set<Integer>[] sortedSets = sortBySize(stationToRoutesMap.get(depSid), stationToRoutesMap.get(arrSid));
		// Check if the same bus line goes through both stations
		return sortedSets[0].stream().anyMatch(bus -> sortedSets[1].contains(bus));
	}

	@SafeVarargs
	final private Set<Integer>[] sortBySize(Set<Integer>... sets) {
		Arrays.sort(sets, (a, b) -> Integer.compare(a.size(), b.size()));
		return sets;
	}

}
