package com.hexaware.transportms.dao;

import com.hexaware.transportms.beans.Driver;
import com.hexaware.transportms.exception.*;

import java.util.List;

public interface DriverDAO {
    Driver getDriverById(int driverId) throws DatabaseConnectionException;

    boolean allocateDriver(int tripId, int driverId) throws DatabaseConnectionException;

    boolean deallocateDriver(int tripId) throws DatabaseConnectionException;

    List<Driver> getAvailableDrivers() throws DatabaseConnectionException;
}
