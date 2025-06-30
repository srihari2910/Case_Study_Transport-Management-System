package com.hexaware.transportms.dao;

import com.hexaware.transportms.beans.Booking;
import com.hexaware.transportms.exception.*;

import java.util.List;

public interface BookingDAO {
    boolean bookTrip(int tripId, int passengerId, String bookingDate) throws DatabaseConnectionException;
    boolean cancelBooking(int bookingId) throws DatabaseConnectionException;
    List<Booking> getBookingsByPassenger(int passengerId) throws BookingNotFoundException, DatabaseConnectionException;
    List<Booking> getBookingsByTrip(int tripId) throws BookingNotFoundException, DatabaseConnectionException;
}
