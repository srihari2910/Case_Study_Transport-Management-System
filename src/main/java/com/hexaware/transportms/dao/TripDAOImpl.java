package com.hexaware.transportms.dao;

import com.hexaware.transportms.beans.Trip;
import com.hexaware.transportms.exception.*;
import com.hexaware.transportms.util.DBConnUtil;

import java.sql.*;


public class TripDAOImpl implements TripDAO {

    @Override
    public boolean scheduleTrip(Trip trip) throws DatabaseConnectionException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnUtil.getConnection();
            if (con == null) {
                throw new DatabaseConnectionException("Database connection failed.");
            }

            String sql = "INSERT INTO Trip (vehicleId, routeId, departureDate, arrivalDate, status, tripType, maxPassengers) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, trip.getVehicleId());
            ps.setInt(2, trip.getRouteId());
            ps.setTimestamp(3, Timestamp.valueOf(trip.getDepartureDate()));
            ps.setTimestamp(4, Timestamp.valueOf(trip.getArrivalDate()));
            ps.setString(5, trip.getStatus());
            ps.setString(6, trip.getTripType());
            ps.setInt(7, trip.getMaxPassengers());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error scheduling trip: " + e.getMessage());
        } finally {
            closeResources(ps, con);
        }
    }

    @Override
    public boolean cancelTrip(int tripId) throws DatabaseConnectionException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnUtil.getConnection();
            if (con == null) {
                throw new DatabaseConnectionException("Unable to connect to database.");
            }

            String sql = "UPDATE Trip SET status = 'Cancelled' WHERE tripId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, tripId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error cancelling trip: " + e.getMessage());
        } finally {
            closeResources(ps, con);
        }
    }

    @Override
    public Trip getTripById(int tripId) throws DatabaseConnectionException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Trip trip = null;
        try {
            con = DBConnUtil.getConnection();
            if (con == null) {
                throw new DatabaseConnectionException("Database connection failed.");
            }

            String sql = "SELECT * FROM Trip WHERE tripId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, tripId);
            rs = ps.executeQuery();

            if (rs.next()) {
                trip = new Trip(
                    rs.getInt("tripId"),
                    rs.getInt("vehicleId"),
                    rs.getInt("routeId"),
                    rs.getTimestamp("departureDate").toLocalDateTime(),
                    rs.getTimestamp("arrivalDate").toLocalDateTime(),
                    rs.getString("status"),
                    rs.getString("tripType"),
                    rs.getInt("maxPassengers")
                );
            }

            return trip;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error retrieving trip: " + e.getMessage());
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
