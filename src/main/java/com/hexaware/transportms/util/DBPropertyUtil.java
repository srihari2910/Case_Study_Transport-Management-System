package com.hexaware.transportms.util;

import java.io.InputStream;
import java.util.Properties;

public class DBPropertyUtil {

    public static Properties getConnectionProperties() {
        Properties props = new Properties();
        try (InputStream input = DBPropertyUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.err.println("db.properties file not found in classpath.");
                return props;
            }
            props.load(input);
        } catch (Exception e) {
            System.err.println("Error reading db.properties: " + e.getMessage());
        }
        return props;
    }
}
