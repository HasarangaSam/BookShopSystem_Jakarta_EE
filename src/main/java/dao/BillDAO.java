package dao;

import model.Bill;
import model.BillItem;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BillDAO - Handles all billing-related database operations.
 */
public class BillDAO {

    // Add a bill and return the generated bill ID
    public int addBill(Bill bill) {
        String sql = "INSERT INTO bills (customer_id, cashier_id, bill_date, total_amount) VALUES (?, ?, ?, ?)";
        int billId = -1;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, bill.getCustomerId());
            stmt.setInt(2, bill.getCashierId());
            stmt.setTimestamp(3, Timestamp.valueOf(bill.getBillDate()));
            stmt.setDouble(4, bill.getTotalAmount());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                billId = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return billId;
    }

    // Add multiple items to bill_items table
    public void addBillItems(List<BillItem> items, int billId) {
        String sql = "INSERT INTO bill_items (bill_id, item_id, quantity, unit_price, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (BillItem item : items) {
                stmt.setInt(1, billId);
                stmt.setInt(2, item.getItemId());
                stmt.setInt(3, item.getQuantity());
                stmt.setDouble(4, item.getUnitPrice());
                stmt.setDouble(5, item.getSubtotal());
                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all bills (for Admin and Cashier)
    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        String sql = """
            SELECT b.*, 
                   c.first_name AS customer_first, c.last_name AS customer_last,
                   u.username AS cashier_username
            FROM bills b
            LEFT JOIN customers c ON b.customer_id = c.customer_id
            LEFT JOIN users u ON b.cashier_id = u.user_id
            ORDER BY b.bill_date DESC
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Bill bill = mapResultSetToBill(rs);

                // Combine first and last name for customer
                String customerName = rs.getString("customer_first") + " " + rs.getString("customer_last");
                bill.setCustomerName(customerName);

                // Use username as cashier name (since users table has no first/last name)
                String cashierName = rs.getString("cashier_username");
                bill.setCashierName(cashierName);

                bills.add(bill);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bills;
    }


 // Get a bill by ID with customer and cashier info
    public Bill getBillById(int billId) {
        Bill bill = null;
        String sql = """
            SELECT b.*, 
                   c.first_name AS customer_first, c.last_name AS customer_last,
                   u.username AS cashier_username
            FROM bills b
            LEFT JOIN customers c ON b.customer_id = c.customer_id
            LEFT JOIN users u ON b.cashier_id = u.user_id
            WHERE b.bill_id = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, billId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                bill = mapResultSetToBill(rs);

                String customerName = rs.getString("customer_first") + " " + rs.getString("customer_last");
                String cashierName = rs.getString("cashier_username");

                bill.setCustomerName(customerName);
                bill.setCashierName(cashierName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bill;
    }


    // Get all items of a bill
    public List<BillItem> getBillItemsByBillId(int billId) {
        List<BillItem> items = new ArrayList<>();
        String sql = """
                SELECT bi.*, i.name AS item_name, i.brand AS brand_name
				FROM bill_items bi
				JOIN items i ON bi.item_id = i.item_id
				WHERE bi.bill_id = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, billId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BillItem item = new BillItem();
                item.setBillItemId(rs.getInt("bill_item_id"));
                item.setBillId(rs.getInt("bill_id"));
                item.setItemId(rs.getInt("item_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getDouble("unit_price"));
                item.setSubtotal(rs.getDouble("subtotal"));
                item.setItemName(rs.getString("item_name"));
                item.setBrandName(rs.getString("brand_name"));

                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    // Helper
    private Bill mapResultSetToBill(ResultSet rs) throws SQLException {
        Bill bill = new Bill();
        bill.setBillId(rs.getInt("bill_id"));
        bill.setCustomerId(rs.getInt("customer_id"));
        bill.setCashierId(rs.getInt("cashier_id"));
        bill.setBillDate(rs.getTimestamp("bill_date").toLocalDateTime());
        bill.setTotalAmount(rs.getDouble("total_amount"));
        return bill;
    }
}
