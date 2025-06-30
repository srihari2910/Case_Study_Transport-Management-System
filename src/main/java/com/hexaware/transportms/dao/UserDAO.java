package com.hexaware.transportms.dao;

import com.hexaware.transportms.exception.DatabaseConnectionException;

public interface UserDAO {
    boolean register(String username, String password) throws DatabaseConnectionException;
    boolean validateUser(String username, String password) throws DatabaseConnectionException;
}
