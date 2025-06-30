package com.hexaware.transportms.dao;

import com.hexaware.transportms.beans.Passenger;
import com.hexaware.transportms.exception.*;
import com.hexaware.transportms.util.DBConnUtil;

import java.sql.*;

public class PassengerDAOImpl implements PassengerDAO {

    @Override
    public Passenger getPassengerById(int passengerId) throws DatabaseConnectionException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnUtil.getConnection();
            if (con == null) throw new DatabaseConnectionException("Unable to connect to database.");

            String sql = "SELECT * FROM Passenger WHERE passengerId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, passengerId);
            rs = ps.executeQuery();

            if (rs.next()) {
                return new Passenger(
                    rs.getInt("passengerId"),
                    rs.getString("firstName"),
                    rs.getString("gender"),
                    rs.getInt("age"),
                    rs.getString("email"),
                    rs.getString("phoneNumber")
                );
            }

            return null;
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error fetching passenger: " + e.getMessage());
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
