package model;

/**
 * Item - Represents an inventory item.
 */
public class Item {
    private int itemId;
    private int categoryId;
    private String categoryName;  // Added this field for display
    private String name;
    private String brand;
    private double unitPrice;
    private int stockQuantity;

    // Constructor for retrieving from DB (without categoryName)
    public Item(int itemId, int categoryId, String name, String brand, double unitPrice, int stockQuantity) {
        this.itemId = itemId;
        this.categoryId = categoryId;
        this.name = name;
        this.brand = brand;
        this.unitPrice = unitPrice;
        this.stockQuantity = stockQuantity;
    }

    // Optional constructor with categoryName (when joining category table)
    public Item(int itemId, int categoryId, String categoryName, String name, String brand, double unitPrice, int stockQuantity) {
        this.itemId = itemId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.name = name;
        this.brand = brand;
        this.unitPrice = unitPrice;
        this.stockQuantity = stockQuantity;
    }

    // Getters and Setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
