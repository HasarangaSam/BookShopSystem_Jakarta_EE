package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection - Singleton class to manage database connections.
 */
public class DBConnection {

    private static Connection connection;

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/pahanaedubookshopdb";
    private static final String USERNAME = "root"; 
    private static final String PASSWORD = "";     

    // Private constructor prevents instantiation
    private DBConnection() {}

    /**
     * Returns a single, valid DB connection instance.
     */
    public static synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load the MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Create a new connection to the database
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Log the error if the connection fails
        }
        return connection;
    }
}
