package com.hexaware.transportms.service;

import com.hexaware.transportms.beans.*;
import com.hexaware.transportms.dao.*;
import com.hexaware.transportms.exception.*;

import java.util.List;

public class TransportServiceImpl implements TransportService {

    private final VehicleDAO vehicleDAO = new VehicleDAOImpl();
    private final TripDAO tripDAO = new TripDAOImpl();
    private final BookingDAO bookingDAO = new BookingDAOImpl();
    private final PassengerDAO passengerDAO = new PassengerDAOImpl();
    private final DriverDAO driverDAO = new DriverDAOImpl();

    @Override
    public boolean addVehicle(Vehicle vehicle) throws DatabaseConnectionException {
        return vehicleDAO.addVehicle(vehicle);
    }

    @Override
    public boolean updateVehicle(Vehicle vehicle) throws VehicleNotFoundException, DatabaseConnectionException {
        return vehicleDAO.updateVehicle(vehicle);
    }

    @Override
    public boolean deleteVehicle(int vehicleId) throws VehicleNotFoundException, DatabaseConnectionException {
        return vehicleDAO.deleteVehicle(vehicleId);
    }

    @Override
    public boolean scheduleTrip(Trip trip) throws TripOverlapException, VehicleNotFoundException, DatabaseConnectionException {
        Vehicle vehicle = vehicleDAO.getVehicleById(trip.getVehicleId());
        if (vehicle == null || !"Available".equalsIgnoreCase(vehicle.getStatus())) {
            throw new VehicleNotFoundException("Vehicle not found or not available.");
        }

        List<Trip> allTrips = List.of();
        for (Trip t : allTrips) {
            if (t.getVehicleId() == trip.getVehicleId()) {
                boolean overlaps = !(trip.getArrivalDate().isBefore(t.getDepartureDate()) ||
                                     trip.getDepartureDate().isAfter(t.getArrivalDate()));
                if (overlaps) {
                    throw new TripOverlapException("Trip overlaps with another scheduled trip.");
                }
            }
        }

        return tripDAO.scheduleTrip(trip);
    }

    @Override
    public boolean cancelTrip(int tripId) throws DatabaseConnectionException {
        return tripDAO.cancelTrip(tripId);
    }

    @Override
    public boolean bookTrip(int tripId, int passengerId, String bookingDate) throws BookingNotFoundException, DatabaseConnectionException {
        Trip trip = tripDAO.getTripById(tripId);
        if (trip == null || !"Scheduled".equalsIgnoreCase(trip.getStatus())) {
            throw new BookingNotFoundException("Trip not found or not active.");
        }

        Passenger passenger = passengerDAO.getPassengerById(passengerId);
        if (passenger == null) {
            throw new BookingNotFoundException("Passenger not found.");
        }

        return bookingDAO.bookTrip(tripId, passengerId, bookingDate);
    }

    @Override
    public boolean cancelBooking(int bookingId) throws BookingNotFoundException, DatabaseConnectionException {
        return bookingDAO.cancelBooking(bookingId);
    }

    @Override
    public boolean allocateDriver(int tripId, int driverId) throws DriverNotAvailableException, DatabaseConnectionException {
        Driver driver = driverDAO.getDriverById(driverId);
        if (driver == null || !"Available".equalsIgnoreCase(driver.getStatus())) {
            throw new DriverNotAvailableException("Driver not available for allocation.");
        }

        return driverDAO.allocateDriver(tripId, driverId);
    }

    @Override
    public boolean deallocateDriver(int tripId) throws DatabaseConnectionException {
        return driverDAO.deallocateDriver(tripId);
    }

    @Override
    public List<Booking> getBookingsByPassenger(int passengerId) throws BookingNotFoundException, DatabaseConnectionException {
        return bookingDAO.getBookingsByPassenger(passengerId);
    }

    @Override
    public List<Booking> getBookingsByTrip(int tripId) throws BookingNotFoundException, DatabaseConnectionException {
        return bookingDAO.getBookingsByTrip(tripId);
    }

    @Override
    public List<Driver> getAvailableDrivers() throws DatabaseConnectionException {
        return driverDAO.getAvailableDrivers();
    }
}
