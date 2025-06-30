package com.hexaware.transportms.beans;

public class Vehicle {
    private int vehicleId;
    private String model;
    private double capacity;
    private String type;
    private String status;

    public Vehicle() {}

    public Vehicle(int vehicleId, String model, double capacity, String type, String status) {
        this.vehicleId = vehicleId;
        this.model = model;
        this.capacity = capacity;
        this.type = type;
        this.status = status;
    }

    public int getVehicleId() { return vehicleId; }
    public String getModel() { return model; }
    public double getCapacity() { return capacity; }
    public String getType() { return type; }
    public String getStatus() { return status; }

    public void setModel(String model) { this.model = model; }
    public void setCapacity(double capacity) { this.capacity = capacity; }
    public void setType(String type) { this.type = type; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return vehicleId + " | " + model + " | " + type + " | " + capacity + " | " + status;
    }
}
