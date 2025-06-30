package com.hexaware.transportms.dao;

import com.hexaware.transportms.beans.Passenger;
import com.hexaware.transportms.exception.*;

public interface PassengerDAO {
    Passenger getPassengerById(int passengerId) throws DatabaseConnectionException;
}
