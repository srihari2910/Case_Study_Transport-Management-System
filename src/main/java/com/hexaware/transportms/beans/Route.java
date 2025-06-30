package com.hexaware.transportms.beans;

public class Route {
    private int routeId;
    private String source;
    private String destination;
    private double distance;

    public Route() {}

    public Route(int routeId, String source, String destination, double distance) {
        this.routeId = routeId;
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    public int getRouteId() { return routeId; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public double getDistance() { return distance; }

    @Override
    public String toString() {
        return routeId + " | " + source + " → " + destination + " | " + distance + " km";
    }
}
