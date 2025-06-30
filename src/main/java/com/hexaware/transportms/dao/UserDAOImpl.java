package com.hexaware.transportms.dao;

import com.hexaware.transportms.exception.DatabaseConnectionException;
import com.hexaware.transportms.util.DBConnUtil;

import java.sql.*;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean register(String username, String password) throws DatabaseConnectionException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnUtil.getConnection();
            if (con == null) throw new DatabaseConnectionException("Unable to connect to database.");

            String sql = "INSERT INTO User (username, password) VALUES (?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Registration failed: " + e.getMessage());
        } finally {
            closeResources(ps, con);
        }
    }

    @Override
    public boolean validateUser(String username, String password) throws DatabaseConnectionException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnUtil.getConnection();
            if (con == null) throw new DatabaseConnectionException("Unable to connect to database.");

            String sql = "SELECT * FROM User WHERE username = ? AND password = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();

            return rs.next(); // If a row exists, login is valid
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Login failed: " + e.getMessage());
        } finally {
            closeResources(rs, ps, con);
        }
    }

    private void closeResources(AutoCloseable... resources) {
        for (AutoCloseable res : resources) {
            try {
                if (res != null) res.close();
            } catch (Exception e) {
                System.err.println("Error closing resource: " + e.getMessage());
            }
        }
    }
}
