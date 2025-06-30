package com.hexaware.transportms.service;

import com.hexaware.transportms.beans.*;
import com.hexaware.transportms.dao.UserDAO;
import com.hexaware.transportms.exception.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class TransportUIService {

    public static void registerUser(Scanner sc, UserDAO userDAO) {
        try {
            System.out.print("Choose a username: ");
            String username = sc.nextLine();
            System.out.print("Choose a password: ");
            String password = sc.nextLine();
            boolean result = userDAO.register(username, password);
            System.out.println(result ? "Registration successful." : "Registration failed.");
        } catch (DatabaseConnectionException e) {
            System.err.println("Registration error: " + e.getMessage());
        }
    }

    public static boolean loginUser(Scanner sc, UserDAO userDAO) {
        try {
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            System.out.print("Enter password: ");
            String password = sc.nextLine();
            boolean success = userDAO.validateUser(username, password);
            if (success) {
                System.out.println("Login successful.");
                return true;
            } else {
                System.out.println("Login failed. Try again.");
                return false;
            }
        } catch (DatabaseConnectionException e) {
            System.err.println("Login error: " + e.getMessage());
            return false;
        }
    }

 
    public static void addVehicle(Scanner sc, TransportService service) throws DatabaseConnectionException {
        System.out.print("Enter model: ");
        String model = sc.nextLine();
        System.out.print("Enter capacity: ");
        double capacity = sc.nextDouble(); sc.nextLine();
        System.out.print("Enter type: ");
        String type = sc.nextLine();
        System.out.print("Enter status: ");
        String status = sc.nextLine();

        boolean added = service.addVehicle(new Vehicle(0, model, capacity, type, status));
        System.out.println(added ? "Vehicle added successfully." : "Failed to add vehicle.");
    }

    public static void updateVehicle(Scanner sc, TransportService service) throws VehicleNotFoundException, DatabaseConnectionException {
        System.out.print("Enter vehicle ID to update: ");
        int id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter new model: ");
        String model = sc.nextLine();
        System.out.print("Enter new capacity: ");
        double capacity = sc.nextDouble(); sc.nextLine();
        System.out.print("Enter new type: ");
        String type = sc.nextLine();
        System.out.print("Enter new status: ");
        String status = sc.nextLine();

        boolean updated = service.updateVehicle(new Vehicle(id, model, capacity, type, status));
        System.out.println(updated ? "Vehicle updated successfully." : "Update failed.");
    }

    public static void deleteVehicle(Scanner sc, TransportService service) throws VehicleNotFoundException, DatabaseConnectionException {
        System.out.print("Enter vehicle ID to delete: ");
        int id = sc.nextInt();
        boolean deleted = service.deleteVehicle(id);
        System.out.println(deleted ? "Vehicle deleted." : "Delete failed.");
    }

    public static void scheduleTrip(Scanner sc, TransportService service) throws TripOverlapException, VehicleNotFoundException, DatabaseConnectionException {
        System.out.print("Vehicle ID: ");
        int vehicleId = sc.nextInt();
        System.out.print("Route ID: ");
        int routeId = sc.nextInt();
        sc.nextLine();
        System.out.print("Departure DateTime (yyyy-MM-ddTHH:mm): ");
        LocalDateTime dep = LocalDateTime.parse(sc.nextLine());
        System.out.print("Arrival DateTime (yyyy-MM-ddTHH:mm): ");
        LocalDateTime arr = LocalDateTime.parse(sc.nextLine());
        System.out.print("Trip Type (Freight/Passenger): ");
        String type = sc.nextLine();
        System.out.print("Max Passengers: ");
        int max = sc.nextInt();

        boolean ok = service.scheduleTrip(new Trip(0, vehicleId, routeId, dep, arr, "Scheduled", type, max));
        System.out.println(ok ? "Trip scheduled." : "Failed to schedule trip.");
    }

    public static void cancelTrip(Scanner sc, TransportService service) throws DatabaseConnectionException {
        System.out.print("Enter trip ID to cancel: ");
        int id = sc.nextInt();
        System.out.println(service.cancelTrip(id) ? "Trip cancelled." : "Cancel failed.");
    }

    public static void bookTrip(Scanner sc, TransportService service) throws BookingNotFoundException, DatabaseConnectionException {
        System.out.print("Trip ID: ");
        int tripId = sc.nextInt();
        System.out.print("Passenger ID: ");
        int passengerId = sc.nextInt();
        sc.nextLine();
        System.out.print("Booking DateTime (yyyy-MM-ddTHH:mm): ");
        String date = sc.nextLine();

        System.out.println(service.bookTrip(tripId, passengerId, date) ? "Booked." : "Booking failed.");
    }

    public static void cancelBooking(Scanner sc, TransportService service) throws BookingNotFoundException, DatabaseConnectionException {
        System.out.print("Enter booking ID to cancel: ");
        int id = sc.nextInt();
        System.out.println(service.cancelBooking(id) ? "Booking cancelled." : "Cancel failed.");
    }

    public static void allocateDriver(Scanner sc, TransportService service) throws DriverNotAvailableException, DatabaseConnectionException {
        System.out.print("Trip ID: ");
        int tripId = sc.nextInt();
        System.out.print("Driver ID: ");
        int driverId = sc.nextInt();

        System.out.println(service.allocateDriver(tripId, driverId) ? "Driver allocated." : "Allocation failed.");
    }

    public static void deallocateDriver(Scanner sc, TransportService service) throws DatabaseConnectionException {
        System.out.print("Trip ID: ");
        int tripId = sc.nextInt();
        System.out.println(service.deallocateDriver(tripId) ? "Driver deallocated." : "Deallocation failed.");
    }

    public static void getBookingsByPassenger(Scanner sc, TransportService service) throws BookingNotFoundException, DatabaseConnectionException {
        System.out.print("Enter passenger ID: ");
        int id = sc.nextInt();
        List<Booking> list = service.getBookingsByPassenger(id);
        list.forEach(System.out::println);
    }

    public static void getBookingsByTrip(Scanner sc, TransportService service) throws BookingNotFoundException, DatabaseConnectionException {
        System.out.print("Enter trip ID: ");
        int id = sc.nextInt();
        List<Booking> list = service.getBookingsByTrip(id);
        list.forEach(System.out::println);
    }

    public static void getAvailableDrivers(TransportService service) throws DatabaseConnectionException {
        List<Driver> list = service.getAvailableDrivers();
        list.forEach(System.out::println);
    }
}
