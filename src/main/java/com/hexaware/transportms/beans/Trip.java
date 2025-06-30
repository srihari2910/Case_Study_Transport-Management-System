package com.hexaware.transportms.beans;

import java.time.LocalDateTime;

public class Trip {
    private int tripId;
    private int vehicleId;
    private int routeId;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private String status;
    private String tripType;
    private int maxPassengers;

    public Trip() {}

    public Trip(int tripId, int vehicleId, int routeId, LocalDateTime departureDate,
                LocalDateTime arrivalDate, String status, String tripType, int maxPassengers) {
        this.tripId = tripId;
        this.vehicleId = vehicleId;
        this.routeId = routeId;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.status = status;
        this.tripType = tripType;
        this.maxPassengers = maxPassengers;
    }

    public int getTripId() { return tripId; }
    public int getVehicleId() { return vehicleId; }
    public int getRouteId() { return routeId; }
    public LocalDateTime getDepartureDate() { return departureDate; }
    public LocalDateTime getArrivalDate() { return arrivalDate; }
    public String getStatus() { return status; }
    public String getTripType() { return tripType; }
    public int getMaxPassengers() { return maxPassengers; }

    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return tripId + " | Vehicle: " + vehicleId + " | Route: " + routeId +
               " | From: " + departureDate + " To: " + arrivalDate +
               " | " + tripType + " | Max: " + maxPassengers + " | " + status;
    }
}
