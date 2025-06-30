package com.hexaware.transportms;

import com.hexaware.transportms.beans.*;
import com.hexaware.transportms.dao.*;
import com.hexaware.transportms.exception.*;
import com.hexaware.transportms.util.DBConnUtil;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest {

    static VehicleDAO vehicleDAO = new VehicleDAOImpl();
    static DriverDAO driverDAO = new DriverDAOImpl();
    static BookingDAO bookingDAO = new BookingDAOImpl();
    static PassengerDAO passengerDAO = new PassengerDAOImpl();
    static TripDAO tripDAO = new TripDAOImpl();

    static Connection testConnection;

    @BeforeEach
    public void startTransaction() throws SQLException {
        testConnection = DBConnUtil.getConnection();
        if (testConnection != null) {
            testConnection.setAutoCommit(false);
        }
    }

    @AfterEach
    public void rollbackTransaction() throws SQLException {
        if (testConnection != null) {
            testConnection.rollback();
            testConnection.close();
        }
    }

    @Test
    @Order(1)
    public void testAddVehicle() throws DatabaseConnectionException {
        Vehicle v = new Vehicle(0, "TestModel", 75, "Passenger", "Available");
        boolean result = vehicleDAO.addVehicle(v);
        assertTrue(result, "Vehicle should be added successfully");
    }

    @Test
    @Order(2)
    public void testGetAvailableDrivers() throws DatabaseConnectionException {
        List<Driver> availableDrivers = driverDAO.getAvailableDrivers();
        assertNotNull(availableDrivers);
        assertTrue(availableDrivers.size() > 0, "At least one driver should be available");
    }

    @Test
    @Order(3)
    public void testGetPassengerById() throws DatabaseConnectionException {
        Passenger p = passengerDAO.getPassengerById(1);
        assertNotNull(p, "Passenger with ID 1 should exist");
    }

    @Test
    @Order(4)
    public void testGetTripById() throws DatabaseConnectionException {
        Trip t = tripDAO.getTripById(1);
        assertNotNull(t, "Trip with ID 1 should exist");
    }

    @Test
    @Order(5)
    public void testBookTripSuccess() throws DatabaseConnectionException {
        boolean result = bookingDAO.bookTrip(1, 1, "2025-06-28T12:00");
        assertTrue(result, "Trip booking should be successful");
    }
    
}
