package com.hexaware.transportms.dao;

import com.hexaware.transportms.beans.Booking;
import com.hexaware.transportms.exception.*;
import com.hexaware.transportms.util.DBConnUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    @Override
    public boolean bookTrip(int tripId, int passengerId, String bookingDate) throws DatabaseConnectionException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnUtil.getConnection();
            if (con == null) {
                throw new DatabaseConnectionException("Unable to connect to database.");
            }

            String sql = "INSERT INTO Booking (tripId, passengerId, bookingDate, status) VALUES (?, ?, ?, 'Confirmed')";
            ps = con.prepareStatement(sql);
            ps.setInt(1, tripId);
            ps.setInt(2, passengerId);
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.parse(bookingDate)));

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error booking trip: " + e.getMessage());
        } finally {
            closeResources(ps, con);
        }
    }

    @Override
    public boolean cancelBooking(int bookingId) throws DatabaseConnectionException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnUtil.getConnection();
            if (con == null) {
                throw new DatabaseConnectionException("Unable to connect to database.");
            }

            String sql = "UPDATE Booking SET status = 'Cancelled' WHERE bookingId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, bookingId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error cancelling booking: " + e.getMessage());
        } finally {
            closeResources(ps, con);
        }
    }

    @Override
    public List<Booking> getBookingsByPassenger(int passengerId) throws BookingNotFoundException, DatabaseConnectionException {
        List<Booking> bookings = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnUtil.getConnection();
            if (con == null) {
                throw new DatabaseConnectionException("Database connection failed.");
            }

            String sql = "SELECT * FROM Booking WHERE passengerId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, passengerId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking(
                    rs.getInt("bookingId"),
                    rs.getInt("tripId"),
                    rs.getInt("passengerId"),
                    rs.getTimestamp("bookingDate").toLocalDateTime(),
                    rs.getString("status")
                );
                bookings.add(booking);
            }

            if (bookings.isEmpty()) {
                throw new BookingNotFoundException("No bookings found for passenger ID: " + passengerId);
            }

            return bookings;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error retrieving bookings: " + e.getMessage());
        } finally {
            closeResources(rs, ps, con);
        }
    }

    @Override
    public List<Booking> getBookingsByTrip(int tripId) throws BookingNotFoundException, DatabaseConnectionException {
        List<Booking> bookings = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnUtil.getConnection();
            if (con == null) {
                throw new DatabaseConnectionException("Unable to connect to database.");
            }

            String sql = "SELECT * FROM Booking WHERE tripId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, tripId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking(
                    rs.getInt("bookingId"),
                    rs.getInt("tripId"),
                    rs.getInt("passengerId"),
                    rs.getTimestamp("bookingDate").toLocalDateTime(),
                    rs.getString("status")
                );
                bookings.add(booking);
            }

            if (bookings.isEmpty()) {
                throw new BookingNotFoundException("No bookings found for trip ID: " + tripId);
            }

            return bookings;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error retrieving bookings: " + e.getMessage());
        } finally {
            closeResources(rs, ps, con);
        }
    }

    private void closeResources(AutoCloseable... resources) {
        for (AutoCloseable res : resources) {
            try {
                if (res != null) res.close();
            } catch (Exception e) {
                System.err.println("Resource closing error: " + e.getMessage());
            }
        }
    }
}
