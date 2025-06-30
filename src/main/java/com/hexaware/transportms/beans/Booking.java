package com.hexaware.transportms.beans;

import java.time.LocalDateTime;

public class Booking {
    private int bookingId;
    private int tripId;
    private int passengerId;
    private LocalDateTime bookingDate;
    private String status;

    public Booking() {}

    public Booking(int bookingId, int tripId, int passengerId, LocalDateTime bookingDate, String status) {
        this.bookingId = bookingId;
        this.tripId = tripId;
        this.passengerId = passengerId;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    public int getBookingId() { return bookingId; }
    public int getTripId() { return tripId; }
    public int getPassengerId() { return passengerId; }
    public LocalDateTime getBookingDate() { return bookingDate; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return bookingId + " | Trip: " + tripId + " | Passenger: " + passengerId +
               " | " + bookingDate + " | " + status;
    }
}
