package com.hexaware.transportms.main;

import com.hexaware.transportms.dao.UserDAO;
import com.hexaware.transportms.dao.UserDAOImpl;
import com.hexaware.transportms.service.TransportService;
import com.hexaware.transportms.service.TransportServiceImpl;
import com.hexaware.transportms.service.TransportUIService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TransportService service = new TransportServiceImpl();
        UserDAO userDAO = new UserDAOImpl();
        boolean loginSuccess = false;

        System.out.println("Welcome to Transport Management System");
        while (!loginSuccess) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            try {
                if (sc.hasNextInt()) {
                    int choice = sc.nextInt();
                    sc.nextLine();
                    
                    switch (choice) {
                        case 1:
                            TransportUIService.registerUser(sc, userDAO);
                            break;
                        case 2:
                            loginSuccess = TransportUIService.loginUser(sc, userDAO);
                            break;
                        case 3:
                            System.out.println("Thank you...!");
                            System.exit(0);
                            sc.close();
                            return;
                        default:
                            System.out.println("Invalid choice. Try again.");
                    }
                } else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException ime) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            } catch (Exception e) {
                String msg = e.getMessage();
                System.out.println("Unexpected error: " + (msg != null ? msg : "Invalid input or operation."));
                sc.nextLine();
            }
        }

        int option = -1;
        do {
            try {
                System.out.println("\nTransport Management Menu");
                System.out.println("1. Add Vehicle");
                System.out.println("2. Update Vehicle");
                System.out.println("3. Delete Vehicle");
                System.out.println("4. Schedule Trip");
                System.out.println("5. Cancel Trip");
                System.out.println("6. Book Trip");
                System.out.println("7. Cancel Booking");
                System.out.println("8. Allocate Driver");
                System.out.println("9. Deallocate Driver");
                System.out.println("10. View Bookings by Passenger");
                System.out.println("11. View Bookings by Trip");
                System.out.println("12. View Available Drivers");
                System.out.println("13. Exit");
                System.out.print("Enter your choice: ");

                if (sc.hasNextInt()) {
                    option = sc.nextInt();
                    sc.nextLine();

                    switch (option) {
                        case 1:
                            TransportUIService.addVehicle(sc, service);
                            break;
                        case 2:
                            TransportUIService.updateVehicle(sc, service);
                            break;
                        case 3:
                            TransportUIService.deleteVehicle(sc, service);
                            break;
                        case 4:
                            TransportUIService.scheduleTrip(sc, service);
                            break;
                        case 5:
                            TransportUIService.cancelTrip(sc, service);
                            break;
                        case 6:
                            TransportUIService.bookTrip(sc, service);
                            break;
                        case 7:
                            TransportUIService.cancelBooking(sc, service);
                            break;
                        case 8:
                            TransportUIService.allocateDriver(sc, service);
                            break;
                        case 9:
                            TransportUIService.deallocateDriver(sc, service);
                            break;
                        case 10:
                            TransportUIService.getBookingsByPassenger(sc, service);
                            break;
                        case 11:
                            TransportUIService.getBookingsByTrip(sc, service);
                            break;
                        case 12:
                            TransportUIService.getAvailableDrivers(service);
                            break;
                        case 13:
                            System.out.println("Logging out");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid option. Try again.");
                    }
                } else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException ime) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine();
            } catch (Exception e) {
                String msg = e.getMessage();
                System.out.println("Error: " + (msg != null ? msg : "Invalid input or operation."));
                sc.nextLine();
            }
        } while (option != 0);

        sc.close();
    }
}
