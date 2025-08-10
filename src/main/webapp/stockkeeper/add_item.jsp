<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Category" %>
<%
    // Session check for storekeeper role
    if (session == null || !"stockkeeper".equals(((model.User) session.getAttribute("user")).getRole())) {
        response.sendRedirect("../login.jsp?error=Unauthorized+access");
        return;
    }

    List<Category> categories = (List<Category>) request.getAttribute("categories");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Item - Pahana Edu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%@ include file="../sidebars/stockkeeper_sidebar.jsp" %>

<div class="main-content">
    <div class="container mt-4">
        <h3 class="mb-4">➕ Add New Item</h3>

        <form action="add_item" method="post" class="border p-4 rounded bg-light">
            <!-- Category Dropdown -->
            <div class="mb-3">
                <label for="category_id" class="form-label">Category</label>
                <select name="category_id" id="category_id" class="form-select" required>
                    <option value="" disabled selected>Select Category</option>
                    <%
                        if (categories != null && !categories.isEmpty()) {
                            for (Category category : categories) {
                    %>
                                <option value="<%= category.getCategoryId() %>">
                                    <%= category.getCategoryName() %>
                                </option>
                    <%
                            }
                        } else {
                    %>
                            <option disabled>No categories found</option>
                    <%
                        }
                    %>
                </select>
            </div>

            <!-- Item Name -->
            <div class="mb-3">
                <label for="name" class="form-label">Item Name</label>
                <input type="text" name="name" id="name" class="form-control" required>
            </div>

            <!-- Brand -->
            <div class="mb-3">
                <label for="brand" class="form-label">Brand</label>
                <input type="text" name="brand" id="brand" class="form-control" required>
            </div>

            <!-- Unit Price -->
            <div class="mb-3">
                <label for="unit_price" class="form-label">Unit Price (Rs.)</label>
                <input type="number" step="0.01" name="unit_price" id="unit_price" class="form-control" required>
            </div>

            <!-- Stock Quantity -->
            <div class="mb-3">
                <label for="stock_quantity" class="form-label">Stock Quantity</label>
                <input type="number" name="stock_quantity" id="stock_quantity" class="form-control" required>
            </div>

            <!-- Error Message -->
            <% if (request.getParameter("error") != null) { %>
                <div class="alert alert-danger"><%= request.getParameter("error") %></div>
            <% } %>

            <!-- Buttons -->
            <button type="submit" class="btn btn-success">Add Item</button>
            <a href="items" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>

</body>
</html>
