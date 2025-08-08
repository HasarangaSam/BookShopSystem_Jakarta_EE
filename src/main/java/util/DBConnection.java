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

    
     //Returns a single shared database connection instance.
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}

