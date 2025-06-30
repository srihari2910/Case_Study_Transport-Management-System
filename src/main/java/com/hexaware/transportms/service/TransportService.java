package com.hexaware.transportms.service;

import com.hexaware.transportms.beans.*;
import com.hexaware.transportms.exception.*;

import java.util.List;

public interface TransportService {
    boolean addVehicle(Vehicle vehicle) throws DatabaseConnectionException;

    boolean updateVehicle(Vehicle vehicle) throws VehicleNotFoundException, DatabaseConnectionException;

    boolean deleteVehicle(int vehicleId) throws VehicleNotFoundException, DatabaseConnectionException;

    boolean scheduleTrip(Trip trip) throws TripOverlapException, VehicleNotFoundException, DatabaseConnectionException;

    boolean cancelTrip(int tripId) throws DatabaseConnectionException;

    boolean bookTrip(int tripId, int passengerId, String bookingDate) throws BookingNotFoundException, DatabaseConnectionException;

    boolean cancelBooking(int bookingId) throws BookingNotFoundException, DatabaseConnectionException;

    boolean allocateDriver(int tripId, int driverId) throws DriverNotAvailableException, DatabaseConnectionException;

    boolean deallocateDriver(int tripId) throws DatabaseConnectionException;

    List<Booking> getBookingsByPassenger(int passengerId) throws BookingNotFoundException, DatabaseConnectionException;

    List<Booking> getBookingsByTrip(int tripId) throws BookingNotFoundException, DatabaseConnectionException;

    List<Driver> getAvailableDrivers() throws DatabaseConnectionException;
}
