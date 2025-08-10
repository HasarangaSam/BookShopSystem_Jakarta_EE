<%@ page import="model.Item, model.Category, java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    model.User user = (model.User) session.getAttribute("user");
    if (user == null || !"stockkeeper".equals(user.getRole())) {
        response.sendRedirect("../login.jsp?error=Unauthorized+access");
        return;
    }

    Item item = (Item) request.getAttribute("item");
    List<Category> categories = (List<Category>) request.getAttribute("categories");

    if (item == null) {
        response.sendRedirect("items?error=Item+not+found");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Item - Pahana Edu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="../sidebars/stockkeeper_sidebar.jsp" %>

<div class="main-content d-flex align-items-center justify-content-center" style="margin-left: 240px; height: 100vh; background-color: #f8fafc;">
    <div class="container" style="max-width: 600px;">
        <h3 class="mb-4 text-center">✏️ Edit Item</h3>

        <form action="edit_item" method="post" class="border p-4 rounded bg-light shadow-sm">
            <input type="hidden" name="id" value="<%= item.getItemId() %>">

            <div class="mb-3">
                <label for="category_id" class="form-label">Category</label>
                <select name="category_id" id="category_id" class="form-select" required>
                    <% for (Category category : categories) { %>
                        <option value="<%= category.getCategoryId() %>"
                            <%= (category.getCategoryId() == item.getCategoryId()) ? "selected" : "" %>>
                            <%= category.getCategoryName() %>
                        </option>
                    <% } %>
                </select>
            </div>

            <div class="mb-3">
                <label for="name" class="form-label">Item Name</label>
                <input type="text" name="name" id="name" value="<%= item.getName() %>" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="brand" class="form-label">Brand</label>
                <input type="text" name="brand" id="brand" value="<%= item.getBrand() %>" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="unit_price" class="form-label">Unit Price</label>
                <input type="number" step="0.01" name="unit_price" id="unit_price" value="<%= item.getUnitPrice() %>" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="stock_quantity" class="form-label">Stock Quantity</label>
                <input type="number" name="stock_quantity" id="stock_quantity" value="<%= item.getStockQuantity() %>" class="form-control" required>
            </div>

            <% if (request.getParameter("error") != null) { %>
                <div class="alert alert-danger mt-3"><%= request.getParameter("error") %></div>
            <% } %>

            <div class="d-grid gap-2 mt-3">
                <button type="submit" class="btn btn-warning">💾 Update Item</button>
                <a href="items" class="btn btn-secondary">🔙 Cancel</a>
            </div>
        </form>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>
</body>
</html>
