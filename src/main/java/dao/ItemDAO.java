package dao;

import model.Item;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO implements ItemDAOInterface{

    // Add new item
	@Override
    public void addItem(Item item) {
        String sql = "INSERT INTO items (category_id, name, brand, unit_price, stock_quantity) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getCategoryId());
            stmt.setString(2, item.getName());
            stmt.setString(3, item.getBrand());
            stmt.setDouble(4, item.getUnitPrice());
            stmt.setInt(5, item.getStockQuantity());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all items with category name
	@Override
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT i.*, c.category_name FROM items i LEFT JOIN categories c ON i.category_id = c.category_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Item item = new Item(
                    rs.getInt("item_id"),
                    rs.getInt("category_id"),
                    rs.getString("category_name"),
                    rs.getString("name"),
                    rs.getString("brand"),
                    rs.getDouble("unit_price"),
                    rs.getInt("stock_quantity")
                );
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    // Get item by ID with category name
	@Override
    public Item getItemById(int id) {
        String sql = "SELECT i.*, c.category_name FROM items i LEFT JOIN categories c ON i.category_id = c.category_id WHERE i.item_id = ?";
        Item item = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                item = new Item(
                    rs.getInt("item_id"),
                    rs.getInt("category_id"),
                    rs.getString("category_name"),
                    rs.getString("name"),
                    rs.getString("brand"),
                    rs.getDouble("unit_price"),
                    rs.getInt("stock_quantity")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    // Update item
	@Override
    public void updateItem(Item item) {
        String sql = "UPDATE items SET category_id=?, name=?, brand=?, unit_price=?, stock_quantity=? WHERE item_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getCategoryId());
            stmt.setString(2, item.getName());
            stmt.setString(3, item.getBrand());
            stmt.setDouble(4, item.getUnitPrice());
            stmt.setInt(5, item.getStockQuantity());
            stmt.setInt(6, item.getItemId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete item
	@Override
    public void deleteItem(int id) {
        String sql = "DELETE FROM items WHERE item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Search items by name (partial match) with category name
	@Override
    public List<Item> searchItemsByName(String name) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT i.*, c.category_name FROM items i LEFT JOIN categories c ON i.category_id = c.category_id WHERE i.name LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Item item = new Item(
                    rs.getInt("item_id"),
                    rs.getInt("category_id"),
                    rs.getString("category_name"),
                    rs.getString("name"),
                    rs.getString("brand"),
                    rs.getDouble("unit_price"),
                    rs.getInt("stock_quantity")
                );
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    // Search by name and category
	@Override
    public List<Item> searchItemsByNameAndCategory(String name, int categoryId) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT i.*, c.category_name FROM items i LEFT JOIN categories c ON i.category_id = c.category_id WHERE i.name LIKE ? AND i.category_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");
            stmt.setInt(2, categoryId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Item item = new Item(
                    rs.getInt("item_id"),
                    rs.getInt("category_id"),
                    rs.getString("category_name"),
                    rs.getString("name"),
                    rs.getString("brand"),
                    rs.getDouble("unit_price"),
                    rs.getInt("stock_quantity")
                );
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    // Get items by category only
	@Override
    public List<Item> getItemsByCategory(int categoryId) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT i.*, c.category_name FROM items i LEFT JOIN categories c ON i.category_id = c.category_id WHERE i.category_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Item item = new Item(
                    rs.getInt("item_id"),
                    rs.getInt("category_id"),
                    rs.getString("category_name"),
                    rs.getString("name"),
                    rs.getString("brand"),
                    rs.getDouble("unit_price"),
                    rs.getInt("stock_quantity")
                );
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    // Update stock only
	@Override
    public void updateStock(int itemId, int newStockQuantity) {
        String sql = "UPDATE items SET stock_quantity = ? WHERE item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newStockQuantity);
            stmt.setInt(2, itemId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
