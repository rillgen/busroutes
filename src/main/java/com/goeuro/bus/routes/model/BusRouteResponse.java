package com.goeuro.bus.routes.model;

/**
 * Response model for the direct bus route service
 * 
 * @author rillgen
 *
 */
public class BusRouteResponse {
	private final int depSid;
	private final int arrSid;
	private final boolean directBusRoute;
	
	public BusRouteResponse(int depSid, int arrSid, boolean directoBusRoute) {
		this.depSid = depSid;
		this.arrSid = arrSid;
		this.directBusRoute = directoBusRoute;
	}
	
	public int getDepSid() {
		return depSid;
	}

	public int getArrSid() {
		return arrSid;
	}

	public boolean isDirectBusRoute() {
		return directBusRoute;
	}

}
