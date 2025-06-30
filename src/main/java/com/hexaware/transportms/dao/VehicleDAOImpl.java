package com.hexaware.transportms.dao;

import com.hexaware.transportms.beans.Vehicle;
import com.hexaware.transportms.exception.*;
import com.hexaware.transportms.util.DBConnUtil;

import java.sql.*;

public class VehicleDAOImpl implements VehicleDAO {

    @Override
    public boolean addVehicle(Vehicle vehicle) throws DatabaseConnectionException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnUtil.getConnection();
            if (con == null) throw new DatabaseConnectionException("Unable to establish database connection.");

            String sql = "INSERT INTO Vehicle (model, capacity, type, status) VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, vehicle.getModel());
            ps.setDouble(2, vehicle.getCapacity());
            ps.setString(3, vehicle.getType());
            ps.setString(4, vehicle.getStatus());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error while adding vehicle: " + e.getMessage());
        } finally {
            closeResources(ps, con);
        }
    }

    @Override
    public boolean updateVehicle(Vehicle vehicle) throws VehicleNotFoundException, DatabaseConnectionException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnUtil.getConnection();
            if (con == null) throw new DatabaseConnectionException("Unable to connect to database.");

            Vehicle existing = getVehicleById(vehicle.getVehicleId());
            if (existing == null) {
                throw new VehicleNotFoundException("Vehicle ID not found: " + vehicle.getVehicleId());
            }

            String sql = "UPDATE Vehicle SET model=?, capacity=?, type=?, status=? WHERE vehicleId=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, vehicle.getModel());
            ps.setDouble(2, vehicle.getCapacity());
            ps.setString(3, vehicle.getType());
            ps.setString(4, vehicle.getStatus());
            ps.setInt(5, vehicle.getVehicleId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error while updating vehicle: " + e.getMessage());
        } finally {
            closeResources(ps, con);
        }
    }

    @Override
    public boolean deleteVehicle(int vehicleId) throws VehicleNotFoundException, DatabaseConnectionException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnUtil.getConnection();
            if (con == null) throw new DatabaseConnectionException("Unable to connect to database.");

            Vehicle existing = getVehicleById(vehicleId);
            if (existing == null) {
                throw new VehicleNotFoundException("Vehicle ID not found: " + vehicleId);
            }

            String sql = "DELETE FROM Vehicle WHERE vehicleId=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, vehicleId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error while deleting vehicle: " + e.getMessage());
        } finally {
            closeResources(ps, con);
        }
    }

    @Override
    public Vehicle getVehicleById(int vehicleId) throws DatabaseConnectionException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnUtil.getConnection();
            if (con == null) throw new DatabaseConnectionException("Unable to connect to database.");

            String sql = "SELECT * FROM Vehicle WHERE vehicleId=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, vehicleId);
            rs = ps.executeQuery();

            if (rs.next()) {
                return new Vehicle(
                    rs.getInt("vehicleId"),
                    rs.getString("model"),
                    rs.getDouble("capacity"),
                    rs.getString("type"),
                    rs.getString("status")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error while fetching vehicle: " + e.getMessage());
        } finally {
            closeResources(rs, ps, con);
        }
    }

    private void closeResources(AutoCloseable... resources) {
        for (AutoCloseable r : resources) {
            try {
                if (r != null) r.close();
            } catch (Exception e) {
                System.err.println("Closing resource failed: " + e.getMessage());
            }
        }
    }
}
