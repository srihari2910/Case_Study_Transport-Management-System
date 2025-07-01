CREATE DATABASE transportdb;
USE transportdb;
select * from vehicle;
select * from trip;
select * from driver;
select * from route;
select * from passenger;
select * from booking;


CREATE TABLE Vehicle (
    vehicleId INT AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(50) NOT NULL,
    capacity DOUBLE NOT NULL,
    type VARCHAR(30) NOT NULL,
    status VARCHAR(20) DEFAULT 'Available'
);

CREATE TABLE Driver (
    driverId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    licenseNumber VARCHAR(30) NOT NULL,
    contact VARCHAR(20),
    status VARCHAR(20) DEFAULT 'Available'
);

CREATE TABLE Route (
    routeId INT AUTO_INCREMENT PRIMARY KEY,
    source VARCHAR(50) NOT NULL,
    destination VARCHAR(50) NOT NULL,
    distance DOUBLE
);

CREATE TABLE Passenger (
    passengerId INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    gender VARCHAR(10),
    age INT,
    email VARCHAR(50),
    phoneNumber VARCHAR(20)
);

CREATE TABLE Trip (
    tripId INT AUTO_INCREMENT PRIMARY KEY,
    vehicleId INT NOT NULL,
    routeId INT NOT NULL,
    driverId INT,
    departureDate DATETIME NOT NULL,
    arrivalDate DATETIME NOT NULL,
    status VARCHAR(20) DEFAULT 'Scheduled',
    tripType VARCHAR(20),
    maxPassengers INT,
    FOREIGN KEY (vehicleId) REFERENCES Vehicle(vehicleId) ON DELETE CASCADE,
    FOREIGN KEY (routeId) REFERENCES Route(routeId) ON DELETE CASCADE,
    FOREIGN KEY (driverId) REFERENCES Driver(driverId) ON DELETE SET NULL
);

CREATE TABLE Booking (
    bookingId INT AUTO_INCREMENT PRIMARY KEY,
    tripId INT NOT NULL,
    passengerId INT NOT NULL,
    bookingDate DATETIME NOT NULL,
    status VARCHAR(20) DEFAULT 'Confirmed',
    FOREIGN KEY (tripId) REFERENCES Trip(tripId) ON DELETE CASCADE,
    FOREIGN KEY (passengerId) REFERENCES Passenger(passengerId) ON DELETE CASCADE
);
CREATE TABLE User (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL
);
INSERT INTO Vehicle (model, capacity, type, status) VALUES
('Volvo X2', 50, 'Passenger', 'Available'),
('Ashok Leyland', 100, 'Freight', 'Available');

INSERT INTO Driver (name, licenseNumber, contact, status) VALUES
('hari', 'DL123456', '9954756569', 'Available'),
('aswin', 'DL789101', '8855667879', 'Available');

INSERT INTO Route (source, destination, distance) VALUES
('Chennai', 'Bangalore', 345),
('Mumbai', 'Pune', 150);

INSERT INTO Passenger (firstName, gender, age, email, phoneNumber) VALUES
('vijay', 'Male', 28, 'vijay@example.com', '9876543210'),
('vishnu', 'Male', 32, 'vishnu@example.com', '9765432109');

INSERT INTO Trip (vehicleId, routeId, departureDate, arrivalDate, status, tripType, maxPassengers) VALUES
(1, 1, '2025-07-01 10:00:00', '2025-07-01 16:00:00', 'Scheduled', 'Passenger', 50),
(2, 2, '2025-07-03 06:00:00', '2025-07-03 09:00:00', 'Scheduled', 'Freight', 0);

INSERT INTO Booking (tripId, passengerId, bookingDate, status) VALUES
(1, 1, '2025-06-25 15:00:00', 'Confirmed'),
(1, 2, '2025-06-26 10:00:00', 'Confirmed');

INSERT INTO User (username, password) VALUES ('admin', 'admin123');


SELECT * FROM Vehicles
WHERE Status = 'Available';

SELECT * FROM Trips
WHERE DepartureDate BETWEEN '2025-07-01' AND '2025-07-10';

SELECT b.BookingID, t.TripID, t.DepartureDate, t.ArrivalDate, b.Status
FROM Bookings b
JOIN Passengers p ON b.PassengerID = p.PassengerID
JOIN Trips t ON b.TripID = t.TripID
WHERE p.Email = 'vishnu@gmail.com';

SELECT TripID, COUNT(*) AS TotalBookings
FROM Bookings
GROUP BY TripID;

SELECT TripID, COUNT(*) AS BookedSeats
FROM Bookings
GROUP BY TripID
HAVING COUNT(*) > 1;

SELECT * FROM Passengers
WHERE FirstName LIKE '%a%';

SELECT b.BookingID, p.FirstName, t.TripID, t.DepartureDate, b.Status
FROM Bookings b
JOIN Passengers p ON b.PassengerID = p.PassengerID
JOIN Trips t ON b.TripID = t.TripID
ORDER BY t.DepartureDate;

SELECT * FROM Trips
WHERE TripID NOT IN (SELECT TripID FROM Bookings);

SELECT * FROM Routes
ORDER BY Distance DESC
LIMIT 1;

