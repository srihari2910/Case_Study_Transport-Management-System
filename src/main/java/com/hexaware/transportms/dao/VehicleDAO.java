package com.hexaware.transportms.dao;

import com.hexaware.transportms.beans.Vehicle;
import com.hexaware.transportms.exception.*;

public interface VehicleDAO {
    boolean addVehicle(Vehicle vehicle) throws DatabaseConnectionException;
    boolean updateVehicle(Vehicle vehicle) throws VehicleNotFoundException, DatabaseConnectionException;
    boolean deleteVehicle(int vehicleId) throws VehicleNotFoundException, DatabaseConnectionException;
    Vehicle getVehicleById(int vehicleId) throws DatabaseConnectionException;
}
