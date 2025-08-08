package testClasses;

import java.sql.Connection;
import util.DBConnection;


// DBConnectionTest - Simple test class to verify database connection.

public class DBConnectionTest {
    public static void main(String[] args) {
        // Try to get a connection from the DBConnection class
        Connection conn = DBConnection.getConnection();

        // Print result
        if (conn != null) {
            System.out.println("✅ Database connection successful.");
        } else {
            System.out.println("❌ Failed to connect to the database.");
        }
    }
}

