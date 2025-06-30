package com.hexaware.transportms.dao;

import com.hexaware.transportms.beans.Driver;
import com.hexaware.transportms.exception.*;
import com.hexaware.transportms.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverDAOImpl implements DriverDAO {

    @Override
    public Driver getDriverById(int driverId) throws DatabaseConnectionException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnUtil.getConnection();
            if (con == null) throw new DatabaseConnectionException("Database connection failed.");

            String sql = "SELECT * FROM Driver WHERE driverId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, driverId);
            rs = ps.executeQuery();

            if (rs.next()) {
                return new Driver(
                    rs.getInt("driverId"),
                    rs.getString("name"),
                    rs.getString("licenseNumber"),
                    rs.getString("contact"),
                    rs.getString("status")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to fetch driver: " + e.getMessage());
        } finally {
            closeResources(rs, ps, con);
        }
    }

    @Override
    public boolean allocateDriver(int tripId, int driverId) throws DatabaseConnectionException {
        Connection con = null;
        PreparedStatement ps1 = null, ps2 = null;
        try {
            con = DBConnUtil.getConnection();
            if (con == null) throw new DatabaseConnectionException("Database connection failed.");

          
            String sql1 = "UPDATE Trip SET driverId = ? WHERE tripId = ?";
            ps1 = con.prepareStatement(sql1);
            ps1.setInt(1, driverId);
            ps1.setInt(2, tripId);

     
            String sql2 = "UPDATE Driver SET status = 'Allocated' WHERE driverId = ?";
            ps2 = con.prepareStatement(sql2);
            ps2.setInt(1, driverId);

            return (ps1.executeUpdate() > 0 && ps2.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to allocate driver: " + e.getMessage());
        } finally {
            closeResources(ps1, ps2, con);
        }
    }

    @Override
    public boolean deallocateDriver(int tripId) throws DatabaseConnectionException {
        Connection con = null;
        PreparedStatement ps1 = null, ps2 = null;
        ResultSet rs = null;
        try {
            con = DBConnUtil.getConnection();
            if (con == null) throw new DatabaseConnectionException("Database connection failed.");

         
            String getDriverSql = "SELECT driverId FROM Trip WHERE tripId = ?";
            ps1 = con.prepareStatement(getDriverSql);
            ps1.setInt(1, tripId);
            rs = ps1.executeQuery();
            int driverId = -1;
            if (rs.next()) {
                driverId = rs.getInt("driverId");
            }

            if (driverId == -1) return false;

          
            String sql1 = "UPDATE Trip SET driverId = NULL WHERE tripId = ?";
            PreparedStatement psRemove = con.prepareStatement(sql1);
            psRemove.setInt(1, tripId);

         
            String sql2 = "UPDATE Driver SET status = 'Available' WHERE driverId = ?";
            ps2 = con.prepareStatement(sql2);
            ps2.setInt(1, driverId);

            return (psRemove.executeUpdate() > 0 && ps2.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to deallocate driver: " + e.getMessage());
        } finally {
            closeResources(rs, ps1, ps2, con);
        }
    }

    @Override
    public List<Driver> getAvailableDrivers() throws DatabaseConnectionException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Driver> list = new ArrayList<>();
        try {
            con = DBConnUtil.getConnection();
            if (con == null) throw new DatabaseConnectionException("Failed to connect to DB.");

            String sql = "SELECT * FROM Driver WHERE status = 'Available'";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Driver(
                    rs.getInt("driverId"),
                    rs.getString("name"),
                    rs.getString("licenseNumber"),
                    rs.getString("contact"),
                    rs.getString("status")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to fetch available drivers: " + e.getMessage());
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
