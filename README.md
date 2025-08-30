# 📚 Pahana Edu Bookshop System

A dynamic web-based *Billing and Inventory Management System* designed for the *Pahana Edu Bookshop*.  
The system streamlines operations for three key roles: *Admin, Cashier, and Stock Keeper*, providing role-based dashboards and functionalities.  

## 🚀 Key Features
- 🔑 *Role Management* – Admin, Cashier, and Stock Keeper dashboards  
- 👥 *User & Customer Management* – Create, update, search, and manage records  
- 📦 *Category & Item Management* – Organize stock with categories and maintain items with stock updates  
- 🧾 *Billing System* – Generate bills, download as PDF (iText), and email invoices (Jakarta Mail)  
- 🔄 *Automated Stock Update* – MySQL trigger reduces stock after billing  
- ✅ *Testing & CI/CD* – DAO testing with JUnit, GitHub Actions for automated workflow  

## 🛠️ Tech Stack
- *Backend:* JSP, Servlets, JDBC  
- *Database:* MySQL with triggers  
- *Libraries:* iText (PDF), Jakarta Mail (Email)  
- *Architecture:* MVC + DAO pattern  
- *Tools:* GitHub Actions (CI/CD), Eclipse IDE  

## 📂 Project Structure
- src/ → controller, dao, model, util, test  
- webapp/ → role-based folders (admin, cashier, storekeeper), shared common, sidebars, and JSPs  
- .github/workflows/ci.yml → CI/CD pipeline
