package com.hexaware.transportms.beans;

public class Driver {
    private int driverId;
    private String name;
    private String licenseNumber;
    private String contact;
    private String status;

    public Driver() {}

    public Driver(int driverId, String name, String licenseNumber, String contact, String status) {
        this.driverId = driverId;
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.contact = contact;
        this.status = status;
    }

    public int getDriverId() { return driverId; }
    public String getName() { return name; }
    public String getLicenseNumber() { return licenseNumber; }
    public String getContact() { return contact; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return driverId + " | " + name + " | " + licenseNumber + " | " + contact + " | " + status;
    }
}
