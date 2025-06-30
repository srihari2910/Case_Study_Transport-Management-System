package com.hexaware.transportms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnUtil {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Properties props = DBPropertyUtil.getConnectionProperties();
            String url = props.getProperty("url");
            String username = props.getProperty("username");
            String password = props.getProperty("password");

            if (url == null) {
                System.err.println("The url cannot be null — check db.properties.");
                return null;
            }

            con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.err.println("Connection error: " + e.getMessage());
        }
        return con;
    }
}
