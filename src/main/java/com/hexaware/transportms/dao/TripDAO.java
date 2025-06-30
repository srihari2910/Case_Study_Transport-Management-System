package com.hexaware.transportms.dao;

import com.hexaware.transportms.beans.Trip;
import com.hexaware.transportms.exception.*;

public interface TripDAO {
    boolean scheduleTrip(Trip trip) throws DatabaseConnectionException;
    boolean cancelTrip(int tripId) throws DatabaseConnectionException;
    Trip getTripById(int tripId) throws DatabaseConnectionException;
}
