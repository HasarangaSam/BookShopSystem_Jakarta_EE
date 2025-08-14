package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import util.DBConnection;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import com.google.gson.Gson;

@WebServlet("/admin/analytics")
public class AnalyticsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Database connection setup
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        // Variables to store the results
        int totalCustomers = 0;
        double totalRevenue = 0.0;
        int totalBills = 0;

        // Lists for the chart data
        List<String> dates = new ArrayList<>();
        List<Double> dailyRevenues = new ArrayList<>();

        try {
            // Get DB connection using your DBConnection utility
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();

            // Query to get total customers
            String customerQuery = "SELECT COUNT(*) AS total_customers FROM customers";
            rs = stmt.executeQuery(customerQuery);
            if (rs.next()) {
                totalCustomers = rs.getInt("total_customers");
            }

            // Query to get total revenue
            String revenueQuery = "SELECT SUM(total_amount) AS total_revenue FROM bills";
            rs = stmt.executeQuery(revenueQuery);
            if (rs.next()) {
                totalRevenue = rs.getDouble("total_revenue");
            }

            // Query to get total bills
            String billsQuery = "SELECT COUNT(*) AS total_bills FROM bills";
            rs = stmt.executeQuery(billsQuery);
            if (rs.next()) {
                totalBills = rs.getInt("total_bills");
            }

            // Query to get daily revenue for the last 30 days
            String dailyRevenueQuery = "SELECT DATE(bill_date) AS bill_date, SUM(total_amount) AS daily_revenue " +
                    "FROM bills " +
                    "WHERE bill_date >= CURDATE() - INTERVAL 30 DAY " +
                    "GROUP BY DATE(bill_date) " +
                    "ORDER BY bill_date ASC";
            rs = stmt.executeQuery(dailyRevenueQuery);

            // Populate dates and daily revenue lists for the chart
            while (rs.next()) {
                dates.add(rs.getString("bill_date"));
                dailyRevenues.add(rs.getDouble("daily_revenue"));
            }

            // Convert dates and dailyRevenues to JSON format
            Gson gson = new Gson();
            String datesJson = gson.toJson(dates);
            String dailyRevenuesJson = gson.toJson(dailyRevenues);

            // Set the data as request attributes to be used in JSP
            request.setAttribute("totalCustomers", totalCustomers);
            request.setAttribute("totalRevenue", totalRevenue);
            request.setAttribute("totalBills", totalBills);
            request.setAttribute("datesJson", datesJson);
            request.setAttribute("dailyRevenuesJson", dailyRevenuesJson);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        } finally {
            // Clean up resources
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
        }

        // Forward to analytics.jsp to render the results
        request.getRequestDispatcher("/admin/analytics.jsp").forward(request, response);
    }
}
