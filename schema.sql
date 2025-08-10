-- 1. Users Table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'cashier', 'stockkeeper') NOT NULL
);

-- 2. Customers Table
CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    address VARCHAR(255),
    telephone VARCHAR(15),
    email VARCHAR(100)
);

-- 3. Categories Table
CREATE TABLE categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL
);

-- 4. Items Table
CREATE TABLE items (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT,
    name VARCHAR(100),
    brand VARCHAR(100),
    unit_price DECIMAL(10,2),
    stock_quantity INT,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
        ON DELETE SET NULL
);

-- 5. Bills Table
CREATE TABLE bills (
    bill_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    cashier_id INT,
    bill_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE SET NULL,
    FOREIGN KEY (cashier_id) REFERENCES users(user_id) ON DELETE SET NULL
);

-- 6. Bill Items Table
CREATE TABLE bill_items (
    bill_item_id INT AUTO_INCREMENT PRIMARY KEY,
    bill_id INT,
    item_id INT,
    quantity INT,
    unit_price DECIMAL(10,2),
    subtotal DECIMAL(10,2),
    FOREIGN KEY (bill_id) REFERENCES bills(bill_id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE SET NULL
);

-- Add a dummy user (cashier)
INSERT INTO users (username, password, role)
VALUES ('cashier1', 'pass123', 'cashier');

-- Add a dummy customer
INSERT INTO customers (first_name, last_name, address, telephone, email)
VALUES ('Test', 'Customer', '123 Main Street', '0700000000', 'test@example.com');

-- Add a dummy category
INSERT INTO categories (category_name)
VALUES ('Stationery');

-- Add a dummy item (linked to the category above)
INSERT INTO items (category_id, name, brand, unit_price, stock_quantity)
VALUES (1, 'Pen', 'Pilot', 120.00, 100);

-- Add a dummy bill (linked to customer + user)
INSERT INTO bills (customer_id, cashier_id, total_amount)
VALUES (1, 1, 240.00);

-- Add a dummy bill item (linked to bill + item)
INSERT INTO bill_items (bill_id, item_id, quantity, unit_price, subtotal)
VALUES (1, 1, 2, 120.00, 240.00);